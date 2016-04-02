package online.bbstats.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.domain.validator.ScoresheetCreateFormValidator;
import online.bbstats.forms.ScoresheetCreateForm;
import online.bbstats.model.SeasonModel;
import online.bbstats.model.TeamModel;
import online.bbstats.repository.domain.Scoresheet;
import online.bbstats.repository.domain.Season;
import online.bbstats.repository.domain.TeamLeague;
import online.bbstats.service.ScoresheetService;
import online.bbstats.service.SeasonService;

@Controller
public class ScoresheetCreateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoresheetCreateController.class);
    
    @Autowired
    private SeasonService seasonService;
    
    @Autowired
    private ScoresheetCreateFormValidator scoresheetCreateFormValidator;
    
    @Autowired 
    private ScoresheetService scoresheetService;
    
    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(scoresheetCreateFormValidator);
    }
    
    @ModelAttribute("seasons")
    public List<SeasonModel> getSeasons() {
        List<Season> seasons = seasonService.findAllSeasons();
        List<SeasonModel> seasonModelList = new ArrayList<SeasonModel>();
        for (Season season: seasons) {
            SeasonModel seasonModel = new SeasonModel();
            BeanUtils.copyProperties(season, seasonModel);
            seasonModelList.add(seasonModel);
        }
        return seasonModelList;
    }
    
    
    @RequestMapping(value = "/scoresheet/create", method = RequestMethod.GET)
    public ModelAndView getScoresheetCreateForm() {
        LOGGER.debug("Getting scoresheetcreate form");
        ModelAndView mav = new ModelAndView("scoresheet_create");
        mav.addObject("form", new ScoresheetCreateForm());
        return mav;
    }
    
    @RequestMapping(value = "/scoresheet/create", method = RequestMethod.POST)
    public String handleScoresheetCreateForm(@Valid @ModelAttribute("form") ScoresheetCreateForm form, BindingResult bindingResult, Model model) {
        LOGGER.debug("Processing scoresheet create form {}", form);
        
        model.addAttribute("teams", getTeamsForSeason(form.getSeasonName()));
        
        if (bindingResult.hasErrors()) {
            
            return "scoresheet_create";
        }
        
        Scoresheet scoresheet = scoresheetService.create(form);
        
        return "redirect:/scoresheet/id/" + scoresheet.getId();
    }
    
    public List<TeamModel> getTeamsForSeason(String seasonName) {
        List<TeamModel> teamModelList = new ArrayList<TeamModel>();
        if (StringUtils.isEmpty(seasonName)) {
            return teamModelList;
        }
        List<TeamLeague> teamLeagues = seasonService.findTeamLeaguesBySeason(seasonName);
        
        if (teamLeagues == null) {
            return teamModelList;
        }
        
        for (TeamLeague teamLeague : teamLeagues) {
            TeamModel teamModel = new TeamModel();
            teamModelList.add(teamModel);
            BeanUtils.copyProperties(teamLeague.getTeam(), teamModel);
        }
        
        return teamModelList;
     }
    
}
