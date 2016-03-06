package online.bbstats.repository.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "scoresheet")
public class Scoresheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "season_id")
    private Season season;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visiting_team_id")
    private Team visitingTeam;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="home_team_id")
    private Team homeTeam;
    
    @Column(name = "game_date")
    private LocalDate gameDate;
    
    @Column(name = "game_number")
    private Integer gameNumber;
    
    @OneToMany
    @JoinColumn(name="v_scoresheet_player_id")
    private List<ScoresheetPlayer> playersOnVisitingTeam;
    
    @OneToMany
    @JoinColumn(name="h_scoresheet_player_id")
    private List<ScoresheetPlayer> playersOnHomeTeam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Team getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(Team visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public LocalDate getGameDate() {
        return gameDate;
    }

    public void setGameDate(LocalDate gameDate) {
        this.gameDate = gameDate;
    }

    public Integer getGameNumber() {
        return gameNumber;
    }

    public void setGameNumber(Integer gameNumber) {
        this.gameNumber = gameNumber;
    }
    
    public String getFormattedGameDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (gameDate == null) {
            return "";
        }
        return formatter.format(gameDate);
    }
    
    public String getFindScoresheetLink() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String dateString = formatter.format(gameDate);
        return "/scoresheet/visitor/" + visitingTeam.getName() + "/home/" + homeTeam.getName() + "/date/" + dateString;
    }

    public List<ScoresheetPlayer> getPlayersOnVisitingTeam() {
        return playersOnVisitingTeam;
    }

    public void setPlayersOnVisitingTeam(List<ScoresheetPlayer> playersOnVisitingTeam) {
        this.playersOnVisitingTeam = playersOnVisitingTeam;
    }

    public List<ScoresheetPlayer> getPlayersOnHomeTeam() {
        return playersOnHomeTeam;
    }

    public void setPlayersOnHomeTeam(List<ScoresheetPlayer> playersOnHomeTeam) {
        this.playersOnHomeTeam = playersOnHomeTeam;
    }
    
//    public ScoresheetPlayer findVisitingScoresheetPlayerByLineupOrderAndIndex(Integer lineupOrder) {
//    	return findScoresheetPlayer(playersOnVisitingTeam, lineupOrder);
//    }
//    
//    private static ScoresheetPlayer findScoresheetPlayer(List<ScoresheetPlayer> playersFromDb, Integer lineupOrder) {
//		if (lineupOrder == null || playersFromDb == null) {
//			return null;
//		}
//		for (ScoresheetPlayer playerFromDb: playersFromDb) {
//			if (lineupOrder.equals(playerFromDb.getLineupOrder())) {
//				return playerFromDb;
//			}
//		}
//		return null;
//	}
    

}
