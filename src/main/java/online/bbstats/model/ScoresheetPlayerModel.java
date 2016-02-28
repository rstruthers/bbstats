package online.bbstats.model;

public class ScoresheetPlayerModel {
    private Long id;
    private String name;
    private String position;
    private Integer lineupOrder;
    private Integer lineupOrderIndex;

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


    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Integer getLineupOrder() {
        return lineupOrder;
    }

    public void setLineupOrder(Integer lineupOrder) {
        this.lineupOrder = lineupOrder;
    }

    public Integer getLineupOrderIndex() {
        return lineupOrderIndex;
    }

    public void setLineupOrderIndex(Integer lineupOrderIndex) {
        this.lineupOrderIndex = lineupOrderIndex;
    }

 
   
}
