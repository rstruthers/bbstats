package online.bbstats.repository.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	
//	@OneToMany(mappedBy="team")
//	private List<Player> players;
	
	@OneToMany(mappedBy="team")
	private List<TeamPlayer> teamPlayers;
	
	@OneToMany(mappedBy="team")
    private List<TeamLeague> teamLeagues;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "league_id")
//	private League league;
	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "season_team",
//	    joinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"),
//	    inverseJoinColumns = @JoinColumn(name = "season_id", referencedColumnName = "id"))
//	private Set<Season> seasons = new HashSet<Season>();

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

//	public List<Player> getPlayers() {
//		return players;
//	}
//
//	public void setPlayers(List<Player> players) {
//		this.players = players;
//	}

//	public League getLeague() {
//		return league;
//	}
//
//	public void setLeague(League league) {
//		this.league = league;
//	}

//	public Set<Season> getSeasons() {
//		return seasons;
//	}
//
//	public void setSeasons(Set<Season> seasons) {
//		this.seasons = seasons;
//	}

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
}
