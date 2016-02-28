package online.bbstats.forms;

import java.util.List;

import online.bbstats.model.ScoresheetPlayerModel;


public class ScoresheetForm {
    private Long id;
    
    private List<ScoresheetPlayerModel> visitingPlayers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ScoresheetPlayerModel> getVisitingPlayers() {
        return visitingPlayers;
    }

    public void setVisitingPlayers(List<ScoresheetPlayerModel> visitingPlayers) {
        this.visitingPlayers = visitingPlayers;
    }
  
}
