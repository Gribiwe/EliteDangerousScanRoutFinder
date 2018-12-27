import entity.DetailedStarSystem;
import entity.Planet;
import entity.StarSystem;
import service.SystemDetailsFinder;
import service.SystemFinder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
   public static void main(String[] args) {
      List<StarSystem> nearStartSystems = SystemFinder.findNearStartSystems("Col 285 Sector RF-M c8-10", 34);

      List<DetailedStarSystem> detailedStarSystems = new ArrayList<DetailedStarSystem>();
      Collections.sort(detailedStarSystems);
      for (StarSystem nearStartSystem : nearStartSystems) {
         detailedStarSystems.add(SystemDetailsFinder.getDetails(nearStartSystem));
      }
      for (DetailedStarSystem detailedStarSystem : detailedStarSystems) {
         int waterCount = 0;
         int earthCount = 0;
         int amiacCount = 0;
         int terraformCount = 0;
         for (Planet planet : detailedStarSystem.getPlanets()) {
            if (planet == Planet.AMIAC_PLANET) {
               amiacCount++;
            } else if (planet == Planet.EARTH_LIKE) {
               earthCount++;
            } else if (planet == Planet.WATER_WORLD || planet == Planet.WATER_WORLD_TERRAFORMABLE) {
               waterCount++;
            } else if (planet.isTerraformable()) {
               terraformCount++;
            }
         }
         boolean amiacValid = amiacCount > 0;
         boolean earthValid = earthCount > 0;
         boolean waterValid = waterCount > 0;
         boolean terraValid = terraformCount > 1;

         if (detailedStarSystem.getCost() > 0 && (amiacValid || earthValid || waterValid || terraValid)) {
            String counter = "";
            if (amiacValid) {
               counter += " | amiacs: " + amiacCount + " |";
            }
            if (earthValid) {
               counter += " | earth-likes: " + earthCount + " |";
            }
            if (waterValid) {
               counter += " | water worlds: " + waterCount + " |";
            }
            if (terraValid) {
               counter += " | total terraformable: " + terraformCount + " |";
            }
            System.out.println(detailedStarSystem.getName() + " | " + detailedStarSystem.getDistance() + " | " + detailedStarSystem.getCost() + counter);
         }
      }
   }
}
