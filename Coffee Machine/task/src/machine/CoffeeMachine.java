package machine;

import java.util.*;
import java.util.stream.Collectors;

public class CoffeeMachine {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SuppliesCalculator calculator = new SuppliesCalculator();


        MachineAction action = showActionPrompt(scanner);

        while (action != MachineAction.EXIT) {
            switch (action) {
                case BUY:
                    CoffeeType coffeeType = showBuyPrompt(scanner);
                    if (coffeeType != null) {
                        String validationMessage = calculator.buyCoffee(coffeeType);
                        System.out.println(validationMessage);
                        System.out.println();
                    } else {
                        break;
                    }
                    break;
                case FILL:
                    Map<CoffeeSupply, Long> suppliesToAdd = showFillPrompts(scanner);
                    calculator.addSupplies(suppliesToAdd);
                    break;
                case TAKE:
                    long moneyWithdrawn = calculator.withDrawAllMoney();
                    System.out.printf("I gave you %s%d%n%n", CoffeeSupply.MONEY.shortUnit, moneyWithdrawn);
                    break;
                case REMAINING:
                    showSupplies(calculator.getAvailableSupplies());
                    break;
            }
            action = showActionPrompt(scanner);
        }
    }

    public static void showSupplies(Map<CoffeeSupply, Long> supplies) {
        System.out.println();
        System.out.println("The coffee machine has:");
        for (CoffeeSupply supply : CoffeeSupply.values()) {
            System.out.println(formatQuantity(supplies.get(supply), supply));
        }
        System.out.println();
    }

    public static MachineAction showActionPrompt(Scanner scanner) {
        String actionList = Arrays.stream(MachineAction.values())
            .map(x -> x.toString().toLowerCase())
            .collect(Collectors.joining(", "));

        System.out.printf("Write action (%s):%n", actionList);

        String actionName = scanner.nextLine();

        return MachineAction.valueOf(actionName.toUpperCase());
    }

    public static CoffeeType showBuyPrompt(Scanner scanner) {
        String coffeeTypeList = Arrays.stream(CoffeeType.values())
            .map(x -> String.format("%d - %s", x.ordinal() + 1, x.toString().toLowerCase()))
            .collect(Collectors.joining(", "));

        coffeeTypeList += ", back - to main menu";

        System.out.printf("What do you want to buy? %s: %n", coffeeTypeList);

        String buyOption = scanner.nextLine();

        if(!buyOption.equals("back")) {
            int coffeeTypeId = Integer.parseInt(buyOption);
            return CoffeeType.values()[coffeeTypeId - 1];
        } else {
            return null;
        }
    }

    public static Map<CoffeeSupply, Long> showFillPrompts(Scanner scanner) {
        Map<CoffeeSupply, Long> suppliesToAdd = new HashMap<>();

        for (CoffeeSupply supply : CoffeeSupply.values()) {
            String unit = supply.longUnit.isEmpty() ? supply.shortUnit : supply.longUnit;

            if (!supply.isCurrency) {
                System.out.printf("Write how many %s of %s you want to add:%n", unit, supply.humanReadableName);
                long amountToAdd = scanner.nextLong();
                suppliesToAdd.put(supply, amountToAdd);
            }
        }
        // move scanner to nextLine to prevent issues with actionPrompt
        scanner.nextLine();

        return suppliesToAdd;
    }

    public static String formatQuantity(long requiredQuantity, CoffeeSupply coffeeSupply) {
        if (coffeeSupply.isCurrency) {
            return String.format("%s%d of %s", coffeeSupply.shortUnit, requiredQuantity, coffeeSupply.humanReadableName);
        }

        if (coffeeSupply.shortUnit.isEmpty()) {
            return String.format("%d %s", requiredQuantity, coffeeSupply.longUnit);
        } else {
            return String.format("%d %s of %s", requiredQuantity, coffeeSupply.shortUnit, coffeeSupply.humanReadableName);
        }

    }
}
