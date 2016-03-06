package online.bbstats.model;

import java.util.List;

public class LineupOrderModel {
    private Integer lineupOrderPosition;
    private List<ScoresheetPlayerModel> scoresheetPlayers;

    public List<ScoresheetPlayerModel> getScoresheetPlayers() {
        return scoresheetPlayers;
    }

    public void setScoresheetPlayers(List<ScoresheetPlayerModel> scoresheetPlayers) {
        this.scoresheetPlayers = scoresheetPlayers;
    }

    public Integer getLineupOrderPosition() {
        return lineupOrderPosition;
    }

    public void setLineupOrderPosition(Integer lineupOrderPosition) {
        this.lineupOrderPosition = lineupOrderPosition;
    }
}
