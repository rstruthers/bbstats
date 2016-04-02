package online.bbstats.model;

public class PlayerModel {
    private Long id;
    private String name;
    private String primaryPosition;

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

    public String getPrimaryPosition() {
        return primaryPosition;
    }

    public void setPrimaryPosition(String primaryPosition) {
        this.primaryPosition = primaryPosition;
    }
   
}
