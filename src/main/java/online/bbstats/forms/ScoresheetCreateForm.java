package online.bbstats.forms;

import org.hibernate.validator.constraints.NotEmpty;

public class ScoresheetCreateForm {
    @NotEmpty
    private String seasonName;
    
    @NotEmpty
    private String visitingTeamName;
    
    @NotEmpty
    private String homeTeamName;
    
    private String gameDate; 
    
    private Integer gameNumberForDate;

    public String getSeasonName() {
        return seasonName;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public String getVisitingTeamName() {
        return visitingTeamName;
    }

    public void setVisitingTeamName(String visitingTeamName) {
        this.visitingTeamName = visitingTeamName;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }


    public Integer getGameNumberForDate() {
        return gameNumberForDate;
    }

    public void setGameNumberForDate(Integer gameNumberForDate) {
        this.gameNumberForDate = gameNumberForDate;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

}
