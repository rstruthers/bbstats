package online.bbstats.service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.BbstatsConstants;
import online.bbstats.forms.LeagueForm;
import online.bbstats.forms.TeamForm;
import online.bbstats.repository.LeagueRepository;
import online.bbstats.repository.domain.League;
import online.bbstats.repository.domain.Team;
import online.bbstats.repository.domain.TeamLeague;

@Service
public class LeagueService {
	private static final Logger LOGGER = LoggerFactory.getLogger(LeagueService.class);
	
	@Autowired
	private LeagueRepository leagueRepository;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired
	private TeamLeagueService teamLeagueService;
	
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
	
	public List<League> findAllLeagues() {
	    return leagueRepository.findAll();
	}

    public void addTeam(String leagueName, Map<String, String> teamValueMap) {
        League league = leagueRepository.findByName(leagueName);
        if (league == null) {
            throw new RuntimeException("Can't find league with name \"" + leagueName + "\"");
        }
        String teamName = teamValueMap.get("Name");
        String location = teamValueMap.get("Location");
        Team team = teamService.findTeamByNameAndLocation(teamName, location);
        if (team == null) {
            TeamForm teamForm = new TeamForm();
            teamForm.setLocation(location);
            teamForm.setName(teamName);
            teamForm.setCity(teamValueMap.get("City"));
            teamForm.setState(teamValueMap.get("State"));
            team = teamService.create(teamForm);
        }
        
        LocalDate startDate = dateStringToLocalDate(teamValueMap.get("Start Date"));
        LocalDate endDate = dateStringToLocalDate(teamValueMap.get("End Date"));
        
        TeamLeague teamLeague = teamLeagueService.findByTeamIdLeagueIdAndActiveAtDate(team.getId(), league.getId(), startDate);
        
        if (teamLeague == null) {
            teamLeagueService.create(team, league, startDate, endDate);
        }
        
    }
    
    private static LocalDate dateStringToLocalDate(String dateString) {
        if (dateString == null) {
            return null;
        }
        try {
            Date date = BbstatsConstants.SPREADSHEET_DATE_FORMAT.parse(dateString);
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
