package model.utils;

import java.time.LocalDate;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class DateConverter {
	static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	public static LocalDate convertDateFromDB(Date date) {
		if (date == null) 
			return null;
		return date.toLocalDate();
		}


	public static Date convertDateToDB(LocalDate date) {
		if (date == null) {
			return null;
		}
		return Date.valueOf(date);
	}
}
