package model.utils;

import java.time.LocalDate;
import java.sql.Date;

public class DateConverter {

	public static LocalDate convertDateFromDB(Date date) {
		return LocalDate.now();
	}
	public static Date convertDateToDB(LocalDate date) {
		return new Date(System.currentTimeMillis());
	}
}
