package online.bbstats.domain;

public class Tweet {
	private String text;
	private User user;
	
	public Tweet() {
		
	}
	
	public Tweet(String text) {
		this.text = text;
	}
	
	public Tweet(String text, User user) {
		this.text = text;
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	
}
