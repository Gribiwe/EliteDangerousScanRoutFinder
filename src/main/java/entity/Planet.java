package entity;

public enum Planet {
   EARTH_LIKE("Earth-like world"),
   WATER_WORLD("Water world"),
   AMIAC_PLANET("Ammonia world");

   Planet(String typeName) {
      this.typeName = typeName;
   }

   private final String typeName;

   public static Planet getByTypeName(String typeName) {
      for (Planet value : Planet.values()) {
         if (value.getTypeName().equals(typeName)) {
            return value;
         }
      }
      throw new RuntimeException("Planet type not found: "+typeName);
   }

   public String getTypeName() {
      return typeName;
   }
}
