package online.bbstats.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import online.bbstats.repository.domain.Scoresheet;

public interface ScoresheetRepository extends JpaRepository<Scoresheet, Long> {
    @Query("select s from Scoresheet s "
            + "where :visitingTeamName = s.visitingTeam.name "
            + "and :homeTeamName = s.homeTeam.name "
            + "and :gameDate = s.gameDate")
    Scoresheet findByTeamsAndDate(@Param("visitingTeamName") String visitingTeamName, 
            @Param("homeTeamName") String homeTeamName, 
            @Param("gameDate") LocalDate gameDate);
}
