package entity;

import java.util.List;

public class StarSystem extends Star implements Comparable<StarSystem>{


   private List<Planet> planets;

   public StarSystem(double distance, String name, List<Planet> planets) {
      super(distance, name);
      this.planets = planets;
   }

   public List<Planet> getPlanets() {
      return planets;
   }

   public int compareTo(StarSystem o) {
      return (int) ((this.getDistance()-o.getDistance())*100);
   }
}
