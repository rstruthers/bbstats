package online.bbstats.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.forms.ScoresheetForm;
import online.bbstats.model.LineupOrderModel;
import online.bbstats.model.PlayerModel;
import online.bbstats.model.ScoresheetPlayerModel;
import online.bbstats.repository.ScoresheetPlayerRepository;
import online.bbstats.repository.domain.Scoresheet;
import online.bbstats.repository.domain.ScoresheetPlayer;
import online.bbstats.repository.domain.TeamPlayer;
import online.bbstats.service.RosterService;
import online.bbstats.service.ScoresheetService;

@Controller
public class ScoresheetController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoresheetController.class);
    
    @Autowired
    private ScoresheetService scoresheetService;
    
    @Autowired
    private RosterService rosterService;
    
    @Autowired
    private ScoresheetPlayerRepository scoresheetPlayerRepository;
    
    @RequestMapping(value = "/scoresheet/id/{id}", method = RequestMethod.GET)
    public ModelAndView getScoresheetById(@PathVariable("id") Long id) {
        LOGGER.debug("Get scoresheet");
        ModelAndView mav = new ModelAndView("scoresheet");
        Scoresheet scoresheet = scoresheetService.findById(id);
        refreshModelForScoresheetPage(mav, scoresheet);
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
        refreshModelForScoresheetPage(mav, scoresheet);
        return mav;
    }
    
    @RequestMapping(value = "/scoresheet", method = RequestMethod.POST)
    public String updateScoresheet(Model model, @ModelAttribute("form") ScoresheetForm form,  
    		@RequestParam(value="action", required=true) String action) {
    	LOGGER.debug(">>>>>>>>>>>>> UPDATING SCORESHEET, action = " + action);
        Scoresheet scoresheet = scoresheetService.findById(form.getId());
    	scoresheetService.updateVisitorScoresheetPlayers(scoresheet.getId(), form.getVisitorLineupOrders());
    	scoresheetService.updateHomeScoresheetPlayers(scoresheet.getId(), form.getHomeLineupOrders());
    	return "redirect:/scoresheet/id/" + scoresheet.getId();
    }
    
    @RequestMapping(value = "/scoresheet/find", method = RequestMethod.GET)
    public ModelAndView getFindScoresheetPage() {
        ModelAndView mav = new ModelAndView("scoresheet_find");
        List<Scoresheet> scoresheets = scoresheetService.findAll();
        mav.addObject("scoresheets", scoresheets);
        return mav;
    }
    
    private void refreshModelForScoresheetPage(ModelAndView mav, Scoresheet scoresheet) {
        mav.addObject("scoresheet", scoresheet);
        addScoresheetFormToModel(mav, scoresheet);
        addPlayersOnVisitingTeamToModel(mav, scoresheet);
        addPlayersOnHomeTeamToModel(mav, scoresheet);
    }
    
    private void addPlayersOnVisitingTeamToModel(ModelAndView mav, Scoresheet scoresheet) {
        List<PlayerModel> playerModelList = getPlayerModelListForTeamOnGameDate(scoresheet.getVisitingTeam().getName(), 
                scoresheet.getGameDate());
         mav.addObject("visitingPlayersDropdown", playerModelList);
	}
    
    private void addPlayersOnHomeTeamToModel(ModelAndView mav, Scoresheet scoresheet) {
        List<PlayerModel> playerModelList = getPlayerModelListForTeamOnGameDate(scoresheet.getHomeTeam().getName(), 
                scoresheet.getGameDate());
        mav.addObject("homePlayersDropdown", playerModelList);
    }
    
    private List<PlayerModel> getPlayerModelListForTeamOnGameDate(String teamName, LocalDate gameDate) {
        List<PlayerModel> playerModelList = new ArrayList<PlayerModel>();
        List<TeamPlayer> teamPlayers = rosterService.findTeamPlayersByTeamNameActiveAtDate(teamName, gameDate); 
        
        if (teamPlayers == null) {
            return playerModelList;
        }
        
        for (TeamPlayer teamPlayer: teamPlayers) {
            PlayerModel playerModel = new PlayerModel();
            playerModelList.add(playerModel);
            playerModel.setName(teamPlayer.getPlayer().getName());
            playerModel.setId(teamPlayer.getPlayer().getId());
        }
        
        return playerModelList;
    }
    
    private void addScoresheetFormToModel(ModelAndView mav, Scoresheet scoresheet) {
		ScoresheetForm form = new ScoresheetForm();
        form.setId(scoresheet.getId());
        List<LineupOrderModel> visitorLineupOrders = new ArrayList<LineupOrderModel>();
        form.setVisitorLineupOrders(visitorLineupOrders);
        for (int i = 1; i <= 9; i++) {
            List<ScoresheetPlayer> playersFromDb = scoresheetPlayerRepository.findVisitorsByScoresheetIdAndLineupOrder(scoresheet.getId(), i);
            addDbPlayersToLineupOrderModel(scoresheet, null, visitorLineupOrders, i, playersFromDb);
        }
        
        List<LineupOrderModel> homeLineupOrders = new ArrayList<LineupOrderModel>();
        form.setHomeLineupOrders(homeLineupOrders);
        for (int i = 1; i <= 9; i++) {
            List<ScoresheetPlayer> playersFromDb = scoresheetPlayerRepository.findHomePlayersByScoresheetIdAndLineupOrder(scoresheet.getId(), i);
            addDbPlayersToLineupOrderModel(null, scoresheet, homeLineupOrders, i, playersFromDb);
        }
 
        mav.addObject("form", form);
	}

    private void addDbPlayersToLineupOrderModel(Scoresheet visitorScoresheet, Scoresheet homeScoresheet, List<LineupOrderModel> lineupOrders,
            int lineupOrderPosition, List<ScoresheetPlayer> playersFromDb) {
        LineupOrderModel lineupOrderModel = new LineupOrderModel();
        lineupOrders.add(lineupOrderModel);
        lineupOrderModel.setLineupOrderPosition(lineupOrderPosition);
        List<ScoresheetPlayerModel> scoresheetPlayerModelList = new ArrayList<ScoresheetPlayerModel>();
        lineupOrderModel.setScoresheetPlayers(scoresheetPlayerModelList);
         
        if (playersFromDb == null || playersFromDb.size() == 0) {
            ScoresheetPlayerModel scoresheetPlayerModel = new ScoresheetPlayerModel();
            scoresheetPlayerModelList.add(scoresheetPlayerModel);
            scoresheetPlayerModel.setLineupOrder(lineupOrderPosition);
            ScoresheetPlayer scoresheetPlayer = new ScoresheetPlayer(visitorScoresheet, homeScoresheet, lineupOrderPosition, 0, null);
            scoresheetPlayerRepository.save(scoresheetPlayer);
            return;
        } 
        for (ScoresheetPlayer playerFromDb: playersFromDb) {
            ScoresheetPlayerModel scoresheetPlayerModel = new ScoresheetPlayerModel();
            scoresheetPlayerModelList.add(scoresheetPlayerModel);
            scoresheetPlayerModel.setLineupOrder(lineupOrderPosition);
            if (playerFromDb != null && playerFromDb.getPlayer() != null) {
                scoresheetPlayerModel.setPlayerId(playerFromDb.getPlayer().getId());
                scoresheetPlayerModel.setAtBats(playerFromDb.getAtBats());
                scoresheetPlayerModel.setRuns(playerFromDb.getRuns());
                scoresheetPlayerModel.setHits(playerFromDb.getHits());
                scoresheetPlayerModel.setRbi(playerFromDb.getRbi());
                scoresheetPlayerModel.setDoubles(playerFromDb.getDoubles());
                scoresheetPlayerModel.setTriples(playerFromDb.getTriples());
                scoresheetPlayerModel.setHomeruns(playerFromDb.getHomeruns());
                scoresheetPlayerModel.setStolenBases(playerFromDb.getStolenBases());
                scoresheetPlayerModel.setErrors(playerFromDb.getErrors());
                scoresheetPlayerModel.setPassedBalls(playerFromDb.getPassedBalls());
            }
        }
    }
   
}
