package online.bbstats.forms;

import org.springframework.web.multipart.MultipartFile;

import online.bbstats.model.LeagueModel;

public class LeagueTeamsUploadForm {
    private MultipartFile file;

    private LeagueModel league;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public LeagueModel getLeague() {
        return league;
    }

    public void setLeague(LeagueModel league) {
        this.league = league;
    }

   

}
