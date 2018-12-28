package service;

import entity.Commander;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CurrentSystemService {

   private static final String ENDPOINT_NAME = "https://www.edsm.net/api-logs-v1/get-position";
   private static final String COMMANDER_NAME_PARAM = "commanderName";
   private static final String COMMANDER_KEY_PARAM = "apiKey";
   private static final JSONParser parser = new JSONParser();

   public static String getCommanderCurrentSystem(Commander commander) throws Exception {
      final String answer = HttpSender.sendGet(ENDPOINT_NAME, COMMANDER_NAME_PARAM + "=" + commander.getName(), COMMANDER_KEY_PARAM + "=" + commander.getApiKey());
      JSONObject jsonObject = (JSONObject) parser.parse(answer);
      return jsonObject.get("system").toString();
   }
}
