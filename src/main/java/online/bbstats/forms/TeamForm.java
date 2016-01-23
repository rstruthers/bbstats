package online.bbstats.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import online.bbstats.repository.domain.League;
import online.bbstats.repository.domain.Team;

public class TeamForm {
	private Long id;
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String city;
	
	@NotEmpty
	private String state;
	
	private League league;
	
	public TeamForm() {
		
	}

	public TeamForm(Team team) {
		if (team != null) {
			BeanUtils.copyProperties(team, this);
		}
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}


}
