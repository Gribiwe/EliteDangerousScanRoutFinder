package service;

import entity.StarSystem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SystemFinder {

   private static final String ENDPOINT_NAME = "https://www.edsm.net/api-v1/sphere-systems";
   private static final String START_SYSTEM_NAME_PARAM = "systemName";
   private static final String RADIUS_PARAM = "radius";
   private static final JSONParser parser = new JSONParser();

   public static List<StarSystem> findNearStartSystems(String startSystemName, int radius) {
      startSystemName = startSystemName.replaceAll(" ", "%20");
      List<StarSystem> starSystems = new ArrayList<StarSystem>();
      try {
         String startSystemParamValue = START_SYSTEM_NAME_PARAM + "=" + startSystemName;
         String radiusParamValue = RADIUS_PARAM + "=" + radius;
         String answer = EDSMHttpSender.sendGet(ENDPOINT_NAME, startSystemParamValue, radiusParamValue);
         JSONArray jsonArray = (JSONArray) parser.parse(answer);
         for (Object next : jsonArray) {
            double distance = Double.parseDouble(((JSONObject) next).get("distance").toString());
//            if (distance != 0) {
            String name = ((JSONObject) next).get("name").toString();
            starSystems.add(new StarSystem(distance, name));
//            }
         }

      } catch (Exception e) {
         e.printStackTrace();
         return new ArrayList<StarSystem>();
      }
      return starSystems;
   }
}
