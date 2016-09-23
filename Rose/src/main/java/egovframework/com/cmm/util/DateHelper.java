package egovframework.com.cmm.util;

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
}
