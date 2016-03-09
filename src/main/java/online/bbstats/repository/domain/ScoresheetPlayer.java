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
@Table(name = "scoresheet_player")
public class ScoresheetPlayer {
    
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
    
    @Column(name = "lineup_order")
    private Integer lineupOrder;
    
    @Column(name = "lineup_order_index")
    private Integer lineupOrderIndex;
    
    @Column(name = "at_bats")
    private Integer atBats;
    
    @Column(name = "runs")
    private Integer runs;
    
    @Column(name = "hits")
    private Integer hits;
    
    @Column(name = "rbi")
    private Integer rbi;
    
    @Column(name = "doubles")
    private Integer doubles;
    
    @Column(name = "triples")
    private Integer triples;
    
    @Column(name = "homeruns")
    private Integer homeruns;
    
    @Column(name = "stolen_bases")
    private Integer stolenBases;
    
    public ScoresheetPlayer() {
        
    }

    public ScoresheetPlayer(Scoresheet visitorScoresheet, Scoresheet homeScoresheet, int lineupOrder, int lineupOrderIndex, Player player) {
       this.visitorScoresheet = visitorScoresheet;
       this.homeScoresheet = homeScoresheet;
       this.lineupOrder = lineupOrder;
       this.lineupOrderIndex = lineupOrderIndex;
       this.player = player;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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

    public Integer getDoubles() {
        return doubles;
    }

    public void setDoubles(Integer doubles) {
        this.doubles = doubles;
    }

    public Integer getTriples() {
        return triples;
    }

    public void setTriples(Integer triples) {
        this.triples = triples;
    }

    public Integer getHomeruns() {
        return homeruns;
    }

    public void setHomeruns(Integer homeruns) {
        this.homeruns = homeruns;
    }

    public Integer getStolenBases() {
        return stolenBases;
    }

    public void setStolenBases(Integer stolenBases) {
        this.stolenBases = stolenBases;
    }

    public Integer getAtBats() {
        return atBats;
    }

    public void setAtBats(Integer atBats) {
        this.atBats = atBats;
    }

    public Integer getRuns() {
        return runs;
    }

    public void setRuns(Integer runs) {
        this.runs = runs;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getRbi() {
        return rbi;
    }

    public void setRbi(Integer rbi) {
        this.rbi = rbi;
    }

}
