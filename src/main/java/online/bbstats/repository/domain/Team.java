package online.bbstats.repository.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "location")
    private String location;

    @OneToMany(mappedBy = "team")
    private List<TeamPlayer> teamPlayers;

    @OneToMany(mappedBy = "team")
    private List<TeamLeague> teamLeagues;
    
    @OneToMany
    @JoinColumn(name="visiting_team_id")
    private List<Scoresheet> scoresheetsAsVisitingTeam;
    
    @OneToMany
    @JoinColumn(name="home_team_id")
    private List<Scoresheet> scoresheetsAsHomeTeam;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<TeamPlayer> getTeamPlayers() {
        return teamPlayers;
    }

    public void setTeamPlayers(List<TeamPlayer> teamPlayers) {
        this.teamPlayers = teamPlayers;
    }

    public List<TeamLeague> getTeamLeagues() {
        return teamLeagues;
    }

    public void setTeamLeagues(List<TeamLeague> teamLeagues) {
        this.teamLeagues = teamLeagues;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
