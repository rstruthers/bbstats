package online.bbstats.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.forms.LeagueTeamsUploadForm;
import online.bbstats.model.LeagueModel;
import online.bbstats.repository.domain.League;
import online.bbstats.service.LeagueService;
import online.bbstats.spreadsheet.SpreadsheetWrapper;

@Controller
public class LeagueTeamsUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeagueTeamsUploadController.class);
    
    private static final int HEADER_ROW_INDEX = 1;
    
    @Autowired
    private LeagueService leagueService;
    
    @RequestMapping(value = "/league/teams/upload", method = RequestMethod.GET)
    public ModelAndView getLeagueTeamsUploadForm() {
        LOGGER.debug("Gettng league teams upload form");
        
        ModelAndView mav = new ModelAndView("league_teams_upload");
        mav.addObject("form", new LeagueTeamsUploadForm());
        
        List<League> leagues = leagueService.findAllLeagues();
        List<LeagueModel> leagueModelList = new ArrayList<LeagueModel>();
        
        for (League league : leagues) {
            LeagueModel leagueModel = new LeagueModel();
            BeanUtils.copyProperties(league, leagueModel);
            leagueModelList.add(leagueModel);
        }
        
        mav.addObject("leagues", leagueModelList);
        return mav;
    }
    
    @RequestMapping(value = "/league/teams/upload", method = RequestMethod.POST)
    public String postLeagueTeamsUploadForm(@RequestParam(name="file") MultipartFile file, @RequestParam(name="league") String leagueName) {
        LOGGER.debug("Uploading league teams");
        
        if (file == null) {
            LOGGER.debug("File is null");
            return "redirect:/league/teams/upload";
        }
        String name = file.getOriginalFilename();
        LOGGER.debug("File name: " + name);
        if (file.isEmpty()) {
            LOGGER.debug("File is empty.");
            return "redirect:/league/teams/uploadd";
        }

        try {
            LOGGER.debug("You successfully uploaded " + name + "!");
        } catch (Exception e) {
            LOGGER.error("You failed to upload " + name + " => " + e.getMessage());
            return "redirect:/league/teams/upload";
        }
        
        SpreadsheetWrapper spreadsheetWrapper = null;
        try {
            spreadsheetWrapper = new SpreadsheetWrapper(file, HEADER_ROW_INDEX);
            spreadsheetWrapper.open();
            spreadsheetWrapper.readRowRecords();
            List<Map<String, String>> teamValueMaps = spreadsheetWrapper.getRecordValueMaps();
            for (Map<String, String> teamValueMap: teamValueMaps) {
                System.out.println(teamValueMap);
                leagueService.addTeam(leagueName, teamValueMap);
            }
        } catch (IOException e) {
            LOGGER.error("You failed to upload " + name + " => " + e.getMessage());
            return "redirect:/league/teams/upload";
        } finally {
            if (spreadsheetWrapper != null) {
                try {
                    spreadsheetWrapper.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
        return "redirect:/teamleagues";
    }
    
    
}
