package online.bbstats.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.forms.LeagueForm;
import online.bbstats.repository.LeagueRepository;
import online.bbstats.repository.domain.League;

@Service
public class LeagueService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LeagueService.class);
	
	@Autowired
	LeagueRepository leagueRepository;
	
	public League create(LeagueForm leagueForm) {
		League league = new League();
		BeanUtils.copyProperties(leagueForm, league);
		leagueRepository.save(league);
		return league;
	}

	public List<League> getAllLeagues() {
		LOGGER.debug("Getting all leagues");
		return leagueRepository.findAll();
	}
	
	public League getLeagueById(Long id) {
		LOGGER.debug("Getting league by id: " + id);
		return leagueRepository.findOne(id);
	}
	
	public void update(LeagueForm leagueForm) {
		League league = new League();
		BeanUtils.copyProperties(leagueForm, league);
		leagueRepository.save(league);
	}
}
