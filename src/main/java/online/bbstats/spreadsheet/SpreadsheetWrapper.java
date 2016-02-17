package online.bbstats.spreadsheet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import online.bbstats.BbstatsConstants;

public class SpreadsheetWrapper {
    

    private MultipartFile file;
    private XSSFWorkbook workbook;
    private int headerRowIndex;
    private Map<Integer, String> headerRowMap;
    private XSSFSheet sheet;
    private List<Map<String, String>> recordValueMaps;

    public SpreadsheetWrapper(MultipartFile file, int headerRowIndex) throws IOException {
        this.file = file;
        this.headerRowIndex = headerRowIndex;

    }

    public void open() throws IOException {
        this.workbook = new XSSFWorkbook(new ByteArrayInputStream(file.getBytes()));
        this.sheet = workbook.getSheetAt(0);
        this.headerRowMap = createHeaderRowMap(sheet, headerRowIndex);
    }

    public void close() throws IOException {
        if (workbook != null) {
            workbook.close();
        }
    }

    public void readRowRecords() {
        int rows = sheet.getPhysicalNumberOfRows();
        recordValueMaps = new ArrayList<Map<String, String>>();
        for (int r = headerRowIndex + 1; r < rows; r++) {
            XSSFRow row = sheet.getRow(r);
            if (row == null) {
                continue;
            }
            recordValueMaps.add(createRecordValueMap(row));
        }
    }

    private static Map<Integer, String> createHeaderRowMap(XSSFSheet sheet, int headerRowIndex) {
        Map<Integer, String> headerRowMap = new HashMap<Integer, String>();
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

    private static String getCellValueAsString(XSSFRow row, int c) {
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
            if (DateUtil.isCellDateFormatted(cell)) {
                value = BbstatsConstants.SPREADSHEET_DATE_FORMAT.format(cell.getDateCellValue());
            } else {
                value = String.valueOf(cell.getNumericCellValue());
            }
            break;
        case XSSFCell.CELL_TYPE_STRING:
            value = cell.getStringCellValue();
            break;
        default:
        }
        return value;
    }

    private Map<String, String> createRecordValueMap(XSSFRow row) {
        Map<String, String> recordValueMap = new HashMap<String, String>();
        int numCells = row.getPhysicalNumberOfCells();
        for (int c = 0; c < numCells; c++) {
            String value = SpreadsheetWrapper.getCellValueAsString(row, c);
            String key = headerRowMap.get(c);
            recordValueMap.put(key, value);
        }
        return recordValueMap;
    }

    public MultipartFile getFile() {
        return file;
    }

    public int getHeaderRowIndex() {
        return headerRowIndex;
    }

    public Map<Integer, String> getHeaderRowMap() {
        return headerRowMap;
    }

    public List<Map<String, String>> getRecordValueMaps() {
        return recordValueMaps;
    }

}
