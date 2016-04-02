package online.bbstats.service;

import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import online.bbstats.BbstatsConstants;
import online.bbstats.forms.ScoresheetCreateForm;
import online.bbstats.model.LineupOrderModel;
import online.bbstats.model.ScoresheetPitcherModel;
import online.bbstats.model.ScoresheetPlayerModel;
import online.bbstats.repository.ScoresheetPitcherRepository;
import online.bbstats.repository.ScoresheetPlayerRepository;
import online.bbstats.repository.ScoresheetRepository;
import online.bbstats.repository.domain.Player;
import online.bbstats.repository.domain.Scoresheet;
import online.bbstats.repository.domain.ScoresheetPitcher;
import online.bbstats.repository.domain.ScoresheetPlayer;
import online.bbstats.repository.domain.Season;
import online.bbstats.repository.domain.Team;

@Service
public class ScoresheetService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ScoresheetService.class);

    @Autowired
    private ScoresheetRepository scoresheetRepository;

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private TeamService teamService;
    
    @Autowired
    private PlayerService playerService;
    
    @Autowired
    private ScoresheetPlayerRepository scoresheetPlayerRepository;
    
    @Autowired
    private ScoresheetPitcherRepository scoresheetPitcherRepository;

    public Scoresheet create(ScoresheetCreateForm form) {
        LOGGER.debug("Creating a scoresheet");
        Scoresheet scoresheet = new Scoresheet();
        Season season = seasonService.findSeasonByName(form.getSeasonName());
        scoresheet.setSeason(season);

        Team visitingTeam = teamService.findTeamByName(form.getVisitingTeamName());
        scoresheet.setVisitingTeam(visitingTeam);

        Team homeTeam = teamService.findTeamByName(form.getHomeTeamName());
        scoresheet.setHomeTeam(homeTeam);

        LocalDate gameDate = LocalDate.parse(form.getGameDate(), BbstatsConstants.DATE_TIME_FORMATTER);
        scoresheet.setGameDate(gameDate);

        scoresheet.setGameNumber(form.getGameNumberForDate());

        scoresheetRepository.save(scoresheet);
        return scoresheet;
    }

    public Scoresheet findById(Long id) {
        return scoresheetRepository.findOne(id);
    }

    public List<Scoresheet> findAll() {
        return scoresheetRepository
                .findAll(new Sort(Sort.Direction.ASC, "gameDate", "homeTeam.name", "visitingTeam.name"));
    }

    public Scoresheet findByTeamsAndDate(String visitingTeamName, String homeTeamName, LocalDate gameDate) {
        return scoresheetRepository.findByTeamsAndDate(visitingTeamName, homeTeamName, gameDate);
    }

    public void updateVisitorScoresheetPlayers(Long scoresheetId, List<LineupOrderModel> lineupOrderModelList) {
        Scoresheet scoresheet = scoresheetRepository.findOne(scoresheetId);
        for (int lineupOrder = 1; lineupOrder <= 9; lineupOrder++) {
            LineupOrderModel lineupOrderModel = lineupOrderModelList.get(lineupOrder - 1);
            List<ScoresheetPlayerModel> scoresheetPlayerModelList = lineupOrderModel.getScoresheetPlayers();
            if (scoresheetPlayerModelList == null) {
                continue;
            }
           
            for (int lineupOrderIndex  = 0; lineupOrderIndex  < scoresheetPlayerModelList.size(); lineupOrderIndex++) {
                ScoresheetPlayerModel scoresheetPlayerModel = scoresheetPlayerModelList.get(lineupOrderIndex);
                ScoresheetPlayer scoresheetPlayer = scoresheetPlayerRepository.findVisitorByScoresheetIdLineupOrderAndIndex(scoresheetId, lineupOrder, lineupOrderIndex);
                updateOrAddScoresheetPlayer(scoresheet, null, lineupOrder, lineupOrderIndex, scoresheetPlayerModel, scoresheetPlayer);
            }
            
            int numDbPlayersAtLineupOrderPosition = scoresheetPlayerRepository.getNumVisitingPlayersAtLineupOrderPosition(scoresheetId, lineupOrder);
            int numModelPlayerAtLineupOrderPosition = scoresheetPlayerModelList.size();
            for (int lineupOrderIndex = numModelPlayerAtLineupOrderPosition; lineupOrderIndex < numDbPlayersAtLineupOrderPosition; lineupOrderIndex++) {
                ScoresheetPlayer scoresheetPlayer = scoresheetPlayerRepository.findVisitorByScoresheetIdLineupOrderAndIndex(scoresheetId, lineupOrder, lineupOrderIndex);
                scoresheetPlayerRepository.delete(scoresheetPlayer);
            }
        }
    }

    public void updateHomeScoresheetPlayers(Long scoresheetId, List<LineupOrderModel> lineupOrderModelList) {
        Scoresheet scoresheet = scoresheetRepository.findOne(scoresheetId);
        
        for (int lineupOrder = 1; lineupOrder <= 9; lineupOrder++) {
            LineupOrderModel lineupOrderModel = lineupOrderModelList.get(lineupOrder - 1);
            List<ScoresheetPlayerModel> scoresheetPlayerModelList = lineupOrderModel.getScoresheetPlayers();
            if (scoresheetPlayerModelList == null) {
                continue;
            }
           
            for (int lineupOrderIndex  = 0; lineupOrderIndex  < scoresheetPlayerModelList.size(); lineupOrderIndex++) {
                ScoresheetPlayerModel scoresheetPlayerModel = scoresheetPlayerModelList.get(lineupOrderIndex);
                ScoresheetPlayer scoresheetPlayer = scoresheetPlayerRepository.findHomePlayerByScoresheetIdLineupOrderAndIndex(scoresheetId, lineupOrder, lineupOrderIndex);
                updateOrAddScoresheetPlayer(null, scoresheet, lineupOrder, lineupOrderIndex, scoresheetPlayerModel, scoresheetPlayer);
            }
            
            int numDbPlayersAtLineupOrderPosition = scoresheetPlayerRepository.getNumHomePlayersAtLineupOrderPosition(scoresheetId, lineupOrder);
            int numModelPlayerAtLineupOrderPosition = scoresheetPlayerModelList.size();
            for (int lineupOrderIndex = numModelPlayerAtLineupOrderPosition; lineupOrderIndex < numDbPlayersAtLineupOrderPosition; lineupOrderIndex++) {
                ScoresheetPlayer scoresheetPlayer = scoresheetPlayerRepository.findHomePlayerByScoresheetIdLineupOrderAndIndex(scoresheetId, lineupOrder, lineupOrderIndex);
                scoresheetPlayerRepository.delete(scoresheetPlayer);
            }
        }
    }

    private void updateOrAddScoresheetPlayer(Scoresheet visitorScoresheet, Scoresheet homeScoresheet, int lineupOrder, int lineupOrderIndex,
            ScoresheetPlayerModel scoresheetPlayerModel, ScoresheetPlayer scoresheetPlayer) {
        Player player = null;
        if (scoresheetPlayerModel.getPlayerId() != null) {
            player = playerService.getPlayerById(scoresheetPlayerModel.getPlayerId());
        }
        if (scoresheetPlayer == null) {
            scoresheetPlayer = new ScoresheetPlayer(visitorScoresheet, homeScoresheet, lineupOrder, lineupOrderIndex, player);
        } else {
            scoresheetPlayer.setLineupOrder(lineupOrder);
            scoresheetPlayer.setLineupOrderIndex(lineupOrderIndex);
            scoresheetPlayer.setPlayer(player);
        }
        if (scoresheetPlayerModel != null) {
            scoresheetPlayer.setAtBats(scoresheetPlayerModel.getAtBats());
            scoresheetPlayer.setRuns(scoresheetPlayerModel.getRuns());
            scoresheetPlayer.setHits(scoresheetPlayerModel.getHits());
            scoresheetPlayer.setRbi(scoresheetPlayerModel.getRbi());
            scoresheetPlayer.setDoubles(scoresheetPlayerModel.getDoubles());
            scoresheetPlayer.setTriples(scoresheetPlayerModel.getTriples());
            scoresheetPlayer.setHomeruns(scoresheetPlayerModel.getHomeruns());
            scoresheetPlayer.setStolenBases(scoresheetPlayerModel.getStolenBases());
            scoresheetPlayer.setErrors(scoresheetPlayerModel.getErrors());
            scoresheetPlayer.setPassedBalls(scoresheetPlayerModel.getPassedBalls());
        }
        scoresheetPlayerRepository.save(scoresheetPlayer);
    }

    public void updateVisitorScoresheetPitchers(Long scoresheetId, List<ScoresheetPitcherModel> visitorPitchers) {
        Scoresheet visitorScoresheet = scoresheetRepository.findOne(scoresheetId);
        
        
        
        
        int pitcherOrder = 0;
        for (ScoresheetPitcherModel pitcherModel: visitorPitchers) {
           pitcherOrder++;
           System.out.println("updating or adding pitcher, pitcherOrder: " + pitcherOrder);
           ScoresheetPitcher scoresheetPitcher = 
                   scoresheetPitcherRepository.findVisitorPitcherByScoresheetIdAndPitcherOrder(scoresheetId, pitcherOrder);
           updateOrAddScoresheetPitcher(visitorScoresheet, null, pitcherOrder, pitcherModel, scoresheetPitcher);
        }
        
        int numDbVisitorPitchers = scoresheetPitcherRepository.getNumVisitorPitchers(scoresheetId);
        int numModelVisitorPitchers = visitorPitchers.size();
        for (pitcherOrder = numModelVisitorPitchers + 1; pitcherOrder <= numDbVisitorPitchers; pitcherOrder++) {
            System.out.println("Deleting pitcher, pitcher order: " + pitcherOrder);
            ScoresheetPitcher scoresheetPitcher = 
                    scoresheetPitcherRepository.findVisitorPitcherByScoresheetIdAndPitcherOrder(scoresheetId, pitcherOrder);
            scoresheetPitcherRepository.delete(scoresheetPitcher);
        }
        
       
    }
    
    public void updateHomeScoresheetPitchers(Long scoresheetId, List<ScoresheetPitcherModel> homePitchers) {
        Scoresheet homeScoresheet = scoresheetRepository.findOne(scoresheetId);
        int pitcherOrder = 0;
        for (ScoresheetPitcherModel pitcherModel: homePitchers) {
           pitcherOrder++;
           ScoresheetPitcher scoresheetPitcher = 
                   scoresheetPitcherRepository.findHomePitcherByScoresheetIdAndPitcherOrder(scoresheetId, pitcherOrder);
           updateOrAddScoresheetPitcher(null, homeScoresheet, pitcherOrder, pitcherModel, scoresheetPitcher);
        }
        int numDbHomePitchers = scoresheetPitcherRepository.getNumHomePitchers(scoresheetId);
        int numModelHomePitchers = homePitchers.size();
        for (pitcherOrder = numModelHomePitchers + 1; pitcherOrder <= numDbHomePitchers; pitcherOrder++) {
            ScoresheetPitcher scoresheetPitcher = 
                    scoresheetPitcherRepository.findHomePitcherByScoresheetIdAndPitcherOrder(scoresheetId, pitcherOrder);
            scoresheetPitcherRepository.delete(scoresheetPitcher);
        }
    }
    
    private void updateOrAddScoresheetPitcher(Scoresheet visitorScoresheet, Scoresheet homeScoresheet, int pitcherOrder,
            ScoresheetPitcherModel pitcherModel, ScoresheetPitcher scoresheetPitcher) {
       
        if (scoresheetPitcher == null) {
            scoresheetPitcher = new ScoresheetPitcher(visitorScoresheet, homeScoresheet, pitcherOrder);
        } 
        
        if (pitcherModel != null) {
            Player player = null;
            if (pitcherModel.getPlayerId() != null) {
                player = playerService.getPlayerById(pitcherModel.getPlayerId());
            }
            scoresheetPitcher.setPlayer(player);
            setWholePlusPartialInningsPitcher(scoresheetPitcher, pitcherModel);
            scoresheetPitcher.setHits(pitcherModel.getHits());
            scoresheetPitcher.setRuns(pitcherModel.getRuns());
            scoresheetPitcher.setEarnedRuns(pitcherModel.getEarnedRuns());
            scoresheetPitcher.setWalks(pitcherModel.getWalks());
            scoresheetPitcher.setStrikeouts(pitcherModel.getStrikeouts());
            scoresheetPitcher.setHomeruns(pitcherModel.getHomeruns());
            scoresheetPitcher.setBalks(pitcherModel.getBalks());
            scoresheetPitcher.setWin(pitcherModel.getWin());
            scoresheetPitcher.setLoss(pitcherModel.getLoss());
            scoresheetPitcher.setSave(pitcherModel.getSave());
        }
        
        scoresheetPitcherRepository.save(scoresheetPitcher);
    }

    private void setWholePlusPartialInningsPitcher(ScoresheetPitcher scoresheetPitcher,
            ScoresheetPitcherModel pitcherModel) {
        try {
            String wholePlusPartialInningsPitchedString = StringUtils.trimToEmpty(pitcherModel.getWholePlusPartialInningsPitched());
            String[] inningsPitchedArray = wholePlusPartialInningsPitchedString.split("\\.");
            
            String inningsPitchedString = "";
            if (inningsPitchedArray.length > 0) {
                inningsPitchedString = inningsPitchedArray[0];
            }
            String partialInningsPitchedString = "";
            if (inningsPitchedArray.length > 1) {
                partialInningsPitchedString = inningsPitchedArray[1];
            }
            scoresheetPitcher.setInningsPitched(stringToInteger(inningsPitchedString));
            scoresheetPitcher.setPartialInningsPitched(stringToInteger(partialInningsPitchedString));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Integer stringToInteger(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception ex) {
            return null;
        }
    }
}
