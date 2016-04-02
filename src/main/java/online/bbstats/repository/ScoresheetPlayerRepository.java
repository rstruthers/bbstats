package online.bbstats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import online.bbstats.repository.domain.ScoresheetPlayer;

public interface ScoresheetPlayerRepository extends JpaRepository<ScoresheetPlayer, Long> {

	@Query("select sp from ScoresheetPlayer sp where :id = sp.visitorScoresheet.id "
			+ "and :lineupOrder = sp.lineupOrder "
			+ "and :lineupOrderIndex = sp.lineupOrderIndex")
	ScoresheetPlayer findVisitorByScoresheetIdLineupOrderAndIndex(@Param("id") Long id, 
			@Param("lineupOrder") Integer lineupOrder,
			@Param("lineupOrderIndex") Integer lineupOrderIndex);

	@Query("select sp from ScoresheetPlayer sp where :id = sp.visitorScoresheet.id "
			+ "and :lineupOrder = sp.lineupOrder "
			+ "order by sp.lineupOrder, sp.lineupOrderIndex")
	List<ScoresheetPlayer> findVisitorsByScoresheetIdAndLineupOrder(@Param("id") Long id, 
			@Param("lineupOrder") Integer lineupOrder);

	@Modifying
    @Transactional
	@Query("delete from ScoresheetPlayer sp where :scoresheetId = sp.visitorScoresheet.id")
	void deleteVisitorsByScoresheetId(@Param("scoresheetId") Long scoresheetId);
	
	@Query("select sp from ScoresheetPlayer sp where :scoresheetId = sp.visitorScoresheet.id "
	        + "order by sp.lineupOrder, sp.lineupOrderIndex")
	List<ScoresheetPlayer> findVisitorsByScoresheetIdOrderByPositionAndIndex(@Param("scoresheetId") Long scoresheetId);

	@Query("select count(sp) from ScoresheetPlayer sp where :scoresheetId = sp.visitorScoresheet.id "
	        + "and :lineupOrder = sp.lineupOrder")
    int getNumVisitingPlayersAtLineupOrderPosition(@Param("scoresheetId") Long scoresheetId, 
            @Param("lineupOrder") Integer lineupOrder);

	@Query("select sp from ScoresheetPlayer sp where :id = sp.homeScoresheet.id "
            + "and :lineupOrder = sp.lineupOrder "
            + "order by sp.lineupOrder, sp.lineupOrderIndex")
    List<ScoresheetPlayer> findHomePlayersByScoresheetIdAndLineupOrder(@Param("id") Long id, 
            @Param("lineupOrder") Integer lineupOrder);
	
	@Query("select sp from ScoresheetPlayer sp where :id = sp.homeScoresheet.id "
            + "and :lineupOrder = sp.lineupOrder "
            + "and :lineupOrderIndex = sp.lineupOrderIndex")
    ScoresheetPlayer findHomePlayerByScoresheetIdLineupOrderAndIndex(@Param("id") Long id, 
            @Param("lineupOrder") Integer lineupOrder,
            @Param("lineupOrderIndex") Integer lineupOrderIndex);
	
	@Query("select count(sp) from ScoresheetPlayer sp where :scoresheetId = sp.homeScoresheet.id "
            + "and :lineupOrder = sp.lineupOrder")
    int getNumHomePlayersAtLineupOrderPosition(@Param("scoresheetId") Long scoresheetId, 
            @Param("lineupOrder") Integer lineupOrder);

}
