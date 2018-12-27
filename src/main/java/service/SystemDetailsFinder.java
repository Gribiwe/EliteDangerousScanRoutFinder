package service;

import entity.DetailedStarSystem;
import entity.Planet;
import entity.StarSystem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class SystemDetailsFinder {

   private static final String ENDPOINT_NAME = "https://www.edsm.net/api-system-v1/bodies";
   private static final String SYSTEM_NAME_PARAM = "systemName";
   private static final JSONParser parser = new JSONParser();

   public static DetailedStarSystem getDetails(StarSystem starSystem) {
      String starSystemName = starSystem.getName().replaceAll(" ", "%20");
      String startSystemParamValue = SYSTEM_NAME_PARAM + "=" + starSystemName;
      DetailedStarSystem detailedStarSystem = null;
      try {
         String answer = EDSMHttpSender.sendGet(ENDPOINT_NAME, startSystemParamValue);
         JSONObject jsonAnswer = (JSONObject) parser.parse(answer);
         JSONArray bodies = (JSONArray) jsonAnswer.get("bodies");
         List<Planet> planets = new ArrayList<Planet>();
         System.out.println(jsonAnswer);
         for (Object body : bodies) {
            JSONObject bodyJSON = (JSONObject) body;
            String type = bodyJSON.get("type").toString();
            if (type.equals("Planet")) {
               Object terraformingState = bodyJSON.get("terraformingState");
               if (terraformingState != null) {
                  boolean terraformable = (terraformingState.toString().equals("Terraformable"));
                  String subType = bodyJSON.get("subType").toString();
                  for (Planet value : Planet.values()) {
                     if (value.getValueAtJSON().equals(subType) && value.isTerraformable() == terraformable) {
                        planets.add(value);
                     }
                  }
               }
            }
         }
         detailedStarSystem = new DetailedStarSystem(starSystem, planets);
      } catch (Exception e) {
         e.printStackTrace();
      }
      return detailedStarSystem;
   }

}
