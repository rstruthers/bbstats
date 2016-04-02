package online.bbstats;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

public class BbstatsConstants {
	public static final String[] POSITIONS = {"P",	"C", "1B", "2B", "3B", "SS", "LF", "CF", "RF", "OF"};
	
	public static final String DATE_PATTERN = "MM/dd/yyyy";
	
	public static final SimpleDateFormat SPREADSHEET_DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
	
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("M/d/yyyy");
	
}
