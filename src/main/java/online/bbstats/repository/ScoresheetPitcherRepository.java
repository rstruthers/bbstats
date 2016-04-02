package online.bbstats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import online.bbstats.repository.domain.ScoresheetPitcher;

public interface ScoresheetPitcherRepository extends JpaRepository<ScoresheetPitcher, Long> {

    @Query("select sp from ScoresheetPitcher sp where :scoresheetId = sp.visitorScoresheet.id "
            + "order by sp.pitcherOrder")
    List<ScoresheetPitcher> findVisitorPitchersByScoresheetId(@Param("scoresheetId") Long scoresheetId);

    @Query("select sp from ScoresheetPitcher sp where :scoresheetId = sp.homeScoresheet.id "
            + "order by sp.pitcherOrder")
    List<ScoresheetPitcher> findHomePitchersByScoresheetId(@Param("scoresheetId") Long scoresheetId);

    @Query("select sp from ScoresheetPitcher sp where :scoresheetId = sp.visitorScoresheet.id "
            + "and :pitcherOrder = sp.pitcherOrder")
    ScoresheetPitcher findVisitorPitcherByScoresheetIdAndPitcherOrder(@Param("scoresheetId") Long scoresheetId, 
            @Param("pitcherOrder") Integer pitcherOrder);

    @Query("select sp from ScoresheetPitcher sp where :scoresheetId = sp.homeScoresheet.id "
            + "and :pitcherOrder = sp.pitcherOrder")
    ScoresheetPitcher findHomePitcherByScoresheetIdAndPitcherOrder(@Param("scoresheetId") Long scoresheetId, 
            @Param("pitcherOrder") Integer pitcherOrder);
    
    @Query("select count(sp) from ScoresheetPitcher sp "
            + "where :scoresheetId = sp.visitorScoresheet.id")
    int getNumVisitorPitchers(@Param("scoresheetId") Long scoresheetId);
    
    @Query("select count(sp) from ScoresheetPitcher sp "
            + "where :scoresheetId = sp.homeScoresheet.id")
    int getNumHomePitchers(@Param("scoresheetId") Long scoresheetId);
}
