package online.bbstats.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.forms.TeamForm;
import online.bbstats.repository.TeamRepository;
import online.bbstats.repository.domain.Team;

@Service
public class TeamService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamService.class);
	
	@Autowired
	TeamRepository teamRepository;
	
	public Team create(TeamForm teamForm) {
		Team team = new Team();
		BeanUtils.copyProperties(teamForm, team);
		teamRepository.save(team);
		return team;
	}

	public List<Team> getAllTeams() {
		LOGGER.debug("Getting all teams");
		return teamRepository.findAll();
	}
}
