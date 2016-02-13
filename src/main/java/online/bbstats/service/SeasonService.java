package online.bbstats.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.repository.SeasonRepository;
import online.bbstats.repository.domain.Season;

@Service
public class SeasonService {
	
	@Autowired
	private SeasonRepository seasonRepository;

	public Season findSeasonByName(String name) {
		return seasonRepository.findByName(name);
	}
	
	public List<Season> findAllSeasons() {
	    return seasonRepository.findAll();
	}

}
