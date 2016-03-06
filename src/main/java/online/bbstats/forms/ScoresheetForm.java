package online.bbstats.forms;

import java.util.List;

import online.bbstats.model.LineupOrderModel;


public class ScoresheetForm {
    private Long id;
    private List<LineupOrderModel> visitorLineupOrders;
    private List<LineupOrderModel> homeLineupOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<LineupOrderModel> getVisitorLineupOrders() {
        return visitorLineupOrders;
    }

    public void setVisitorLineupOrders(List<LineupOrderModel> visitorLineupOrders) {
        this.visitorLineupOrders = visitorLineupOrders;
    }

    public List<LineupOrderModel> getHomeLineupOrders() {
        return homeLineupOrders;
    }

    public void setHomeLineupOrders(List<LineupOrderModel> homeLineupOrders) {
        this.homeLineupOrders = homeLineupOrders;
    }

    
}
