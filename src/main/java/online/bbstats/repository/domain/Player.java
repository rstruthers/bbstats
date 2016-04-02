package online.bbstats.repository.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "player")
public class Player {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, updatable = false)
	private Long id;

	@Column(name = "name")
	private String name;
	
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	
	@OneToMany(mappedBy="player")
	private List<TeamPlayer> teamPlayers;
	
	@OneToMany(mappedBy="player")
    private List<ScoresheetPlayer> scoresheetPlayers;
	
	@OneToMany(mappedBy="player")
    private List<ScoresheetPlayer> scoresheetPitchers;

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

	public List<TeamPlayer> getTeamPlayers() {
		return teamPlayers;
	}

	public void setTeamPlayers(List<TeamPlayer> teamPlayers) {
		this.teamPlayers = teamPlayers;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getFormattedDateOfBirth() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		if (dateOfBirth == null) {
			return "";
		}
		return formatter.format(dateOfBirth);
	}

    public List<ScoresheetPlayer> getScoresheetPlayers() {
        return scoresheetPlayers;
    }

    public void setScoresheetPlayers(List<ScoresheetPlayer> scoresheetPlayers) {
        this.scoresheetPlayers = scoresheetPlayers;
    }

    public List<ScoresheetPlayer> getScoresheetPitchers() {
        return scoresheetPitchers;
    }

    public void setScoresheetPitchers(List<ScoresheetPlayer> scoresheetPitchers) {
        this.scoresheetPitchers = scoresheetPitchers;
    }

}
