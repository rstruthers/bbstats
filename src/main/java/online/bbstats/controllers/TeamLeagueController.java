package online.bbstats.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.model.TeamModel;
import online.bbstats.repository.domain.TeamLeague;
import online.bbstats.service.TeamLeagueService;

@Controller
public class TeamLeagueController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamLeagueController.class);
    
    @Autowired
    private TeamLeagueService teamLeagueService;
    
    @RequestMapping(value="/teamleagues", method=RequestMethod.GET)
    public ModelAndView getTeamLeagueListPage() {
        LOGGER.debug("Getting team league list page");
        ModelAndView mav = new ModelAndView("teamleagues");
        List<TeamLeague> teamLeagues = teamLeagueService.findAll();
        
        List<TeamModel> teamModelList = new ArrayList<TeamModel>();
        
        for (TeamLeague teamLeague: teamLeagues) {
            TeamModel team = new TeamModel();
            team.setLeague(teamLeague.getLeague().getName());
            team.setLocation(teamLeague.getTeam().getLocation());
            team.setName(teamLeague.getTeam().getName());
            team.setStartDate(teamLeague.getStartDate());
            team.setEndDate(teamLeague.getEndDate());
            teamModelList.add(team);
        }
        
        mav.addObject("teams", teamModelList);
        return mav;
    }
    
}
