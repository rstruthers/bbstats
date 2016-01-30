package online.bbstats.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;

public class UserForm {
	private Long id;

	@NotEmpty
    private String email = "";

    private String password = "";

    private String passwordRepeated = "";

    private Role role = Role.USER;
    
    public UserForm() {
    	
    }

    public UserForm(User user) {
		if (user != null) {
			BeanUtils.copyProperties(user, this);
		}
	}

	public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeated() {
        return passwordRepeated;
    }

    public void setPasswordRepeated(String passwordRepeated) {
        this.passwordRepeated = passwordRepeated;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserCreateForm{" +
                "email='" + email.replaceFirst("@.+", "@***") + '\'' +
                ", password=***" + '\'' +
                ", passwordRepeated=***" + '\'' +
                ", role=" + role +
                '}';
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}