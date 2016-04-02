package online.bbstats.forms;

import org.springframework.web.multipart.MultipartFile;

import online.bbstats.model.SeasonModel;
import online.bbstats.model.TeamModel;

public class RosterUploadForm {
	private MultipartFile file;
	
	private SeasonModel season;
	
	private TeamModel team;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

    public SeasonModel getSeason() {
        return season;
    }

    public void setSeason(SeasonModel season) {
        this.season = season;
    }

    public TeamModel getTeam() {
        return team;
    }

    public void setTeam(TeamModel team) {
        this.team = team;
    }
}
