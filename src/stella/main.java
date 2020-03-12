package stella;

import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;

public class main {     
	static LiveSpeechRecognizer recognizer;

    public static void main(String[] args) throws Exception {
    	
    	say("Stella is starting.");
        Configuration configuration = new Configuration();

        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/8000.dic");
        configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/8000.lm");

        recognizer = new LiveSpeechRecognizer(configuration);
        String os;
        String res;
        Boolean mute = false;
                
        os = System.getProperty("os.name");
        
        if (os.contains("Linux")) {
        recognizer.startRecognition(true);
        SpeechResult result = recognizer.getResult();
        say("Hi David! How can I help You?");

     while ((result = recognizer.getResult()) != null) {
    	 res = result.getHypothesis().toLowerCase();
    	 
    	 if(res.contains("heystella") || res.contains("yes") || res.contains("no")) {
    		System.out.format("Hypothesis: %s\n", res);
    		
 	    
    	try {
    		
    		switch (res) {    		
    			case "heystella howistheweather today":
    				say(Weather.getWeather(0));
    				break;
    			case "heystella howistheweather tomorrow":
    				say(Weather.getWeather(1));
    				break;
    			case "heystella howistheweather nextweek":
    				say(Weather.getWeekWeather());
    				break;
    				
    			case "heystella whatdateisit":
    				say(Time.parseDate());
    				break;
    			case "heystella whattimeisit":
    				say(Time.parseTime());
    				break;
    				
    			case "heystella openbrowser":
    				Runtime.getRuntime().exec("firefox");
    				say("Have a productive searching session!");
    				break;
    			case "heystella closebrowser":
    				confirm("killall firefox", "Ok, I will terminate the search for now!", "");
    				break;
    			case "heystella opentab":
    				Runtime.getRuntime().exec("firefox duckduckgo.com");
    				break;
    				
    			case "heystella openshell":
    				Runtime.getRuntime().exec("gnome-terminal");
    				say("Stay careful!");
    				break;
    				
    			case "heystella openblender":
    				Runtime.getRuntime().exec("blender");
    				say("Happy rendering!");
    				break;
    			case "heystella closeblender":
    				confirm("killall blender", "Time to cool down your graphics chip!", "");
    				break;
    				
    			case "heystella mute":
    				if (confirm("amixer set Master mute", "lol", "")) {
    					mute = true;
    				}
    				break;
    			case "heystella unmute":
    				if (mute) {
    					Runtime.getRuntime().exec("amixer set Master unmute");
    					say("It's not easy to shut up for such a long time!");
    					mute = false;
    				}
    				break;
    			case "heystella volumeup":
    				Runtime.getRuntime().exec("amixer set Master 10%+");
    				say("Okay");
    				break;
    			case "heystella volumedown":
    				Runtime.getRuntime().exec("amixer set Master 10%-");
    				say("Okay");
    				break;
    				
    			case "heystella openfiles":
    				Runtime.getRuntime().exec("caja /home/user");
    				break;
    			case "heystella closefiles":
    				Runtime.getRuntime().exec("killall caja");
    				break;
    				
    			case "heystella opentexteditor":
    				Runtime.getRuntime().exec("pluma");
    				say("This is way better than notepad!");
    				break;
    			case "heystella closetexteditor":
    				confirm("killall pluma", "I hope you hit the save button!", "");
    				break;
    			
    			case "heystella whenisthenextrocketlaunch":
    				say(Astro.getLaunches());
    				break;
    				
    			case "heystella showmoonphase":
    				say(Astro.getPhase("moon"));
    		        break;
    			case "heystella showmarsphase":
    				say(Astro.getPhase("mars"));
    		        break;
    			case "heystella showvenusphase":
    				say(Astro.getPhase("venus"));
    		        break;
    			case "heystella showmercuryphase":
    				say(Astro.getPhase("mercury"));
    		        break;
    			case "heystella showjupiterphase":
    				say(Astro.getPhase("jupiter"));
    		        break;
    		        
    			case "heystella inwhichconstellationis jupiter":
    				say(Astro.getPosition("jupiter"));
    				break;
    			case "heystella inwhichconstellationis saturn":
    				say(Astro.getPosition("saturn"));
    				break;
    			case "heystella inwhichconstellationis mars":
    				say(Astro.getPosition("mars"));
    				break;
    			case "heystella inwhichconstellationis venus":
    				say(Astro.getPosition("venus"));
    				break;
    			case "heystella inwhichconstellationis mercury":
    				say(Astro.getPosition("mercury"));
    				break;
    			case "heystella inwhichconstellationis uranus":
    				say(Astro.getPosition("uranus"));
    				break;
    				
    			case "heystella whendoihave goodobservationconditions fordeepskyobjects":
    				say(Astro.deepSky());
    				break;
    		}
    	}
 	    	catch (IOException e) {
 	    		 System.out.println("Could not open program!");
    		}
    	 }
    }
 		recognizer.stopRecognition();
        }
        
        else {
        	say("Stella is currently only available for Linux");
        }
    }
    
    
    public static Boolean confirm (String com, String addition, String rocket) throws IOException {
    	if (!rocket.equals("rocket")) {
    		say("Are you sure?");
    	}
    	
    	else {
    		say("Do you want more detailed mission information?");
    	}
    	
		Boolean answer = false;
		String result2;
		
		while (!answer) {
			result2 = recognizer.getResult().getHypothesis().toLowerCase();
			if (result2.contains("yes")) {
				if (!rocket.equals("rocket")) {
					Runtime.getRuntime().exec(com);
					say(addition);
					return true;
				}
				
				else {
					return true;
				}
			}
			else if (result2.contains("no")) {
				say("Alright.");
				return false;
			}
			else {
				System.out.println("else");
			}
		}
		return false;
    }
    
    public static void say (String input) {
    	TextToSpeech tts = new TextToSpeech();
        tts.setVoice("cmu-slt-hsmm");
        tts.speak(input, 1.0f, false, true);
    }
        
}



