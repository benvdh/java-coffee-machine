package machine;

import java.util.Map;
import java.util.Set;

public class CoffeeRecipe {
    private final Map<CoffeeSupply, Integer> recipe;

    public CoffeeRecipe(Map<CoffeeSupply, Integer> recipe) {
        this.recipe = recipe;
    }

    public int getQuantityByIngredient(CoffeeSupply coffeeSupply) {
        return recipe.get(coffeeSupply);
    }

    public Set<Map.Entry<CoffeeSupply, Integer>> getRecipeEntries() {
        return recipe.entrySet();
    }

    public Map<CoffeeSupply, Integer> getRecipe() {
        return recipe;
    }
}
