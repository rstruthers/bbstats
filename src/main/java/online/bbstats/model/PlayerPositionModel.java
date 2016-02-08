package online.bbstats.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PlayerPositionModel {
    private String name;
    private LocalDate dateOfBirth;
    private Map<String, Integer> positionMap;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Map<String, Integer> getPositionMap() {
        return positionMap;
    }

    public void setPositionMap(Map<String, Integer> positionMap) {
        this.positionMap = positionMap;
    }
    
    public String getFormattedDateOfBirth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        if (dateOfBirth == null) {
            return "";
        }
        return formatter.format(dateOfBirth);
    }
}
