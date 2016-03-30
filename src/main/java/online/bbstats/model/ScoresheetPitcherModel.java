package online.bbstats.model;

public class ScoresheetPitcherModel {
    private Long playerId;
    private String name;
    private Integer pitcherOrder;
    private String wholePlusPartialInningsPitched;
    private Integer hits;
    private Integer runs;
    private Integer earnedRuns;
    private Integer walks;
    private Integer strikeouts;
    private Integer homeruns;
    private Integer balks;
    private Boolean win;
    private Boolean loss;
    private Boolean save;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getEarnedRuns() {
        return earnedRuns;
    }

    public void setEarnedRuns(Integer earnedRuns) {
        this.earnedRuns = earnedRuns;
    }

    public Integer getWalks() {
        return walks;
    }

    public void setWalks(Integer walks) {
        this.walks = walks;
    }

    public Integer getStrikeouts() {
        return strikeouts;
    }

    public void setStrikeouts(Integer strikeouts) {
        this.strikeouts = strikeouts;
    }

    public Integer getHomeruns() {
        return homeruns;
    }

    public void setHomeruns(Integer homeruns) {
        this.homeruns = homeruns;
    }

    public Integer getBalks() {
        return balks;
    }

    public void setBalks(Integer balks) {
        this.balks = balks;
    }

    public Boolean getWin() {
        return win;
    }

    public void setWin(Boolean win) {
        this.win = win;
    }

    public Boolean getLoss() {
        return loss;
    }

    public void setLoss(Boolean loss) {
        this.loss = loss;
    }

    public Boolean getSave() {
        return save;
    }

    public void setSave(Boolean save) {
        this.save = save;
    }

    public Integer getPitcherOrder() {
        return pitcherOrder;
    }

    public void setPitcherOrder(Integer pitcherOrder) {
        this.pitcherOrder = pitcherOrder;
    }

    public String getWholePlusPartialInningsPitched() {
        return wholePlusPartialInningsPitched;
    }

    public void setWholePlusPartialInningsPitched(String wholePlusPartialInningsPitched) {
        this.wholePlusPartialInningsPitched = wholePlusPartialInningsPitched;
    }
}
