package online.bbstats.forms;

import java.util.List;

import online.bbstats.model.LineupOrderModel;
import online.bbstats.model.ScoresheetPitcherModel;


public class ScoresheetForm {
    private Long id;
    private List<LineupOrderModel> visitorLineupOrders;
    private List<LineupOrderModel> homeLineupOrders;
    private List<ScoresheetPitcherModel> visitorPitchers;
    private List<ScoresheetPitcherModel> homePitchers;

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

    public List<ScoresheetPitcherModel> getVisitorPitchers() {
        return visitorPitchers;
    }

    public void setVisitorPitchers(List<ScoresheetPitcherModel> visitorPitchers) {
        this.visitorPitchers = visitorPitchers;
    }

    public List<ScoresheetPitcherModel> getHomePitchers() {
        return homePitchers;
    }

    public void setHomePitchers(List<ScoresheetPitcherModel> homePitchers) {
        this.homePitchers = homePitchers;
    }

    
}
