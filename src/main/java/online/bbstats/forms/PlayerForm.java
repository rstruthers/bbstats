package online.bbstats.forms;


import java.time.LocalDate;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import online.bbstats.repository.domain.Player;

public class PlayerForm {
	private Long id;
	
	@NotEmpty
	private String name;
	
	@DateTimeFormat (pattern="dd/MM/YYYY")
	private LocalDate dateOfBirth;
	
	public PlayerForm() {
		
	}

	public PlayerForm(Player player) {
		if (player != null) {
			BeanUtils.copyProperties(player, this);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


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
