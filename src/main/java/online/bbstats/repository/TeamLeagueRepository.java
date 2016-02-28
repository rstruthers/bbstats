package online.bbstats.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import online.bbstats.repository.domain.TeamLeague;

public interface TeamLeagueRepository extends JpaRepository<TeamLeague, Long> {
    
    @Query("select tl from TeamLeague tl where :date >= tl.startDate "
            + "and (tl.endDate is null or :date <= tl.endDate) order by tl.team.location, tl.team.name asc")
    List<TeamLeague> findByActiveAtDate(@Param("date") LocalDate date);
    
    @Query("select tl from TeamLeague tl "
            + "where :teamId = tl.team.id "
            + "and :leagueId = tl.league.id "
            + "and :date >= tl.startDate "
            + "and (tl.endDate is null or :date <= tl.endDate) order by tl.team.location, tl.team.name asc")
    TeamLeague findByTeamIdLeagueIdAndActiveAtDate(@Param("teamId") Long teamId, @Param("leagueId") Long leagueId, @Param("date") LocalDate date);

    @Query("select tl from TeamLeague tl "
            + "where :teamName = tl.team.name "
            + "and :date >= tl.startDate "
            + "and (tl.endDate is null or :date <= tl.endDate) order by tl.team.location, tl.team.name asc")
    TeamLeague findByTeamNameAndActiveAtDate(@Param("teamName") String teamName, @Param("date") LocalDate date);
}
