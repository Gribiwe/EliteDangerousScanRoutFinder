package entity;

public class StarSystem implements Comparable<StarSystem>{

   private double distance;
   private String name;

   public StarSystem(double distance, String name) {
      this.distance = distance;
      this.name = name;
   }

   public double getDistance() {
      return distance;
   }

   public String getName() {
      return name;
   }

   public int compareTo(StarSystem o) {
      return (int) ((o.getDistance()-this.getDistance())*100);
   }
}
