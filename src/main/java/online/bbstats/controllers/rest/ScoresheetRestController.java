package online.bbstats.controllers.rest;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import online.bbstats.model.PlayerModel;
import online.bbstats.repository.domain.TeamPlayer;
import online.bbstats.service.RosterService;

@RestController
public class ScoresheetRestController {
    
    @Autowired
    private RosterService rosterService;
    
    @RequestMapping(value = "/bbstats/v1/teams/{teamName}/players/date/{date}", 
            method = RequestMethod.GET, 
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PlayerModel> getPlayersForTeamActiveAtDate( @PathVariable("teamName") String teamName,
           @PathVariable("date") String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M-d-yyyy");
        LocalDate gameDate = LocalDate.parse(date, formatter);
        
        List<PlayerModel> playerModelList = new ArrayList<PlayerModel>();
        
        List<TeamPlayer> teamPlayers = rosterService.findTeamPlayersByTeamNameActiveAtDate(teamName, gameDate);
        
        if (teamPlayers == null) {
            return playerModelList;
        }
        
        for (TeamPlayer teamPlayer: teamPlayers) {
            PlayerModel playerModel = new PlayerModel();
            playerModelList.add(playerModel);
            playerModel.setName(teamPlayer.getPlayer().getName());
            playerModel.setId(teamPlayer.getPlayer().getId());
        }
        
        return playerModelList;
    }
}
