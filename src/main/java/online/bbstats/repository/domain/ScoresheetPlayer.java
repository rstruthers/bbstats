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
    @JoinColumn(name = "player_id")
    private Player player;
    
    @Column(name = "lineup_order")
    private Integer lineupOrder;
    
    @Column(name = "lineup_order_index")
    private Integer lineupOrderIndex;
    
    public ScoresheetPlayer() {
        
    }

    public ScoresheetPlayer(Scoresheet visitorScoresheet, int lineupOrder, int lineupOrderIndex, Player player) {
       this.visitorScoresheet = visitorScoresheet;
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

    

}