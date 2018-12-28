package entity;

public enum Commander {

   GRIBIWE("Gribiwe", "91fa4a9ca1f210ac0192b4a53a49f91fe5f8795b"),
   MARTELLY("Martelly", "e6a62265ef7f84936c5eac628274fcb0c28a745d");

   Commander(String name, String apiKey) {
      this.name = name;
      this.apiKey = apiKey;
   }

   private final String name;

   private final String apiKey;

   public String getName() {
      return name;
   }

   public String getApiKey() {
      return apiKey;
   }
}
