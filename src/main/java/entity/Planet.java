package entity;

public enum Planet {

   HIGHT_METAL_TERRAFORMABLE("High metal content world", true, 420000),
   HIGHT_METAL_NOT_TERRAFORMABLE("High metal content world", false, 37000),
   EARTH_LIKE("Earth-like world", false, 670000),
   WATER_WORLD("Water world", false, 156000),
   WATER_WORLD_TERRAFORMABLE("Water world", true, 695000),
   ROCK_TERRAFORMABLE("Rocky body", true, 285000),
   AMIAC_PLANET("Ammonia world", false, 410000);

   Planet(String valueAtJSON, boolean terraformable, int price) {
      this.price = price;
      this.terraformable = terraformable;
      this.valueAtJSON = valueAtJSON;
   }

   private final int price;
   private final String valueAtJSON;
   private final boolean terraformable;

   public int getPrice() {
      return price;
   }

   public String getValueAtJSON() {
      return valueAtJSON;
   }

   public boolean isTerraformable() {
      return terraformable;
   }
}
