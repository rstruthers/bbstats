package online.bbstats.forms;

import java.util.List;

import online.bbstats.model.LineupOrderModel;


public class ScoresheetForm {
    private Long id;
    
    private List<LineupOrderModel> lineupOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LineupOrderModel> getLineupOrders() {
        return lineupOrders;
    }

    public void setLineupOrders(List<LineupOrderModel> lineupOrders) {
        this.lineupOrders = lineupOrders;
    }

  

}
