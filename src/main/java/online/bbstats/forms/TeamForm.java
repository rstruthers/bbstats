package online.bbstats.forms;

import org.hibernate.validator.constraints.NotEmpty;

public class TeamForm {
	
	@NotEmpty
	private String name;
	
	@NotEmpty
	private String city;
	
	@NotEmpty
	private String state;

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


}
