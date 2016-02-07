package online.bbstats.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import online.bbstats.repository.domain.TeamPlayer;

public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {
	
	@Query("select tp from TeamPlayer tp where tp.team.id = :teamId and tp.player.id = :playerId and :date >= tp.startDate "
			+ "and (tp.endDate is null or :date <= tp.endDate)")
	TeamPlayer findByTeamIdPlayerIdActiveAtDate(@Param("teamId") Long teamId, @Param("playerId") Long playerId, @Param("date") LocalDate date);
}
