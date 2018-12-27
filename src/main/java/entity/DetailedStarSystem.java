package entity;

import java.util.ArrayList;
import java.util.List;

public class DetailedStarSystem extends StarSystem {

   private List<Planet> planets;

   public DetailedStarSystem(StarSystem starSystem, List<Planet> planets) {
      super(starSystem.getDistance(), starSystem.getName());
      this.planets = new ArrayList<Planet>();
      this.planets.addAll(planets);
   }

   public int getCost() {
      int cost = 0;
      for (Planet planet : planets) {
         cost += planet.getPrice();
      }
      return cost;
   }

   public List<Planet> getPlanets() {
      return planets;
   }
}
