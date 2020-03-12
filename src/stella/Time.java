package stella;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Time {
    public static String parseDate() {
        DateFormat df = new SimpleDateFormat("dd/MM/");
        Date dateobj = new Date();
        String date = df.format(dateobj);
        String[] datearr = date.split("/");
        Integer day = Integer.parseInt(datearr[0]);
        Integer month = Integer.parseInt(datearr[1]);
        String daystr = "";
        String monthstr = "";
        
        switch (day) {
        case 1: daystr = "first";
        	break;
        case 2: daystr = "second";
        	break;
        case 3: daystr = "third";
    		break;
        default: daystr = day.toString();
        	break;
        }
        
        switch (month) {
        case 1: monthstr = "January"; 
        	break;
        case 2: monthstr = "February"; 
			break;
        case 3: monthstr = "March"; 
			break;
        case 4: monthstr = "April"; 
			break;
        case 5: monthstr = "May"; 
			break;
        case 6: monthstr = "June"; 
			break;
        case 7: monthstr = "July"; 
			break;
        case 8: monthstr = "August"; 
			break;
        case 9: monthstr = "September"; 
			break;
        case 10: monthstr = "October"; 
			break;
        case 11: monthstr = "November"; 
			break;
        case 12: monthstr = "December"; 
			break;
        }
        
        String endstr = "Today is the " + daystr + " of " + monthstr + "."; 
        
        return endstr;
    }
    
    public static String parseTime() {
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date timeobj = new Date();
        String time = df.format(timeobj);
        String[] timearr = time.split(":");
        Integer hour = Integer.parseInt(timearr[0]);
        Integer minute = Integer.parseInt(timearr[1]);
        
        String endstr = "It is " + hour.toString() + " o'clock and " + minute.toString() + " minutes."; 
        
        return endstr;
    }

}
