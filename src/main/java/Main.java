import entity.Commander;
import entity.Planet;
import entity.StarSystem;
import service.CurrentSystemService;
import service.SystemDetailsFinder;

import java.util.Collections;
import java.util.List;

public class Main {
   public static void main(String[] args) throws Exception {
      System.out.println(getBestStars(Commander.GRIBIWE, 100));
      System.out.println(getBestStars(Commander.MARTELLY, 100));
   }

   private static String getBestStars(Commander commander, int radius) throws Exception {
      StringBuilder result = new StringBuilder("BEST STARS IN " + radius + " LY FOR " + commander.getName());

      String commanderCurrentSystemName = CurrentSystemService.getCommanderCurrentSystem(commander);
      final String startSystemId = SystemDetailsFinder.getId(commanderCurrentSystemName);
      result.append("\nCurrent system: ").append(commanderCurrentSystemName);

      List<StarSystem> nearSystems = SystemDetailsFinder.getNearSystems(startSystemId, "" + radius);
      Collections.sort(nearSystems);
      for (StarSystem nearSystem : nearSystems) {
         StringBuilder planets = new StringBuilder();
         for (Planet planet : nearSystem.getPlanets()) {
            planets.append(planet.getTypeName()).append(", ");
         }
         result.append("\n").append(nearSystem.getName()).append(" | ").append(nearSystem.getDistance()).append(" | ").append(planets.toString());
      }

      return result.toString();
   }
}
