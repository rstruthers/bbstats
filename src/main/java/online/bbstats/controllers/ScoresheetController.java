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
        mav.addObject("scoresheet", scoresheet);
        addScoresheetFormToModel(mav, scoresheet);
        addPlayersOnVisitingTeamToModel(mav, scoresheet);
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
        addScoresheetFormToModel(mav, scoresheet);
        addPlayersOnVisitingTeamToModel(mav, scoresheet);
        return mav;
    }
    
    @RequestMapping(value = "/scoresheet", method = RequestMethod.POST)
    public String updateScoresheet(Model model, @ModelAttribute("form") ScoresheetForm form,  
    		@RequestParam(value="action", required=true) String action) {
    	LOGGER.debug(">>>>>>>>>>>>> UPDATING SCORESHEET, action = " + action);
    	
        Scoresheet scoresheet = scoresheetService.findById(form.getId());
    	
    	if (action.startsWith("vp:")) {
    	    String[] vpArray = action.split(":");
            int vpLineupOrder = Integer.parseInt(vpArray[1]);
            int vpLineupOrderIndex = Integer.parseInt(vpArray[2]);
    	    scoresheetService.addVisitorScoresheetPlayerInLineupOrderAfterIndex(scoresheet.getId(), vpLineupOrder, vpLineupOrderIndex);
    	} else if (action.startsWith("delete_vp:")) {
    		//deleteVisitingPlayerFromPositionOrderSpot(form, action);
    	} else {
    	    scoresheetService.updateVisitorScoresheetPlayers(scoresheet.getId(), form.getLineupOrders());
    	}
    	
    	return "redirect:/scoresheet/id/" + scoresheet.getId();
    }

	
/**
    private void deleteVisitingPlayerFromPositionOrderSpot(ScoresheetForm form, String action) {
		String[] vpArray = action.split(":");
		int vpLineupOrder = Integer.parseInt(vpArray[1]);
		int vpLineupOrderIndex = Integer.parseInt(vpArray[2]);
		List<ScoresheetPlayerModel> scoresheetPlayerModelList = form.getVisitingPlayers();
		int indexWhereButtonWasClicked = 0;
		int numberOfPlayersInPositionOrderSpot = 0;
		for (int i = 0; i < scoresheetPlayerModelList.size(); i++) {
			ScoresheetPlayerModel scoresheetPlayerModel = scoresheetPlayerModelList.get(i);
			if (vpLineupOrder == scoresheetPlayerModel.getLineupOrder()) {
				numberOfPlayersInPositionOrderSpot++;
			}
			if (vpLineupOrder == scoresheetPlayerModel.getLineupOrder() &&
					vpLineupOrderIndex == scoresheetPlayerModel.getLineupOrderIndex()) {
				indexWhereButtonWasClicked = i;
			}
		}
		if (numberOfPlayersInPositionOrderSpot == 1) {
			return;
		}
		scoresheetPlayerModelList.remove(indexWhereButtonWasClicked);
		int lineupOrderIndex = 0;
		for (int i = 0; i < scoresheetPlayerModelList.size(); i++) {
			ScoresheetPlayerModel scoresheetPlayerModel = scoresheetPlayerModelList.get(i);
			if (vpLineupOrder == scoresheetPlayerModel.getLineupOrder() ) {
				scoresheetPlayerModel.setLineupOrderIndex(lineupOrderIndex);
				lineupOrderIndex++;
			}
		}
	}

	private void addVisitingPlayerToPositionOrderSpot(ScoresheetForm form, String action) {
		String[] vpArray = action.split(":");
		int vpLineupOrder = Integer.parseInt(vpArray[1]);
		int vpLineupOrderIndex = Integer.parseInt(vpArray[2]);
		List<ScoresheetPlayerModel> scoresheetPlayerModelList = form.getVisitingPlayers();
		int indexWhereButtonWasClicked = 0;
		for (int i = 0; i < scoresheetPlayerModelList.size(); i++) {
			ScoresheetPlayerModel scoresheetPlayerModel = scoresheetPlayerModelList.get(i);
			if (vpLineupOrder == scoresheetPlayerModel.getLineupOrder() &&
					vpLineupOrderIndex == scoresheetPlayerModel.getLineupOrderIndex()) {
				indexWhereButtonWasClicked = i;
				break;
			}
		}
		ScoresheetPlayerModel scoresheetPlayerModel = new ScoresheetPlayerModel();
		scoresheetPlayerModel.setLineupOrder(vpLineupOrder);
		scoresheetPlayerModel.setLineupOrderIndex(vpLineupOrderIndex + 1);
		scoresheetPlayerModelList.add(indexWhereButtonWasClicked + 1, scoresheetPlayerModel);
		int lineupOrderIndex = 0;
		for (int i = 0; i < scoresheetPlayerModelList.size(); i++) {
			scoresheetPlayerModel = scoresheetPlayerModelList.get(i);
			if (vpLineupOrder == scoresheetPlayerModel.getLineupOrder() ) {
				scoresheetPlayerModel.setLineupOrderIndex(lineupOrderIndex);
				lineupOrderIndex++;
			}
		}
	}
	**/
    
    @RequestMapping(value = "/scoresheet/find", method = RequestMethod.GET)
    public ModelAndView getFindScoresheetPage() {
        ModelAndView mav = new ModelAndView("scoresheet_find");
        List<Scoresheet> scoresheets = scoresheetService.findAll();
        mav.addObject("scoresheets", scoresheets);
        return mav;
    }
    
    private void addPlayersOnVisitingTeamToModel(ModelAndView mav, Scoresheet scoresheet) {
    	 List<PlayerModel> playerModelList = new ArrayList<PlayerModel>();
         
         List<TeamPlayer> teamPlayers = rosterService.findTeamPlayersByTeamNameActiveAtDate(scoresheet.getVisitingTeam().getName(), 
        		 scoresheet.getGameDate());
         
         if (teamPlayers == null) {
             return;
         }
         
         for (TeamPlayer teamPlayer: teamPlayers) {
             PlayerModel playerModel = new PlayerModel();
             playerModelList.add(playerModel);
             playerModel.setName(teamPlayer.getPlayer().getName());
             playerModel.setId(teamPlayer.getPlayer().getId());
         }
         mav.addObject("visitingPlayersDropdown", playerModelList);
	}
    
    private void addScoresheetFormToModel(ModelAndView mav, Scoresheet scoresheet) {
		ScoresheetForm form = new ScoresheetForm();
        form.setId(scoresheet.getId());
        List<LineupOrderModel> lineupOrders = new ArrayList<LineupOrderModel>();
        form.setLineupOrders(lineupOrders);
        for (int i = 1; i <= 9; i++) {
            List<ScoresheetPlayer> playersFromDb = scoresheetPlayerRepository.findVisitorsByScoresheetIdAndLineupOrder(scoresheet.getId(), i);
            LineupOrderModel lineupOrderModel = new LineupOrderModel();
            lineupOrders.add(lineupOrderModel);
            lineupOrderModel.setLineupOrderPosition(i);
            List<ScoresheetPlayerModel> scoresheetPlayerModelList = new ArrayList<ScoresheetPlayerModel>();
            lineupOrderModel.setScoresheetPlayers(scoresheetPlayerModelList);
           
            if (playersFromDb == null || playersFromDb.size() == 0) {
                ScoresheetPlayerModel scoresheetPlayerModel = new ScoresheetPlayerModel();
                scoresheetPlayerModelList.add(scoresheetPlayerModel);
                scoresheetPlayerModel.setLineupOrder(i);
                ScoresheetPlayer scoresheetPlayer = new ScoresheetPlayer(scoresheet, i, 0, null);
                scoresheetPlayerRepository.save(scoresheetPlayer);
                continue;
            } 
            for (ScoresheetPlayer playerFromDb: playersFromDb) {
                ScoresheetPlayerModel scoresheetPlayerModel = new ScoresheetPlayerModel();
                scoresheetPlayerModelList.add(scoresheetPlayerModel);
                scoresheetPlayerModel.setLineupOrder(i);
                if (playerFromDb != null && playerFromDb.getPlayer() != null) {
                    scoresheetPlayerModel.setPlayerId(playerFromDb.getPlayer().getId());
                }
            }
        }
        
        /**
        for (int i = 1; i <= 9; i++) {
        	List<ScoresheetPlayer> playersFromDb = scoresheetPlayerRepository.findVisitorsByScoresheetIdAndLineupOrder(scoresheet.getId(), i);
        	if (playersFromDb == null || playersFromDb.size() == 0) {
        	    ScoresheetPlayerModel scoresheetPlayerModel = new ScoresheetPlayerModel();
                scoresheetPlayerModel.setLineupOrder(i);
                scoresheetPlayerModel.setLineupOrderIndex(0);
                scoresheetPlayerModelList.add(scoresheetPlayerModel);
                ScoresheetPlayer scoresheetPlayer = new ScoresheetPlayer(scoresheet, i, 0, null);
                scoresheetPlayerRepository.save(scoresheetPlayer);
        	    continue;
        	}
        	for (ScoresheetPlayer playerFromDb: playersFromDb) {
	        	ScoresheetPlayerModel scoresheetPlayerModel = new ScoresheetPlayerModel();
	        	scoresheetPlayerModel.setLineupOrder(i);
	        	scoresheetPlayerModel.setLineupOrderIndex(playerFromDb.getLineupOrderIndex());
	        	if (playerFromDb != null && playerFromDb.getPlayer() != null) {
	        		scoresheetPlayerModel.setPlayerId(playerFromDb.getPlayer().getId());
	        	}
	        	scoresheetPlayerModelList.add(scoresheetPlayerModel);
        	}
        }
        form.setVisitingPlayers(scoresheetPlayerModelList);
        
        **/
        mav.addObject("form", form);
	}
    
   
}
