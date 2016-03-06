package online.bbstats.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import online.bbstats.BbstatsConstants;
import online.bbstats.forms.ScoresheetCreateForm;
import online.bbstats.model.LineupOrderModel;
import online.bbstats.model.ScoresheetPlayerModel;
import online.bbstats.repository.ScoresheetPlayerRepository;
import online.bbstats.repository.ScoresheetRepository;
import online.bbstats.repository.domain.Player;
import online.bbstats.repository.domain.Scoresheet;
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

	public void updateVisitingPlayers(Scoresheet scoresheet, List<ScoresheetPlayerModel> visitingPlayers) {
		/**
	    List<ScoresheetPlayer> playersFromDb = scoresheetPlayerRepository.findVisitorsByScoresheetIdOrderByPositionAndIndex(scoresheet.getId());
		for (ScoresheetPlayer playerFromDb: playersFromDb) {
		    Integer spModelIndex = findScoresheetPlayerModelIndexInList(visitingPlayers, playerFromDb.getLineupOrder(), playerFromDb.getLineupOrderIndex());
		    if (spModelIndex == null) {
		        scoresheetPlayerRepository.delete(playerFromDb);
		        continue;
		    }
		    
		    ScoresheetPlayerModel spModel = visitingPlayers.get(spModelIndex);
		    visitingPlayers.remove(spModelIndex);
		    if (spModel.getPlayerId() != null) {
		        if (playerFromDb.getPlayer() == null || !spModel.getPlayerId().equals(playerFromDb.getId())) {
		            playerFromDb.setPlayer(playerService.getPlayerById(spModel.getPlayerId()));
		            scoresheetPlayerRepository.save(playerFromDb);
		            continue;
		        }
		    } else {
		        if (playerFromDb.getPlayer() != null) {
		            playerFromDb.setPlayer(null);
		            scoresheetPlayerRepository.save(playerFromDb);
		            continue;
		        }
		    }
		}
		
		for (ScoresheetPlayerModel spModel: visitingPlayers) {
		    ScoresheetPlayer newPlayer = new ScoresheetPlayer();
		    newPlayer.setLineupOrder(spModel.getLineupOrder());
		    newPlayer.setLineupOrderIndex(spModel.getLineupOrderIndex());
		    if (spModel.getPlayerId() != null) {
		        newPlayer.setPlayer(playerService.getPlayerById(spModel.getPlayerId()));
		        newPlayer.setVisitorScoresheet(scoresheet);
		        scoresheetPlayerRepository.save(newPlayer);
		    }
		}
		
		**/
		
		/**
		scoresheetPlayerRepository.deleteVisitorsByScoresheetId(scoresheet.getId());
        List<ScoresheetPlayer> scoresheetPlayers = new ArrayList<ScoresheetPlayer>();
        for (ScoresheetPlayerModel visitingPlayer: visitingPlayers) {
            ScoresheetPlayer scoresheetPlayer = new ScoresheetPlayer();
            scoresheetPlayers.add(scoresheetPlayer);
            scoresheetPlayer.setLineupOrder(visitingPlayer.getLineupOrder());
            scoresheetPlayer.setLineupOrderIndex(visitingPlayer.getLineupOrderIndex());
            scoresheetPlayer.setVisitorScoresheet(scoresheet);
            if (visitingPlayer.getPlayerId() != null) {
                scoresheetPlayer.setPlayer(playerService.getPlayerById(visitingPlayer.getPlayerId()));
            }
            scoresheetPlayerRepository.save(scoresheetPlayer);
        }
        scoresheetRepository.save(scoresheet);
        **/
	}

	/**
    private Integer findScoresheetPlayerModelIndexInList(List<ScoresheetPlayerModel> players,
            Integer lineupOrder, Integer lineupOrderIndex) {
        for (int i = 0; i < players.size(); i++) {
            ScoresheetPlayerModel spModel = players.get(i);
            if (spModel.getLineupOrder().equals(lineupOrder) && spModel.getLineupOrderIndex().equals(lineupOrderIndex)) {
                return i;
            }
        }
        return null;
    }
    **/

    public void addVisitorScoresheetPlayerInLineupOrderAfterIndex(Long scoresheetId, int lineupOrder, int lineupOrderIndex) {
       List<ScoresheetPlayer> playersAtLineupOrderPos = scoresheetPlayerRepository.findVisitorsByScoresheetIdAndLineupOrder(scoresheetId, lineupOrder);
       Scoresheet scoresheet = scoresheetRepository.findOne(scoresheetId);
       
       for (int i = lineupOrderIndex + 1; i < playersAtLineupOrderPos.size(); i++) {
           playersAtLineupOrderPos.get(i).setLineupOrderIndex(i + 1);
           scoresheetPlayerRepository.save(playersAtLineupOrderPos.get(i));
       }
       ScoresheetPlayer scoresheetPlayer = new ScoresheetPlayer(scoresheet, lineupOrder, lineupOrderIndex, null);
       scoresheetPlayerRepository.save(scoresheetPlayer);
    }
  

    public void updateVisitorScoresheetPlayers(Long scoresheetId, List<LineupOrderModel> lineupOrderModelList) {
        Scoresheet scoresheet = scoresheetRepository.findOne(scoresheetId);
        
        for (int lineupOrder = 1; lineupOrder <= 9; lineupOrder++) {
            LineupOrderModel lineupOrderModel = lineupOrderModelList.get(lineupOrder - 1);
            LOGGER.debug("lineupOrder: " + lineupOrder);
            List<ScoresheetPlayerModel> scoresheetPlayerModelList = lineupOrderModel.getScoresheetPlayers();
            if (scoresheetPlayerModelList == null) {
                LOGGER.debug("scoresheetPlayerModelList is null");
                continue;
            }
           
            for (int lineupOrderIndex  = 0; lineupOrderIndex  < scoresheetPlayerModelList.size(); lineupOrderIndex++) {
                LOGGER.debug("lineupOrderIndex: " + lineupOrderIndex);
                ScoresheetPlayerModel scoresheetPlayerModel = scoresheetPlayerModelList.get(lineupOrderIndex);
                ScoresheetPlayer scoresheetPlayer = scoresheetPlayerRepository.findVisitorByScoresheetIdLineupOrderAndIndex(scoresheetId, lineupOrder, lineupOrderIndex);
                Player player = null;
                LOGGER.debug("scoresheetPlayerModel.getPlayerId(): " + scoresheetPlayerModel.getPlayerId() );
                if (scoresheetPlayerModel.getPlayerId() != null) {
                    player = playerService.getPlayerById(scoresheetPlayerModel.getPlayerId());
                    if (player == null) {
                        LOGGER.debug("Player from db is null");
                    } else {
                        LOGGER.debug("Player from db is not null, name is " + player.getName());
                    }
                }
                if (scoresheetPlayer == null) {
                    LOGGER.debug("scoresheetPlayer from db is null");
                    scoresheetPlayer = new ScoresheetPlayer(scoresheet, lineupOrder, lineupOrderIndex, player);
                } else {
                    LOGGER.debug("scoresheetPlayer from db is not null");
                    scoresheetPlayer.setLineupOrder(lineupOrder);
                    scoresheetPlayer.setLineupOrderIndex(lineupOrderIndex);
                    scoresheetPlayer.setPlayer(player);
                }
                LOGGER.debug("saving scoresheetPlayer");
                scoresheetPlayerRepository.save(scoresheetPlayer);
            }
            
            int numDbPlayersAtLineupOrderPosition = scoresheetPlayerRepository.getNumVisitingPlayersAtLineupOrderPosition(scoresheetId, lineupOrder);
            int numModelPlayerAtLineupOrderPosition = scoresheetPlayerModelList.size();
            for (int lineupOrderIndex = numModelPlayerAtLineupOrderPosition; lineupOrderIndex < numDbPlayersAtLineupOrderPosition; lineupOrderIndex++) {
                ScoresheetPlayer scoresheetPlayer = scoresheetPlayerRepository.findVisitorByScoresheetIdLineupOrderAndIndex(scoresheetId, lineupOrder, lineupOrderIndex);
                scoresheetPlayerRepository.delete(scoresheetPlayer);
            }
        }
    }
}
