package online.bbstats.repository.domain;

public enum Position {
	NO_POSITION("position.none"),
	CATCHER("position.c"),
	FIRST_BASE("position.1b"),
	SECOND_BASE("position.2b"),
	SHORT_STOP("position.ss"),
	THIRD_BASE("position.3b"),
	LEFT_FIELD("position.lf"),
	CENTER_FIELD("position.cf"),
	RIGHT_FIELD("position.rf"),
	UTILITY("position.ut"),
	PITCHER("position.p"),
	OUT_FIELD("position.of");
	
	private String value;
	
	 
    private Position(String value) {
        this.value = value;
    }
 
    public String getValue() {
        return value;
    }
	
	
}
