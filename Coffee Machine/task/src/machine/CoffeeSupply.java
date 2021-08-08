package machine;

public enum CoffeeSupply {
    WATER("water", "ml", "", false),
    MILK("milk", "ml", "", false),
    BEANS("coffee beans", "g", "grams", false),
    CUPS("coffee", "", "disposable cups", false),
    MONEY("money", "$", "", true);


    String humanReadableName;
    String shortUnit;
    String longUnit;
    boolean isCurrency;

    CoffeeSupply(String name, String shortUnit, String longUnit, boolean isCurrency) {
        this.humanReadableName = name;
        this.shortUnit = shortUnit;
        this.longUnit = longUnit;
        this.isCurrency = isCurrency;
    }
}
