package online.bbstats.repository.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "scoresheet_pitcher")
public class ScoresheetPitcher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "v_scoresheet_player_id")
    private Scoresheet visitorScoresheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "h_scoresheet_player_id")
    private Scoresheet homeScoresheet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;
    
    @Column(name = "pitcher_order")
    private Integer pitcherOrder;

    @Column(name = "innings_pitched")
    private Integer inningsPitched;
    
    @Column(name = "partial_innings_pitched")
    private Integer partialInningsPitched;
    
    @Column(name = "hits")
    private Integer hits;
    
    @Column(name = "runs")
    private Integer runs;
    
    @Column(name = "earnedRuns")
    private Integer earnedRuns;
    
    @Column(name = "walks")
    private Integer walks;
    
    @Column(name = "strikeouts")
    private Integer strikeouts;
    
    @Column(name = "homeruns")
    private Integer homeruns;
    
    @Column(name = "balks")
    private Integer balks;
    
    @Column(name = "win")
    private Boolean win;
    
    @Column(name = "loss")
    private Boolean loss;
    
    @Column(name = "save")
    private Boolean save;
    
    public ScoresheetPitcher() {
        
    }
    
    public ScoresheetPitcher(Scoresheet visitorScoresheet, Scoresheet homeScoresheet, Integer pitcherOrder) {
        setVisitorScoresheet(visitorScoresheet);
        setHomeScoresheet(homeScoresheet);
        setPitcherOrder(pitcherOrder);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Scoresheet getVisitorScoresheet() {
        return visitorScoresheet;
    }

    public void setVisitorScoresheet(Scoresheet visitorScoresheet) {
        this.visitorScoresheet = visitorScoresheet;
    }

    public Scoresheet getHomeScoresheet() {
        return homeScoresheet;
    }

    public void setHomeScoresheet(Scoresheet homeScoresheet) {
        this.homeScoresheet = homeScoresheet;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public Integer getPitcherOrder() {
        return pitcherOrder;
    }

    public void setPitcherOrder(Integer pitcherOrder) {
        this.pitcherOrder = pitcherOrder;
    }

    public Integer getInningsPitched() {
        return inningsPitched;
    }

    public void setInningsPitched(Integer inningsPitched) {
        this.inningsPitched = inningsPitched;
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

    public Integer getPartialInningsPitched() {
        return partialInningsPitched;
    }

    public void setPartialInningsPitched(Integer partialInningsPitched) {
        this.partialInningsPitched = partialInningsPitched;
    }

}
