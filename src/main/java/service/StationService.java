package service;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class StationService {

   private static final String ENDPOINT_NAME = "https://www.edsm.net/api-system-v1/stations";
   private static final String SYSTEM_NAME_PARAM = "systemName";
   private static final JSONParser parser = new JSONParser();

   public static boolean isPopulated(String starName) throws Exception {
      starName = starName.replaceAll(" ", "%20");
      String answer = EDSMHttpSender.sendGet(ENDPOINT_NAME, SYSTEM_NAME_PARAM+"="+starName);
      JSONObject jsonObject = (JSONObject) parser.parse(answer);
      JSONArray jsonArray = (JSONArray) jsonObject.get("stations");
      if (jsonArray == null) {
         return true;
      }
      return jsonArray.size() != 0;
   }
}
