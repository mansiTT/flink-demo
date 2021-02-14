package com.batch.filetoes.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utils {
	

	public static final String HEADERS = "Region,Country,Item Type,Sales Channel,Order Priority,Order Date,Order ID,Ship Date,Units Sold,Unit Price,Unit Cost,Total Revenue,Total Cost,Total Profit"; 


	public static String formatDate(String inDate) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern("MM/dd/YYYY");
		// pass your DOB String
		DateTime jodatime = dtf.parseDateTime(inDate);
		// Format for output
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyy-MM-dd");
		return dtfOut.print(jodatime);
	}


}
