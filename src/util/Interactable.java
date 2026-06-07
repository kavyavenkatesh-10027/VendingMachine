package util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

 public interface Interactable {

     Scanner scanner = new Scanner(System.in);

     default String prompt(String label) {
        System.out.print(label);
        return scanner.nextLine().trim();
    }
    
     default int readInt(String prompt) {
        while (true) {
            System.out.print(prompt + " : ");
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
                System.out.println("Please enter a number greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a whole number greater than zero.");
            }
        }
    }
    
     default Map<String, Integer> readFoodItemsMap(String context) {
        Map<String, Integer> foodItems = new HashMap<>();
        System.out.println("Enter food items for the " + context + " (blank food ID to stop):");
        while (true) {
            System.out.print("  Food ID: ");
            String foodId = scanner.nextLine().trim();
            if (foodId.isEmpty()) {
                if (foodItems.isEmpty()) {
                    System.out.println("  At least one food item is required. Try again.");
                    continue;
                }
                break;
            }
            int qty = readInt("Quantity :");
            foodItems.merge(foodId, qty, Integer::sum);
        }
        return foodItems;
    }

     default LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please use the format yyyy-MM-dd.");
            }
        }
    }

     default BigDecimal readBigDecimal(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                BigDecimal value = new BigDecimal(scanner.nextLine().trim());
                if (value.compareTo(BigDecimal.ZERO) <= 0) {
                    System.out.println("Please enter a number greater than zero.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a number greater than zero.");
            }
        }
    }

     default <T extends Enum<T>> T readEnum(Class<T> clazz, String label) {
        T[] constants = clazz.getEnumConstants();
        System.out.println(label + " options:");
        for (int i = 0; i < constants.length; i++) {
            System.out.println("  " + (i + 1) + ". " + constants[i]);
        }
        while (true) {
            System.out.print("Choose (1-" + constants.length + "): ");
            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());
                if (choice >= 1 && choice <= constants.length) {
                    return constants[choice - 1];
                }
            } catch (NumberFormatException ignored) {}
            System.out.println("Invalid choice.");
        }
    }
}
