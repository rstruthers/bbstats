package online.bbstats.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.forms.ScoresheetForm;
import online.bbstats.repository.domain.Scoresheet;
import online.bbstats.service.ScoresheetService;

@Controller
public class ScoresheetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoresheetController.class);
    
    @Autowired
    private ScoresheetService scoresheetService;
    
    @RequestMapping(value = "/scoresheet/id/{id}", method = RequestMethod.GET)
    public ModelAndView getScoresheetById(@PathVariable("id") Long id) {
        LOGGER.debug("Get scoresheet");
        ModelAndView mav = new ModelAndView("scoresheet");
        Scoresheet scoresheet = scoresheetService.findById(id);
        mav.addObject("scoresheet", scoresheet);
        ScoresheetForm form = new ScoresheetForm();
        form.setId(scoresheet.getId());
        mav.addObject("form", form);
        return mav;
    }
    
    @RequestMapping(value = "/scoresheet/visitor/{visitingTeamName}/home/{homeTeamName}/date/{date}", method = RequestMethod.GET)
    public ModelAndView getScoresheet(@PathVariable("visitingTeamName") String visitingTeamName,
            @PathVariable("homeTeamName") String homeTeamName,
            @PathVariable("date") String date) {
        LOGGER.debug("Get scoresheet");
        ModelAndView mav = new ModelAndView("scoresheet");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        LocalDate gameDate = LocalDate.parse(date, formatter);
        Scoresheet scoresheet = scoresheetService.findByTeamsAndDate(visitingTeamName, homeTeamName, gameDate);
        mav.addObject("scoresheet", scoresheet);
        ScoresheetForm form = new ScoresheetForm();
        form.setId(scoresheet.getId());
        mav.addObject("form", form);
        return mav;
    }
    
    @RequestMapping(value = "/scoresheet/find", method = RequestMethod.GET)
    public ModelAndView getFindScoresheetPage() {
        ModelAndView mav = new ModelAndView("scoresheet_find");
        List<Scoresheet> scoresheets = scoresheetService.findAll();
        mav.addObject("scoresheets", scoresheets);
        return mav;
    }
}
