package online.bbstats.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

import online.bbstats.repository.domain.League;

public class LeagueForm {
	private Long id;

	@NotEmpty
	private String name;
	
	public LeagueForm() {
		
	}
	


	public LeagueForm(League league) {
		if (league != null) {
			BeanUtils.copyProperties(league, this);
		}
	}



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
}
