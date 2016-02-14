package online.bbstats.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.repository.TeamLeagueRepository;
import online.bbstats.repository.domain.TeamLeague;

@Service
public class TeamLeagueService {
    
    @Autowired
    private TeamLeagueRepository teamLeagueRepository;
    
    public List<TeamLeague> findByActiveAtDate(LocalDate date) {
        return teamLeagueRepository.findByActiveAtDate(date);
    }

}
