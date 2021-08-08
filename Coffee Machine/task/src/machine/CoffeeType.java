package machine;

import java.util.Map;

public enum CoffeeType {
    ESPRESSO(
        new CoffeeRecipe(
            Map.of(
                CoffeeSupply.WATER, 250,
                CoffeeSupply.BEANS, 16,
                CoffeeSupply.CUPS, 1
            )
        ),
        4
    ),
    LATTE(
        new CoffeeRecipe(
            Map.of(
                CoffeeSupply.WATER, 350,
                CoffeeSupply.MILK, 75,
                CoffeeSupply.BEANS, 20,
                CoffeeSupply.CUPS, 1
            )
        ),
        7
    ),
    CAPPUCCINO(
        new CoffeeRecipe(
            Map.of(
                CoffeeSupply.WATER, 200,
                CoffeeSupply.MILK, 100,
                CoffeeSupply.BEANS, 12,
                CoffeeSupply.CUPS, 1
            )
        ),
        6
    );

    CoffeeRecipe recipe;
    long price;

    CoffeeType(CoffeeRecipe recipe, long price) {
        this.recipe = recipe;
        this.price = price;
    }
}
