package online.bbstats.controllers;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import online.bbstats.forms.RosterUploadForm;
import online.bbstats.repository.domain.Season;
import online.bbstats.repository.domain.Team;
import online.bbstats.service.RosterService;
import online.bbstats.service.SeasonService;
import online.bbstats.service.TeamService;

@Controller
public class RosterUploadController {
	private static final Logger LOGGER = LoggerFactory.getLogger(TeamController.class);

	private static String UPLOAD_LOCATION = "C:/dev-tools/mytemp/";
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private TeamService teamService;
	
	@Autowired 
	SeasonService seasonService;

	@RequestMapping(value = "/roster/upload", method = RequestMethod.GET)
	public ModelAndView getRosterUploadForm() {
		LOGGER.debug("Getting roster upload form");
		return new ModelAndView("roster_upload", "form", new RosterUploadForm());
	}

	@RequestMapping(value = "/roster/upload", method = RequestMethod.POST)
	public ModelAndView postRosterUploadForm(@ModelAttribute("form") RosterUploadForm form) {
		LOGGER.debug("Uploading roster");

		MultipartFile file = form.getFile();
		if (file == null) {
			LOGGER.debug("File is null");
			return new ModelAndView("roster_upload");
		}
		String name = file.getOriginalFilename();
		LOGGER.debug("File name: " + name);
		if (file.isEmpty()) {
			LOGGER.debug("File is empty.");
			return new ModelAndView("roster_upload");
		}

		try {
			LOGGER.debug("You successfully uploaded " + name + "!");
		} catch (Exception e) {
			LOGGER.error("You failed to upload " + name + " => " + e.getMessage());
			return new ModelAndView("roster_upload");
		}
		
		Team team = teamService.findTeamByName("Orioles");
		Season season = seasonService.findSeasonByName("1961");
		

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
			// TODO Auto-generated catch block
			e.printStackTrace();
			LOGGER.error("Failed to read bytes into Excel file");
			return new ModelAndView("roster_upload");
		} finally {

			try {
				workbook.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new ModelAndView("roster_upload");
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

	@RequestMapping(value = "/roster/uploadold", method = RequestMethod.POST)
	public @ResponseBody String handleFileUpload(@RequestParam("file") MultipartFile file) {
		System.out.println("/roster/upload post");
		String name = "unknown";
		if (file == null) {
			System.out.println(">>>> file is null");
		} else {
			name = file.getOriginalFilename();
		}
		if (!file.isEmpty()) {
			System.out.println("File is not empty!!!!");

			System.out.println("file name: " + name);
			try {
				byte[] bytes = file.getBytes();
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(new File(UPLOAD_LOCATION, name)));
				stream.write(bytes);
				stream.close();
				return "You successfully uploaded " + name + "!";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		} else {
			return "You failed to upload " + name + " because the file was empty.";
		}
	}
}
