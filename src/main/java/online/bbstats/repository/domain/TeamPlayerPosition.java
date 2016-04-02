package online.bbstats.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "team_player_pos")
public class TeamPlayerPosition {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;
	
	@Column(name = "position")
	private String position;
	
	@Column(name = "num_games")
	private Integer numGames;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "team_player_id")
	private TeamPlayer teamPlayer;
	
	public TeamPlayerPosition() {
		
	}
	
	public TeamPlayerPosition(TeamPlayer teamPlayer, String position, Integer numGames) {
		this.teamPlayer = teamPlayer;
		this.position = position;
		this.numGames = numGames;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}


	public TeamPlayer getTeamPlayer() {
		return teamPlayer;
	}

	public void setTeamPlayer(TeamPlayer teamPlayer) {
		this.teamPlayer = teamPlayer;
	}

    public Integer getNumGames() {
        return numGames;
    }

    public void setNumGames(Integer numGames) {
        this.numGames = numGames;
    }
	
	
	
	
}
