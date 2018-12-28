package entity;

public class Star {

   private double distance;
   private String name;

   public Star(double distance, String name) {
      this.distance = distance;
      this.name = name;
   }

   public double getDistance() {
      return distance;
   }

   public String getName() {
      return name;
   }
}
