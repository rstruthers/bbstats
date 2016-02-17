package online.bbstats.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.repository.TeamLeagueRepository;
import online.bbstats.repository.domain.League;
import online.bbstats.repository.domain.Team;
import online.bbstats.repository.domain.TeamLeague;

@Service
public class TeamLeagueService {
    
    @Autowired
    private TeamLeagueRepository teamLeagueRepository;
    
    public List<TeamLeague> findByActiveAtDate(LocalDate date) {
        return teamLeagueRepository.findByActiveAtDate(date);
    }
    
    public TeamLeague findByTeamIdLeagueIdAndActiveAtDate(Long teamId, Long leagueId, LocalDate date) {
        return teamLeagueRepository.findByTeamIdLeagueIdAndActiveAtDate(teamId, leagueId, date);
    }

    public void create(Team team, League league, LocalDate startDate, LocalDate endDate) {
        TeamLeague teamLeague = new TeamLeague();
        teamLeague.setTeam(team);
        teamLeague.setLeague(league);
        teamLeague.setStartDate(startDate);
        teamLeague.setEndDate(endDate);
        teamLeagueRepository.save(teamLeague);
    }

    public List<TeamLeague> findAll() {
        return teamLeagueRepository.findAll();
    }
}
