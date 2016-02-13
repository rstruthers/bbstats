package online.bbstats.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import online.bbstats.model.TeamModel;
import online.bbstats.repository.domain.Season;
import online.bbstats.repository.domain.Team;
import online.bbstats.service.RosterService;
import online.bbstats.service.SeasonService;
import online.bbstats.service.TeamService;

@Controller
public class RosterUploadController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RosterUploadController.class);

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
        List<Team> teams = teamService.getAllTeams();
        List<TeamModel> teamModelList = new ArrayList<TeamModel>();
        for (Team team: teams) {
            TeamModel teamModel = new TeamModel();
            BeanUtils.copyProperties(team, teamModel);
            teamModelList.add(teamModel);
        }
        mav.addObject("seasons", seasonModelList);
        mav.addObject("teams", teamModelList);
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

        XSSFWorkbook workbook = null;
        try {
            workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()));
            XSSFSheet sheet = workbook.getSheetAt(0);
            Map<Integer, String> headerRowMap = createHeaderRowMap(sheet);

            int rows = sheet.getPhysicalNumberOfRows();
            for (int r = 2; r < rows; r++) {
                XSSFRow row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                Map<String, String> playerValueMap = createPlayerValueMap(row, headerRowMap);
                rosterService.addPlayerToRoster(team, season, playerValueMap);
            }

        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Failed to read bytes from Excel file");
            return "roster_upload";
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       
        return "redirect:/roster/view/season/" + season.getName() + "/team/" + team.getName();
    }


    private Map<String, String> createPlayerValueMap(XSSFRow row, Map<Integer, String> headerRowMap) {
        Map<String, String> playerValueMap = new HashMap<String, String>();
        int numCells = row.getPhysicalNumberOfCells();
        for (int c = 0; c < numCells; c++) {
            String value = getCellValueAsString(row, c);
            String key = headerRowMap.get(c);
            playerValueMap.put(key, value);
        }
        return playerValueMap;
    }

    private Map<Integer, String> createHeaderRowMap(XSSFSheet sheet) {
        Map<Integer, String> headerRowMap = new HashMap<Integer, String>();
        int headerRowIndex = 1;
        XSSFRow row = sheet.getRow(headerRowIndex);
        if (row == null) {
            throw new RuntimeException("No header row at row index " + headerRowIndex);
        }

        int numHeaderCells = row.getPhysicalNumberOfCells();
        for (int c = 0; c < numHeaderCells; c++) {
            String headerValue = getCellValueAsString(row, c);
            if (headerValue != null) {
                headerRowMap.put(c, headerValue);
            }
        }
        return headerRowMap;
    }

    private String getCellValueAsString(XSSFRow row, int c) {
        XSSFCell cell = row.getCell(c);
        if (cell == null) {
            return null;
        }
        String value = null;
        switch (cell.getCellType()) {

        case XSSFCell.CELL_TYPE_FORMULA:
            value = cell.getCellFormula();
            break;

        case XSSFCell.CELL_TYPE_NUMERIC:
            value = String.valueOf(cell.getNumericCellValue());
            break;

        case XSSFCell.CELL_TYPE_STRING:
            value = cell.getStringCellValue();
            break;

        default:
        }
        return value;
    }

    
}
