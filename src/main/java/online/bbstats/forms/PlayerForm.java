package online.bbstats.forms;


import java.time.LocalDate;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import online.bbstats.repository.domain.Player;
import online.bbstats.repository.domain.Position;

public class PlayerForm {
	private Long id;
	
	@NotEmpty
	private String name;
	
	private Position position;
	
	//@DateTimeFormat(iso = ISO.DATE)
	@DateTimeFormat (pattern="dd/MM/YYYY")
	private LocalDate dateOfBirth;
	
	//private Team team;
	
	
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


	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

//	public Team getTeam() {
//		return team;
//	}
//
//	public void setTeam(Team team) {
//		this.team = team;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
}
