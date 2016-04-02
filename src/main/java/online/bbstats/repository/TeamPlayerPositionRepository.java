package online.bbstats.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import online.bbstats.repository.domain.TeamPlayer;
import online.bbstats.repository.domain.TeamPlayerPosition;

public interface TeamPlayerPositionRepository extends JpaRepository<TeamPlayerPosition, Long> {
	
	TeamPlayerPosition findByTeamPlayerAndPosition(TeamPlayer teamPlayer, String position);
	
}
