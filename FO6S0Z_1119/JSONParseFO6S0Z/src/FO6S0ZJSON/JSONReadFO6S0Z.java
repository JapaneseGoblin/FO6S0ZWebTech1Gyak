package FO6S0ZJSON;

import java.io.FileReader;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONReadFO6S0Z {
	
	public static void main(String args [])
    {
        try(FileReader reader = new FileReader("FO6S0Z_OraRend.Json")){
        //Parse
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(reader);
        
        JSONObject root = (JSONObject) jsonObject.get("orarend");
        JSONArray lessons = (JSONArray) root.get("ora");
        
        System.out.println("FO6S0Z Órarend 2025 ősz:\n");
        
        for(int i = 1; i< lessons.size(); i++) {
            JSONObject lesson = (JSONObject) lessons.get(i);
            JSONObject time = (JSONObject) lesson.get("idopont");
            System.out.println("Tárgy: "+ lesson.get("targy"));
            System.out.println("Időpont: "+ time.get("nap") + " "+ time.get("tol") +" " + time.get("ig"));
            System.out.println("Helyszín: "+ lesson.get("helyszin"));
            System.out.println("Oktató: "+ lesson.get("oktato"));
            System.out.println("Szak: "+ lesson.get("szak"));
        }
        
        }catch(Exception e) {
            e.printStackTrace();
        }
        
    }
}
