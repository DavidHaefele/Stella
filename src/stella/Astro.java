package stella;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Astro {
    public static String getLaunches() throws IOException, ParseException {
    	main.say("Getting info.");
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Date dateobj = new Date();
        String date = df.format(dateobj);
        LocalDate date2 = LocalDate.now().plusDays(7);
        
    	String str = "";
        String launchStr = "";
        String requestURL = "https://launchlibrary.net/1.4/launch/" + date + "/" + date2;
        URL url = new URL(requestURL);
        URLConnection conn = url.openConnection();  
        
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

                while ((str = br.readLine()) != null) {
                    System.out.println(launchStr);
                    launchStr = str;
                }
                
                JSONParser parser = new JSONParser();
                JSONObject launchObj = (JSONObject) parser.parse(launchStr);
                JSONArray launchArr = (JSONArray) launchObj.get("launches");
                JSONObject[] objArr = new JSONObject[launchArr.size()];
                
                for (int i = 0; i < launchArr.size(); ++i) {
                	objArr[i] = (JSONObject) launchArr.get(i);
                }
                String endstr = "In the next seven days ";
                
                for (int i = 0; i < launchArr.size(); ++i) {
                	if ((((JSONObject)((JSONObject) objArr[i]).get("lsp")).get("name").toString() != null) && (((JSONObject)((JSONObject) objArr[i]).get("lsp")).get("name").toString().equals("SpaceX"))) {
                		String nameStr = objArr[i].get("name").toString();
                		String[] nameArr = nameStr.split("\\|");
                		String name = nameArr[0];
                		String launchT = objArr[i].get("net").toString();
                		String[] dateArr = launchT.split("\\s+");
                		dateArr[1] = dateArr[1].substring(0, dateArr[1].length() - 1);
                		
                		String[] timeArr = dateArr[3].split(":");
                		Integer hour = Integer.parseInt(timeArr[0]);
                		Integer day = Integer.parseInt(dateArr[1]);                		
                		
                		switch (hour) {
                		case 23:
                			day = day + 1;
                			dateArr[1] = day.toString();
                			hour = 1;
                			timeArr[0] = hour.toString();
                			break;
                			
                		case 24:
                			day = day + 1;
                			dateArr[1] = day.toString();
                			hour = 2;
                			timeArr[0] = hour.toString();
                			break;
                		
                		default:
                			hour = hour + 2;
                			timeArr[0] = hour.toString();
                		}
                		
                		switch (day) {
                		case 1:
                			dateArr[1] = "first";
                			break;
                			
                		case 2:
                			dateArr[1] = "second";
                			break;
                			
                		case 3:
                			dateArr[1] = "third";
                			break;
                		}
                    
                		endstr = endstr + ", the" + name + " will start on " + dateArr[0] + dateArr[1] + " at " + timeArr[0] + " o'clock and " + timeArr[1] + " minutes in European time.";
                	}
                }
                
                if (endstr.equals("In the next seven days ")) {
                	endstr = "There are no SpaceX launches in the next seven days.";
                }
                
                else {
                	endstr = endstr + " Don't miss it!";
                	Boolean more = main.confirm("", "", "rocket");
                	
                    if (more == true) {
                    	for (int i = 0; i < launchArr.size(); ++i) {
                        	if ((((JSONObject) ((JSONArray) ((JSONObject) objArr[i]).get("missions")).get(0)).get("description").toString() != null) && (((JSONObject)((JSONObject) objArr[i]).get("lsp")).get("name").toString().equals("SpaceX"))) {
                        		String detailsStr = ((JSONObject) ((JSONArray) ((JSONObject) objArr[i]).get("missions")).get(0)).get("description").toString();
                        		endstr = endstr + " Now some mission details: " + detailsStr;
                        	}
                    	}
                    }
                }
                
				return endstr;
    }
    
    
    public static String getPhase (String planet) throws IOException {
    	DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    	Date dateobj = new Date();
        String date = df.format(dateobj);
        Runtime.getRuntime().exec("firefox http://api.usno.navy.mil/imagery/" + planet + ".png?date=" + date + "&time=22:00");
        
        return "This is the " + planet + " phase you can observe on this evening.";
    }
    
    
    public static String getPosition (String planet) throws ScriptException, IOException {
    	/*ScriptEngine engine = new ScriptEngineManager().getEngineByName("python");
    	engine.eval("import ephem");*/
    	DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    	Date dateobj = new Date();
        String date = df.format(dateobj);
    	
        String line;
        String pos = null;
        Process p = Runtime.getRuntime().exec("python positions.py " + planet + " " + date);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(p.getInputStream()) );
        
        while ((line = in.readLine()) != null) {
          System.out.println(line);
          pos = line;
        }
        in.close();
        
		return pos;
    }
    
    
    public static String deepSky () throws IOException {
    	DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    	Date dateobj = new Date();
        String date = df.format(dateobj);
        
        String endstr;
        String line;
        String nm = null;
        Process p = Runtime.getRuntime().exec("python newmoon.py " + date);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(p.getInputStream()) );
        
        while ((line = in.readLine()) != null) {
          System.out.println(line);
          nm = line;
        }
        in.close();
        String[] nmArr1 = nm.split(" ");
        String[] nmArr2 = nmArr1[0].split("/");        
        Integer day = Integer.parseInt(nmArr2[2]);
        Integer month = Integer.parseInt(nmArr2[1]);
        String daystr = null;
        String monthstr = null;
        
        switch (day) {
		case 1:
			daystr = "first";
			break;
			
		case 2:
			daystr = "second";
			break;
			
		case 3:
			daystr = "third";
			break;
			
		default:
			daystr = day + "th";
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
        
        endstr = "The best time to observe deep sky objects is on the " + daystr + " of " + monthstr + ".";
        
		return endstr;
    	
    }

}
