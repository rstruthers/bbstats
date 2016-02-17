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

import online.bbstats.forms.RosterUploadForm;
import online.bbstats.model.SeasonModel;
import online.bbstats.repository.domain.Season;
import online.bbstats.repository.domain.Team;
import online.bbstats.service.RosterService;
import online.bbstats.service.SeasonService;
import online.bbstats.service.TeamService;
import online.bbstats.spreadsheet.SpreadsheetWrapper;

@Controller
public class RosterUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RosterUploadController.class);
    
    private static final int HEADER_ROW_INDEX = 1;

    @Autowired
    private RosterService rosterService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private SeasonService seasonService;

    @RequestMapping(value = "/roster/upload", method = RequestMethod.GET)
    public ModelAndView getRosterUploadForm() {
        LOGGER.debug("Getting roster upload form");
        ModelAndView mav = new ModelAndView("roster_upload");
        mav.addObject("form", new RosterUploadForm());
        List<Season> seasons = seasonService.findAllSeasons();
        List<SeasonModel> seasonModelList = new ArrayList<SeasonModel>();
        for (Season season: seasons) {
            SeasonModel seasonModel = new SeasonModel();
            BeanUtils.copyProperties(season, seasonModel);
            seasonModelList.add(seasonModel);
        }
        mav.addObject("seasons", seasonModelList);
        return mav;
    }

    @RequestMapping(value = "/roster/upload", method = RequestMethod.POST)
    public String postRosterUploadForm(@RequestParam(name="file") MultipartFile file, @RequestParam(name="team") String teamName,
            @RequestParam(name="season") String seasonName) {
        LOGGER.debug("Uploading roster");

        if (file == null) {
            LOGGER.debug("File is null");
            return "roster_upload";
        }
        String name = file.getOriginalFilename();
        LOGGER.debug("File name: " + name);
        if (file.isEmpty()) {
            LOGGER.debug("File is empty.");
            return "roster_upload";
        }

        try {
            LOGGER.debug("You successfully uploaded " + name + "!");
        } catch (Exception e) {
            LOGGER.error("You failed to upload " + name + " => " + e.getMessage());
            return "roster_upload";
        }

        Team team = teamService.findTeamByName(teamName);
        Season season = seasonService.findSeasonByName(seasonName);
        SpreadsheetWrapper spreadsheetWrapper = null;

        try {
            spreadsheetWrapper = new SpreadsheetWrapper(file, HEADER_ROW_INDEX);
            spreadsheetWrapper.open();
            spreadsheetWrapper.readRowRecords();
            List<Map<String, String>> playerValueMaps = spreadsheetWrapper.getRecordValueMaps();
            for (Map<String, String> playerValueMap: playerValueMaps) {
                rosterService.addPlayerToRoster(team, season, playerValueMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Failed to read bytes from Excel file");
            return "roster_upload";
        } finally {
            if (spreadsheetWrapper != null) {
                try {
                    spreadsheetWrapper.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
       
        return "redirect:/roster/view/season/" + season.getName() + "/team/" + team.getName();
    }
    
}
