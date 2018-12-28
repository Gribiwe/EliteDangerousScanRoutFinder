package service;

import entity.Planet;
import entity.Star;
import entity.StarSystem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemDetailsFinder {

   private static final String SEARCH_ENDPOINT_NAME = "https://eddb.io/system/search";
   private static final String SYSTEM_NAME_PARAM = "system%5Bname%5D";

   private static final String BODIES_ENDPOINT_NAME = "https://eddb.io/body";
   private static final String CSRF_PARAM = "_csrf=YCPLrdCct1LXxBojasOVegPFdcdw6m-iDZji3X5O1rQudBcwDs7ly8zPTLbzj8NhWNQhUWRxPq4WSLlJs9sPg%3D%3D";
   private static final String IS_POPULATED_PARAM = "body%5BsystemIsPopulated%5D=0";
   private static final String PLANET_TYPES_IDS_PARAM = "body%5BplanetTypeIds%5D=36%2C26%2C20";
   private static final String MAX_DISTANCE_PARAM = "body%5BmaxReferenceDistance%5D";
   private static final String START_SYSTEM_PARAM = "body%5BreferenceSystemId%5D";
   private static final String SORT_PARAM = "body%5Bsort%5D=referenceDistance";

   private static final JSONParser parser = new JSONParser();

   public static String getId(String starSystem) throws Exception {
      String starSystemName = starSystem.replaceAll(" ", "+");
      String startSystemParamValue = SYSTEM_NAME_PARAM + "=" + starSystemName;

      String answer = HttpSender.sendGet(SEARCH_ENDPOINT_NAME, startSystemParamValue);
      System.out.println(answer);
      JSONObject jsonAnswer = (JSONObject) ((JSONArray) parser.parse(answer)).get(0);
      return jsonAnswer.get("id").toString();
   }

   public static List<StarSystem> getNearSystems(String startSystemId, String maxDistance) throws Exception {
      List<StarSystem> result = new ArrayList<>();
      String answer = HttpSender.sendGet(BODIES_ENDPOINT_NAME, CSRF_PARAM, IS_POPULATED_PARAM, PLANET_TYPES_IDS_PARAM,
              MAX_DISTANCE_PARAM + "=" + maxDistance, START_SYSTEM_PARAM + "=" + startSystemId, SORT_PARAM);

      String table = "<table>" + answer.substring(answer.indexOf("<tbody>"), answer.indexOf("</tbody>")) + "</table>";
      Document document = Jsoup.parse(table);
      Element tableElement = document.select("table").first();

      Map<String, List<Planet>> starPlanets = new HashMap<>();
      Map<String, Double> starDistances = new HashMap<>();

      for (Element row : tableElement.select("tr")) {
         Elements tds = row.select("td");

         boolean isPopulated = false;
         Elements select = tds.get(4).select("span[class='has-feature']");
         if (select.size() > 0){
            if (select.first().select("i[title='System Populated']").size() > 0) {
               isPopulated = true;
            }
        }

         if (!isPopulated) {
            String systemName = tds.get(1).text();
            double distance = Double.parseDouble(tds.get(5).text().replaceAll(" ly", ""));

            starDistances.putIfAbsent(systemName, distance);

            String planetType = tds.get(2).text();
            List<Planet> planets = starPlanets.get(systemName);
            if (planets != null) {
               planets.add(Planet.getByTypeName(planetType));
               starPlanets.replace(systemName, planets);
            } else {
               planets = new ArrayList<>();
               planets.add(Planet.getByTypeName(planetType));
               starPlanets.put(systemName, planets);
            }
         }
      }

      for (String system : starDistances.keySet()) {
         List<Planet> planets = starPlanets.get(system);
         double distance = starDistances.get(system);
         result.add(new StarSystem(distance, system, planets));
      }

      return result;
   }
}