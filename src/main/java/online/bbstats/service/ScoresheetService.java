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
        scoresheetPlayerRepository.save(scoresheetPlayer);
    }
}
