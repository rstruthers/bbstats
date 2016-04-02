package online.bbstats.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.repository.SeasonRepository;
import online.bbstats.repository.domain.Season;
import online.bbstats.repository.domain.TeamLeague;

@Service
public class SeasonService {
	
	@Autowired
	private SeasonRepository seasonRepository;
	
	@Autowired
	private TeamLeagueService teamLeagueService;

	public Season findSeasonByName(String name) {
		return seasonRepository.findByName(name);
	}
	
	public List<Season> findAllSeasons() {
	    return seasonRepository.findAllByOrderByStartDateAsc();
	}

    public List<TeamLeague> findTeamLeaguesBySeason(String seasonName) {
        Season season = findSeasonByName(seasonName);
        if (season == null) {
            return null;
        }
        
        return teamLeagueService.findByActiveAtDate(season.getStartDate());
        
       
    }

}
