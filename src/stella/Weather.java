package stella;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Weather {
	public static String getWeather(int d) throws IOException, ParseException {
		main.say("Getting info.");
		String endstr = "";
		String str = "";
		String weatherStr = "";
		String requestURL = "https://api.darksky.net/forecast/327b5f1429696fde57fd92433d9fda14/48.83777,10.0933?units=si";
		URL url = new URL(requestURL);
		URLConnection conn = url.openConnection();  

		BufferedReader br = new BufferedReader(new InputStreamReader(
		    (conn.getInputStream())));

		    while ((str = br.readLine()) != null) {
		        System.out.println(weatherStr);
		        weatherStr = str;
		    }
		    
		    JSONParser parser = new JSONParser();
		    JSONObject weatherObj = (JSONObject) parser.parse(weatherStr);
		    JSONArray weatherArr = (JSONArray) ((JSONObject) weatherObj.get("daily")).get("data");
		    JSONObject[] objArr = new JSONObject[weatherArr.size()];
		    
		    for (int i = 0; i < weatherArr.size(); ++i) {
		    	objArr[i] = (JSONObject) weatherArr.get(i);
		    }
		    String day = "";
		    String today = "";
		    String todayMaxT = "";
		    String todayProb = "";
		    
		    if (((JSONObject) objArr[d]).get("summary").toString() != null) {
		    	today = objArr[d].get("summary").toString();
		    	double todayMaxTd = Math.floor((double) objArr[d].get("temperatureHigh"));
		    	Integer todayMaxTi = (int) todayMaxTd;
		    	todayMaxT = todayMaxTi.toString();
		    	
		    	try {
			    	double todayProbD = (double) objArr[d].get("precipProbability");
			    	todayProbD = todayProbD * 100;
			    	Integer todayProbI = (int) todayProbD;
			    	todayProb = todayProbI.toString();
	        		}
	        		catch (ClassCastException e) {
	        			long todayProbD = (long) objArr[d].get("precipProbability");
		        		todayProbD = todayProbD * 100;
		        		Integer todayProbI = (int) todayProbD;
		        		todayProb = todayProbI.toString();
	        		}
		    }
		    
		    else {
		    	endstr = "There is currently no information available.";
		    }
		    
		    switch (d) {
		    case 0:
		    	day = "Today";
		    	break;
		    	
		    case 1:
		    	day = "Tomorrow";
		    	break;
		    	
		    default:
		    	day = "In " + d + " days";
		    }
		    
		    endstr = day + " the weather is " + today + " with maximal " + todayMaxT + " degrees and a " + todayProb + " percent chance of precipitation.";
		    				
		return endstr;
		}


		public static String getWeekWeather () throws ParseException, IOException, java.text.ParseException {
			main.say("Getting info.");	
			String endstr = "";
			String str = "";
			String weatherStr = "";
			String requestURL = "https://api.darksky.net/forecast/327b5f1429696fde57fd92433d9fda14/48.83777,10.0933?units=si";
			URL url = new URL(requestURL);
			URLConnection conn = url.openConnection();  

			BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));

		    while ((str = br.readLine()) != null) {
		        weatherStr = str;
		    }
		    
		    JSONParser parser = new JSONParser();
		    JSONObject weatherObj = (JSONObject) parser.parse(weatherStr);
		    JSONArray weatherArr = (JSONArray) ((JSONObject) weatherObj.get("daily")).get("data");
		    JSONObject[] objArr = new JSONObject[weatherArr.size()];
		    
		    for (int i = 0; i < weatherArr.size(); ++i) {
				objArr[i] = (JSONObject) weatherArr.get(i);
		    	}

			Integer[] todayMaxT = new Integer[7];
			Integer[] todayMinT = new Integer[7];
			Integer[] todayProb = new Integer[7];

		    for (int d = 0; d < 7; ++d) {                    
		        	if (((JSONObject) objArr[d]).get("summary").toString() != null) {
		        		
		        		try {
		        		double todayMaxTd = Math.round((double) objArr[d].get("temperatureHigh"));
		        		Integer todayMaxTi = (int) Math.round(todayMaxTd);
		        		todayMaxT[d] = todayMaxTi;
		        		}
		        		catch (ClassCastException e) {
		        			long todayMaxTl = (long) Math.round((long) objArr[d].get("temperatureHigh"));
			        		Integer todayMaxTi = (int) Math.round(todayMaxTl);
			        		todayMaxT[d] = todayMaxTi;
		        		}
		        		
		        		try {
		        		double todayMinTd = Math.round((double) objArr[d].get("temperatureLow"));
		        		Integer todayMinTi = (int) Math.round(todayMinTd);
		        		todayMinT[d] = todayMinTi;
		        		}
		        		catch (ClassCastException e) {
		        			long todayMinTl = (long) Math.round((long) objArr[d].get("temperatureLow"));
			        		Integer todayMinTi = (int) Math.round(todayMinTl);
			        		todayMinT[d] = todayMinTi;
		        		}
		        		
		        		try {
		        		double todayProbD = (double) objArr[d].get("precipProbability");
		        		todayProbD = todayProbD * 100;
		        		Integer todayProbI = (int) todayProbD;
		        		todayProb[d] = todayProbI;
		        		}
		        		catch (ClassCastException e) {
		        			long todayProbL = (long) objArr[d].get("precipProbability");
			        		todayProbL = todayProbL * 100;
			        		Integer todayProbI = (int) todayProbL;
			        		todayProb[d] = todayProbI;
		        		}
		        	}
		        
		        	else {
		        		endstr = "There is currently no information available.";
		        		return endstr;
		        	}
		    }
		    
		    endstr = "In the next week we have the highest temperatures, " + getMax(todayMaxT, "degrees") + ", and the lowest temperatures " + getMin(todayMinT, "degrees") + ". The highest probability for precipitation is " + getMax(todayProb, "percent") + ".";
		    
		return endstr;
		}


		public static String getMax (Integer[] array, String unit) throws java.text.ParseException {
			Integer maxVal = array[0];
			Integer iter = 0;

			for (int i = 0; i < array.length; ++i) {
				if (array[i] > maxVal) {
					maxVal = array[i];
					iter = i;
				}
			}
			Calendar c = Calendar.getInstance(); 
	        String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK), iter);
			
			String endstr = "on " + dayOfWeek + ", with maximal " + maxVal.toString() + " " + unit;
			return endstr;
		}

		public static String getMin (Integer[] array, String unit) throws java.text.ParseException {
			Integer minVal = array[0];
			Integer iter = 0;

			for (int i = 0; i < array.length; ++i) {
				if (array[i] < minVal) {
					minVal = array[i];
					iter++;
				}
			}
			Calendar c = Calendar.getInstance(); 
	        String dayOfWeek = getDayOfWeek(c.get(Calendar.DAY_OF_WEEK), iter);
	        
	        String endstr = "on " + dayOfWeek + ", with minimal " + minVal.toString() + " " + unit;

		return endstr;
		}
		
		public static String getDayOfWeek(int val1, int val2){
			int value;
			if((val1 + val2) <= 7) {
				value = val1+val2;
			}
			else {
				value = (val1+val2)-7;
			}
			
		    String day = "";
		    switch(value){
		    case 1:
		        day="Sunday";
		        break;
		    case 2:
		        day="Monday";
		        break;
		    case 3:
		        day="Tuesday";
		        break;
		    case 4:
		        day="Wednesday";
		        break;
		    case 5:
		        day="Thursday";
		        break;
		    case 6:
		        day="Friday";
		        break;
		    case 7:
		        day="Saturday";
		        break;
		    }
		    return day;
		}
}
