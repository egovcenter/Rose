package egovframework.com.cmm.util;

import java.text.ParseException;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

public class DateHelper {

    public static String getCurrDateTime() {
        Date from = new Date();
        String to = "";
        try {
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            to = transFormat.format(from);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return to;
    }
    
    public static Date convertDate(String date, String inputFormat) {
    	if ((date == null) || (date.isEmpty() || date.trim().isEmpty()) 
    			|| (inputFormat == null) || (inputFormat.isEmpty())) {
    		return null;
    	}
    	
    	SimpleDateFormat format = new SimpleDateFormat(inputFormat);
    	
    	Date dateTime = new Date();
		try {
			dateTime = format.parse(date.trim());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	return dateTime;
    }
}
