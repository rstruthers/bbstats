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
import online.bbstats.repository.ScoresheetRepository;
import online.bbstats.repository.domain.Scoresheet;
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

}
