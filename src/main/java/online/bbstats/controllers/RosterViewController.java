package online.bbstats.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.BbstatsConstants;
import online.bbstats.forms.RosterSelectForm;
import online.bbstats.model.PlayerPositionModel;
import online.bbstats.model.SeasonModel;
import online.bbstats.repository.domain.Season;
import online.bbstats.repository.domain.Team;
import online.bbstats.repository.domain.TeamPlayer;
import online.bbstats.repository.domain.TeamPlayerPosition;
import online.bbstats.service.RosterService;
import online.bbstats.service.SeasonService;
import online.bbstats.service.TeamService;

@Controller
public class RosterViewController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RosterViewController.class);
    
    @Autowired
    private RosterService rosterService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private SeasonService seasonService;
    
    @RequestMapping(value = "/roster/view/season/{seasonName}/team/{teamName}", method = RequestMethod.GET)
    public ModelAndView getRosterView(@PathVariable("seasonName") String seasonName, @PathVariable("teamName") String teamName) {
        LOGGER.debug("getRosterView");
        ModelAndView mav = new ModelAndView("roster_view");
        
        Season season = seasonService.findSeasonByName(seasonName);
        Team team = teamService.findTeamByName(teamName);
        
        List<PlayerPositionModel> players = getPlayerPositionModelList(team, season);
        mav.addObject("team", team);
        mav.addObject("season", season);
        mav.addObject("numPlayers", team.getTeamPlayers().size());
        mav.addObject("positionNames", BbstatsConstants.POSITIONS);
        mav.addObject("players", players);
        return mav;
    }
    
    @RequestMapping(value = "/roster/view/select", method = RequestMethod.GET)
    public ModelAndView getRosterViewSelect() {
        LOGGER.debug("getRosterViewSelect");
        ModelAndView mav = new ModelAndView("roster_view_select");
        mav.addObject("form", new RosterSelectForm());
        List<Season> seasons = seasonService.findAllSeasons();
        List<SeasonModel> seasonModelList = new ArrayList<SeasonModel>();
        for (Season season: seasons) {
            SeasonModel seasonModel = new SeasonModel();
            BeanUtils.copyProperties(season, seasonModel);
            seasonModelList.add(seasonModel);
        }
        mav.addObject("seasons", seasonModelList);
       
        return mav;
    }
    
    @RequestMapping(value = "/roster/view", method = RequestMethod.POST)
    public ModelAndView getRosterViewUsingForm(@ModelAttribute("form") RosterSelectForm form) {
        LOGGER.debug("getRosterView");
        ModelAndView mav = new ModelAndView("roster_view");
        
        Season season = seasonService.findSeasonByName(form.getSeasonName());
        Team team = teamService.findTeamByName(form.getTeamName());
        
        List<PlayerPositionModel> players = getPlayerPositionModelList(team, season);
        mav.addObject("team", team);
        mav.addObject("season", season);
        mav.addObject("numPlayers", team.getTeamPlayers().size());
        mav.addObject("positionNames", BbstatsConstants.POSITIONS);
        mav.addObject("players", players);
        return mav;
    }
    
    private List<PlayerPositionModel> getPlayerPositionModelList(Team team, Season season) {
        List<TeamPlayer> teamPlayers = rosterService.findTeamPlayersByTeamAndSeason(team, season);
        List<PlayerPositionModel> playerPositionModelList = new ArrayList<PlayerPositionModel>();
        for (TeamPlayer teamPlayer : teamPlayers) {
            PlayerPositionModel playerPositionModel = new PlayerPositionModel();
            playerPositionModel.setName(teamPlayer.getPlayer().getName());
            playerPositionModel.setDateOfBirth(teamPlayer.getPlayer().getDateOfBirth());
            Map<String, Integer> positionMap = new HashMap<String, Integer>();
            List<TeamPlayerPosition> teamPlayerPositions = teamPlayer.getTeamPlayerPositions();
            for (TeamPlayerPosition tpp : teamPlayerPositions) {
                positionMap.put(tpp.getPosition(), tpp.getNumGames());
            }
            playerPositionModel.setPositionMap(positionMap);
            playerPositionModelList.add(playerPositionModel);
        }
        return playerPositionModelList;

    }
}
