package online.bbstats.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TeamModel {
    
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    
    private Long id;
    private String name;
    private String location;
    private String league;
    private LocalDate startDate;
    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public String getFormattedStartDate() {
        if (startDate == null) {
            return "";
        } else {
            return formatter.format(startDate);
        }
    }
    
    public String getFormattedEndDate() {
        if (endDate == null) {
            return "";
        } else {
            return formatter.format(endDate);
        }
    }

}
