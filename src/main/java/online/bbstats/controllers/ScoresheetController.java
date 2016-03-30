package online.bbstats.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
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
import online.bbstats.model.ScoresheetPitcherModel;
import online.bbstats.model.ScoresheetPlayerModel;
import online.bbstats.repository.ScoresheetPitcherRepository;
import online.bbstats.repository.ScoresheetPlayerRepository;
import online.bbstats.repository.domain.Scoresheet;
import online.bbstats.repository.domain.ScoresheetPitcher;
import online.bbstats.repository.domain.ScoresheetPlayer;
import online.bbstats.repository.domain.TeamPlayer;
import online.bbstats.repository.domain.TeamPlayerPosition;
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
    
    @Autowired
    private ScoresheetPitcherRepository scoresheetPitcherRepository;
    
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
    	scoresheetService.updateVisitorScoresheetPitchers(scoresheet.getId(), form.getVisitorPitchers());
    	scoresheetService.updateHomeScoresheetPitchers(scoresheet.getId(), form.getHomePitchers());
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
        addPitchersOnVisitingTeamToModel(mav, scoresheet);
        addPitchersOnHomeTeamToModel(mav, scoresheet);
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
    
    private void addPitchersOnVisitingTeamToModel(ModelAndView mav, Scoresheet scoresheet) {
        List<PlayerModel> pitcherModelList = getPitcherModelListForTeamOnGameDate(scoresheet.getVisitingTeam().getName(), 
                scoresheet.getGameDate());
         mav.addObject("visitingPitchersDropdown", pitcherModelList);
    }
    
    private void addPitchersOnHomeTeamToModel(ModelAndView mav, Scoresheet scoresheet) {
        List<PlayerModel> pitcherModelList = getPitcherModelListForTeamOnGameDate(scoresheet.getHomeTeam().getName(), 
                scoresheet.getGameDate());
         mav.addObject("homePitchersDropdown", pitcherModelList);
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
    
    private List<PlayerModel> getPitcherModelListForTeamOnGameDate(String teamName, LocalDate gameDate) {
        List<PlayerModel> pitcherModelList = new ArrayList<PlayerModel>();
        List<TeamPlayer> teamPlayers = rosterService.findTeamPlayersByTeamNameActiveAtDate(teamName, gameDate); 
        
        if (teamPlayers == null) {
            return pitcherModelList;
        }
        
        for (TeamPlayer teamPlayer: teamPlayers) {
            PlayerModel playerModel = new PlayerModel();
            pitcherModelList.add(playerModel);
            playerModel.setName(teamPlayer.getPlayer().getName());
            playerModel.setId(teamPlayer.getPlayer().getId());
            List<TeamPlayerPosition> teamPlayerPositions = teamPlayer.getTeamPlayerPositions();
            String primaryPosition = getPrimaryPosition(teamPlayerPositions);
            playerModel.setPrimaryPosition(primaryPosition);
        }
        
        sortPlayerModelsSoPitchersAppearFirst(pitcherModelList);
        
        return pitcherModelList;
    }
    
    private void sortPlayerModelsSoPitchersAppearFirst(List<PlayerModel> pitcherModelList) {
        Collections.sort(pitcherModelList, new PitcherPlayerModelComparator());
    }

    private String getPrimaryPosition(List<TeamPlayerPosition> teamPlayerPositions) {
        if (teamPlayerPositions == null) {
            return "";
        }
        TeamPlayerPosition primaryPlayerPosition = null;
        for (TeamPlayerPosition teamPlayerPosition: teamPlayerPositions) {
            if (teamPlayerPosition == null || teamPlayerPosition.getNumGames() == null) {
                continue;
            }
            if (primaryPlayerPosition == null) {
                primaryPlayerPosition = teamPlayerPosition;
                continue;
            }
            if (teamPlayerPosition.getNumGames() != null) {
                if (primaryPlayerPosition.getNumGames() == null) {
                    primaryPlayerPosition = teamPlayerPosition;
                    continue;
                } else {
                    if (teamPlayerPosition.getNumGames() > primaryPlayerPosition.getNumGames()) {
                        primaryPlayerPosition = teamPlayerPosition;
                        continue;
                    }
                }
            }
        }
        if (primaryPlayerPosition != null) {
            return StringUtils.trimToEmpty(primaryPlayerPosition.getPosition());
        }
        return "";
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
        
        List<ScoresheetPitcherModel> visitorPitchers = new ArrayList<ScoresheetPitcherModel>();
        form.setVisitorPitchers(visitorPitchers);
        List<ScoresheetPitcher> visitorPitchersFromDb = scoresheetPitcherRepository.findVisitorPitchersByScoresheetId(scoresheet.getId());
        addDbPitchersToVisitorPitchersModel(scoresheet, null, visitorPitchers, visitorPitchersFromDb);
        
        List<ScoresheetPitcherModel> homePitchers = new ArrayList<ScoresheetPitcherModel>();
        form.setHomePitchers(homePitchers);
        List<ScoresheetPitcher> homePitchersFromDb = scoresheetPitcherRepository.findHomePitchersByScoresheetId(scoresheet.getId());
        addDbPitchersToVisitorPitchersModel(null, scoresheet, homePitchers, homePitchersFromDb);
        
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
    
    private void addDbPitchersToVisitorPitchersModel(Scoresheet visitorScoresheet, Scoresheet homeScoresheet,
            List<ScoresheetPitcherModel> scoresheetPitcherModelList, List<ScoresheetPitcher> pitchersFromDb) {
        if (pitchersFromDb == null || pitchersFromDb.size() == 0) {
            ScoresheetPitcherModel scoresheetPitcherModel = new ScoresheetPitcherModel();
            scoresheetPitcherModel.setPitcherOrder(1);
            scoresheetPitcherModelList.add(scoresheetPitcherModel);
            ScoresheetPitcher scoresheetPitcher = new ScoresheetPitcher(visitorScoresheet, homeScoresheet, 1);
            scoresheetPitcherRepository.save(scoresheetPitcher);
            return;
        }
        
        for (ScoresheetPitcher pitcherFromDb: pitchersFromDb) {
            ScoresheetPitcherModel scoresheetPitcherModel = new ScoresheetPitcherModel();
            scoresheetPitcherModelList.add(scoresheetPitcherModel);
            if (pitcherFromDb != null) {
                if (pitcherFromDb.getPlayer() != null) {
                    scoresheetPitcherModel.setPlayerId(pitcherFromDb.getPlayer().getId());
                }
                BeanUtils.copyProperties(pitcherFromDb, scoresheetPitcherModel);
                setWholePlusPartialInningsPitched(pitcherFromDb, scoresheetPitcherModel);
            }
        }
    }

    private void setWholePlusPartialInningsPitched(ScoresheetPitcher pitcherFromDb,
            ScoresheetPitcherModel scoresheetPitcherModel) {
        int wholeInningsPitched = integerToInt(pitcherFromDb.getInningsPitched());
        int partialInningsPitched = integerToInt(pitcherFromDb.getPartialInningsPitched());
        String wholePlusPartialInningsPitched = "";
        if (wholeInningsPitched == 0 && partialInningsPitched == 0) {
            wholePlusPartialInningsPitched = "";
        } else if (wholeInningsPitched == 0) {
            wholePlusPartialInningsPitched = "." + partialInningsPitched;
        } else if (partialInningsPitched == 0) {
            wholePlusPartialInningsPitched = Integer.toString(wholeInningsPitched);
        } else {
            wholePlusPartialInningsPitched = wholeInningsPitched + "." + partialInningsPitched;
        }
        scoresheetPitcherModel.setWholePlusPartialInningsPitched(wholePlusPartialInningsPitched);
    }

    private int integerToInt(Integer i) {
        if (i == null) {
            return 0;
        }
        return i.intValue();
    }

  
}
