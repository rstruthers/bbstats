package online.bbstats.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.bbstats.BbstatsConstants;
import online.bbstats.repository.TeamPlayerPositionRepository;
import online.bbstats.repository.TeamPlayerRepository;
import online.bbstats.repository.domain.Player;
import online.bbstats.repository.domain.Season;
import online.bbstats.repository.domain.Team;
import online.bbstats.repository.domain.TeamLeague;
import online.bbstats.repository.domain.TeamPlayer;
import online.bbstats.repository.domain.TeamPlayerPosition;

@Service
public class RosterService {
	
	@Autowired
	private PlayerService playerService;
	
	@Autowired
	private TeamPlayerRepository teamPlayerRepository;
	
	@Autowired
	private TeamPlayerPositionRepository teamPlayerPositionRepository;
	
	@Autowired
	private TeamLeagueService teamLeagueService;
	
	private static final DateTimeFormatter DATE_OF_BIRTH_FORMATTER =
            DateTimeFormatter.ofPattern("MMM d yyyy");
	
	public void addPlayerToRoster(Team team, Season season, Map<String, String> playerValueMap) {
		String name = playerValueMap.get("Name");
		name = StringUtils.removeEnd(name, " HOF");
		name = StringUtils.trimToNull(name);
		if (name == null) {
			System.out.println("Name is null, can't continue");
		}
		
		LocalDate dateOfBirth = null;
		try {
			dateOfBirth = LocalDate.parse(playerValueMap.get("DoB"), DATE_OF_BIRTH_FORMATTER);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		if (dateOfBirth == null) {
			System.out.println("Date of birth is null, can't continue");
			return;
		}
		
		
		Player player = playerService.findPlayerByNameAndDateOfBirth(name, dateOfBirth);
		
		if (player == null) {
			System.out.println("Unable to find player with " + name + " and date of birth " + dateOfBirth);
			player = playerService.create(name, dateOfBirth);
		} else {
			System.out.println("Found player with " + name + " and date of birth " + dateOfBirth);
		}
		
		TeamPlayer teamPlayer = addPlayerToTeam(player, team, season.getStartDate());
		
		if (teamPlayer == null) {
			System.out.println("Unable to add player to team, can't continue");
			return;
		}
		
		for (String position: BbstatsConstants.POSITIONS) {
			String countAsString = playerValueMap.get(position);
			if (countAsString == null) {
				continue;
			}
			int count = 0;
			try {
				count = (int) Double.parseDouble(countAsString);
			} catch (Exception ex) {
				//ex.printStackTrace();
			}

			if (count > 0) {
				updatePositionGameCountForTeamPlayer(teamPlayer, position, count);
			}
		}
	}
	
	public TeamPlayer addPlayerToTeam(Player player, Team team, LocalDate startDate) {
		TeamPlayer teamPlayer = teamPlayerRepository.findByTeamIdPlayerIdActiveAtDate(team.getId(), player.getId(), startDate);
		if (teamPlayer != null) {
			System.out.println("Found the teamPlayer, returning");
			return teamPlayer;
		}
		System.out.println("Did not find the teamPlayer, creating a new teamPlayer");
		return teamPlayerRepository.save(new TeamPlayer(team, player, startDate));
	}
	
	public TeamPlayerPosition updatePositionGameCountForTeamPlayer(TeamPlayer teamPlayer, String position, int numGames) {
		
		System.out.println(position + ": " + numGames);
		TeamPlayerPosition teamPlayerPosition = teamPlayerPositionRepository.findByTeamPlayerAndPosition(teamPlayer, position);
		if (teamPlayerPosition != null) {
			if (numGames != teamPlayerPosition.getNumGames()) {
				teamPlayerPosition.setNumGames(numGames);
				teamPlayerPositionRepository.save(teamPlayerPosition);
			}
		} else {
			teamPlayerPosition = new TeamPlayerPosition(teamPlayer, position, numGames);
			teamPlayerPositionRepository.save(teamPlayerPosition);
		}
		return teamPlayerPosition;
	}
	
	public List<TeamPlayer> findTeamPlayersByTeamAndSeason(Team team, Season season) {
		return teamPlayerRepository.findByTeamIdActiveAtDate(team.getId(), season.getStartDate());
	}
	
	public List<TeamPlayer> findTeamPlayersByTeamNameActiveAtDate(String teamName, LocalDate date) {
	    List<TeamPlayer> teamPlayers = new ArrayList<TeamPlayer>();
	    TeamLeague teamLeague = teamLeagueService.findByTeamNameAndActiveAtDate(teamName, date);
	    if (teamLeague == null) {
	        return teamPlayers;
	    }
	    return teamPlayerRepository.findByTeamIdActiveAtDate(teamLeague.getTeam().getId(), date);
	}

    public void deleteRoster(Team team, Season season) {
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findByTeamIdActiveAtDate(team.getId(), season.getStartDate());
        for (TeamPlayer teamPlayer: teamPlayers) {
            try {
                deleteTeamPlayerPositions(teamPlayer);
                teamPlayerRepository.delete(teamPlayer);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void deleteTeamPlayerPositions(TeamPlayer teamPlayer) {
        List<TeamPlayerPosition> teamPlayerPositions = teamPlayer.getTeamPlayerPositions();
        for (TeamPlayerPosition position: teamPlayerPositions) {
            teamPlayerPositionRepository.delete(position);
        }
    }
	
}
