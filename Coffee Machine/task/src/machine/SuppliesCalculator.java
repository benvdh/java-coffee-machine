package machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SuppliesCalculator {
    private final Map<CoffeeSupply, Long> availableSupplies;
    private final String SUCCESS_MESSAGE="I have enough resources, making you a coffee!";

    public SuppliesCalculator() {
        Map<CoffeeSupply, Long> initialSupplies = Map.of(
            CoffeeSupply.WATER, 400L,
            CoffeeSupply.MILK, 540L,
            CoffeeSupply.BEANS, 120L,
            CoffeeSupply.CUPS, 9L,
            CoffeeSupply.MONEY, 550L
        );

        this.availableSupplies = new HashMap();
        this.availableSupplies.putAll(initialSupplies);
    }
    
    public long calculateMaxAmountOfCupsByRecipe(CoffeeRecipe recipe) {
        ArrayList<Long> maxCupsPerIngredient = new ArrayList<>();

        for (Map.Entry<CoffeeSupply, Integer> recipeEntry : recipe.getRecipeEntries()) {
            maxCupsPerIngredient.add(availableSupplies.get(recipeEntry.getKey()) / recipeEntry.getValue());
        }

        return maxCupsPerIngredient.stream().min(Long::compare).get();
    }

    public String buyCoffee(CoffeeType coffeeType) {
        String canWeMakeThisCoffee = canWeMakeThisCoffee(coffeeType);

        if(!canWeMakeThisCoffee.equals(SUCCESS_MESSAGE)) {
            return canWeMakeThisCoffee;
        }

        for (Map.Entry<CoffeeSupply, Integer> recipeEntry : coffeeType.recipe.getRecipeEntries()) {
            CoffeeSupply supply = recipeEntry.getKey();
            availableSupplies.put(supply, availableSupplies.get(supply) - recipeEntry.getValue());
        }
        availableSupplies.put(CoffeeSupply.MONEY, availableSupplies.get(CoffeeSupply.MONEY) + coffeeType.price);

        return SUCCESS_MESSAGE;
    }

    public String canWeMakeThisCoffee(CoffeeType coffeeType) {
        for (Map.Entry<CoffeeSupply, Integer> recipeEntry : coffeeType.recipe.getRecipeEntries()) {
            CoffeeSupply supply = recipeEntry.getKey();
            long remainingSupply = availableSupplies.get(supply) - recipeEntry.getValue();

            if (remainingSupply < 0) {
                return String.format("Sorry, not enough %s!", supply == CoffeeSupply.CUPS ? supply.longUnit : supply.humanReadableName);
            }
        }

        return SUCCESS_MESSAGE;
    }

    public void addSupplies(Map<CoffeeSupply, Long> suppliesToAdd) {
        for (Map.Entry<CoffeeSupply, Long> supplyToAdd: suppliesToAdd.entrySet()) {
            CoffeeSupply supply = supplyToAdd.getKey();
            availableSupplies.put(supply, availableSupplies.get(supply) + supplyToAdd.getValue());
        }
    }

    public long withDrawAllMoney() {
        Long moneyWithDrawn = availableSupplies.put(CoffeeSupply.MONEY, 0L);

        return moneyWithDrawn == null ? 0 : moneyWithDrawn;
    }

    public Map<CoffeeSupply, Long> getAvailableSupplies() {
        return availableSupplies;
    }
}
