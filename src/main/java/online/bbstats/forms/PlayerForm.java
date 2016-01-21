package online.bbstats.forms;


import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.BeanUtils;

import online.bbstats.repository.domain.Player;
import online.bbstats.repository.domain.Position;
import online.bbstats.repository.domain.Team;

public class PlayerForm {
	private Long id;
	
	@NotEmpty
	private String name;
	
	@Range(min=15, max=100)
	private Integer age;
	
	
	private Position position;
	
	private Team team;
	
	public PlayerForm() {
		
	}

	public PlayerForm(Player player) {
		if (player != null) {
			System.out.println("PlayerForm(player), player.position = " + player.getPosition());
			BeanUtils.copyProperties(player, this);
			System.out.println("After copy, playerForm.position = " + this.getPosition());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
