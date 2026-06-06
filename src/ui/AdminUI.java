package ui;

import controller.AdminController;
import model.*;
import util.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class AdminUI implements Interactable{
//Used Ai for clean output
    private final Scanner scanner;
    private final AdminController adminController = new AdminController();

    public AdminUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        boolean running = true;
        while (running) {
            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. Create vending machine");
            System.out.println("2. Remove vending machine");
            System.out.println("3. Add slot to vending machine");
            System.out.println("4. Remove slot");
            System.out.println("5. Register food item");
            System.out.println("6. Remove food item");
            System.out.println("7. Add food type to slot");
            System.out.println("8. Refill food in slot");
            System.out.println("9. Edit food description");
            System.out.println("10. Edit food name");
            System.out.println("11. Edit food price");
            System.out.println("12. Edit food brand");
            System.out.println("13. Edit food warning(Clear/Add)");
            System.out.println("14. View all vending machines");
            System.out.println("15. View all food items");
            System.out.println("16. View product count at a machine");
            System.out.println("17. View cash drawer (denominations)");
            System.out.println("18. Add cash to drawer");
            System.out.println("19. View purchase history");
            System.out.println("0. Exit");
            System.out.println("=================================");
            System.out.print("Choice: ");

            String input = scanner.nextLine().trim();

            try {
                switch (input) {
                    case "1":  createVendingMachine(); break;
                    case "2": removeVendingMachine(); break;
                    case "3":  addSlotToVendingMachine(); break;
                    case "4": removeSlot(); break;
                    case "5":  registerFood(); break;
                    case "6": removeFood(); break;
                    case "7":  addNewFoodTypeToSlot(); break;
                    case "8":  refillFoodInSlot(); break;
                    case "9":  editFoodDescription(); break;
                    case "10":  editFoodName(); break;
                    case "11":  editFoodPrice(); break;
                    case "12":  editFoodBrand(); break;
                    case "13": editFoodWarning(); break;
                    case "14": viewAllVendingMachines(); break;
                    case "15": viewAllFoods(); break;
                    case "16": viewProductCount(); break;
                    case "17": viewCashDrawer(); break;
                    case "18": addCashToDrawer(); break;
                    case "19": viewPurchaseHistory(); break;
                    case "0":  running = false; break;
                    default:   System.out.println("Invalid choice. Please try again.");
                }
            } catch (VendingMachineException e) {
                System.out.println("[Error] " + e.getMessage());
            }
        }
    }

    private void createVendingMachine() {
        System.out.println("\n--- Create Vending Machine ---");

        Location location = (Location) readEnum(Location.class, "Location");
        LocalDate establishedOn = readDate("Established on (yyyy-MM-dd): ");
        Map<String, Integer> firstSlotFoodItems = readFoodItemsMap("first slot");

        VendingMachine vm = adminController.createVendingMachine(location, establishedOn, firstSlotFoodItems);

        System.out.println("\nVending machine created successfully!");
        System.out.println(vm);
    }

    private void removeVendingMachine() {
        System.out.println("\n--- Remove Vending Machine ---");
        String vmId = prompt("Vending machine ID to remove: ");;
        adminController.removeVendingMachine(vmId);
        System.out.println("Vending machine " + vmId + " and all its slots have been removed.");
    }

    private void addSlotToVendingMachine() {
        System.out.println("\n--- Add Slot to Vending Machine ---");

        String vendingMachineId = prompt("Vending machine ID: ");

        Map<String, Integer> foodItems = readFoodItemsMap("new slot");

        Slot slot = adminController.addSlotToVendingMachine(vendingMachineId, foodItems);

        System.out.println("\nSlot added successfully!");
        System.out.println(slot);
    }
    private void removeSlot() {
        System.out.println("\n--- Remove Slot ---");
        String slotId = prompt("Slot ID to remove: ");
        adminController.removeSlot(slotId);
        System.out.println("Slot " + slotId + " removed. Food quantities in it have been deducted.");
    }

    private void registerFood() {
        System.out.println("\n--- Register Food Item ---");

        String productName = prompt("Product name: ");

        String brand = prompt("Brand: ");

        String description = prompt("Description: ");

        String warning = prompt("Warning (press Enter to skip): ");
        if (warning.isEmpty()) warning = null;

        BigDecimal price = readBigDecimal("Price: ");

        System.out.print("Manufacturing location: ");
        Location manufacturingLocation = (Location) readEnum(Location.class, "Manufacturing location");

        LocalDate manufacturingDate = readDate("Manufacturing date (yyyy-MM-dd): ");
        LocalDate expiryDate = readDate("Expiry date (yyyy-MM-dd): ");

        VegNonVeg vegOrNonVeg = (VegNonVeg) readEnum(VegNonVeg.class , "Veg / Non-veg");

        System.out.print("Ingredients (comma-separated): ");
        List<String> ingredients = Arrays.asList(scanner.nextLine().trim().split(","));

        FoodType foodType = (FoodType) readEnum(FoodType.class, "Food type");

        Food food = adminController.registerFood(productName, brand, description, warning,
                price, manufacturingLocation, manufacturingDate, vegOrNonVeg,
                ingredients, expiryDate, foodType);

        System.out.println("\nFood registered successfully!");
        System.out.println(food);
    }

    private void removeFood() {
        System.out.println("\n--- Remove Food Item ---");
        String foodId = prompt("Food ID to remove: ");
        adminController.removeFood(foodId);
        System.out.println("Food " + foodId + " removed from registry and from all slots.");
    }

    private void addNewFoodTypeToSlot() {
        System.out.println("\n--- Add New Food Type to Slot ---");

        String slotId = prompt("Slot ID: ");

        String foodId = prompt("Food ID");

        int quantity = readInt("Quantity: ");

        adminController.addNewFoodTypeToSlot(slotId, foodId, quantity);
        System.out.println("Food added to slot successfully.");
    }

    private void refillFoodInSlot() {
        System.out.println("\n--- Refill Food in Slot ---");

        String slotId = prompt("Slot ID: ");
        String foodId = prompt("Food ID: ");
        int quantity = readInt("Quantity to add: ");

        adminController.refillFoodInSlot(slotId, foodId, quantity);
        System.out.println("Slot refilled successfully.");
    }

    private void editFoodDescription() {
        System.out.println("\n--- Edit Food Description ---");

        String foodId = prompt("Food ID: ");
        String newDescription = prompt("New description: ");

        adminController.editFoodDescription(foodId, newDescription);
        System.out.println("Description updated.");
    }

    private void editFoodName() {
        System.out.println("\n--- Edit Food Name ---");

        String foodId = prompt("Food ID: ");
        String newName = prompt("New name: ");

        adminController.editFoodName(foodId, newName);
        System.out.println("Name updated.");
    }

    private void editFoodPrice() {
        System.out.println("\n--- Edit Food Price ---");

        String foodId = prompt("Food ID: ");
        BigDecimal newPrice = readBigDecimal("New price: ");

        adminController.editFoodPrice(foodId, newPrice);
        System.out.println("Price updated.");
    }

    private void editFoodBrand() {
        System.out.println("\n--- Edit Food Brand ---");

        String foodId = prompt("Food ID: ");
        String newBrand = prompt("New Brand :");

        adminController.editFoodBrand(foodId, newBrand);
        System.out.println("Brand updated.");
    }

    private void editFoodWarning() {
        System.out.println("\n--- Edit Food Warning ---");

        String foodId = prompt("Food ID: ");
        String newWarning = prompt("New warning (press Enter to clear): ");

        if (newWarning.isEmpty()){
            newWarning = null;
        }
        adminController.editFoodWarning(foodId, newWarning);
        System.out.println("Warning updated.");
    }

    private void viewAllVendingMachines() {
        Set<VendingMachine> machines = adminController.getAllVendingMachines();
        if (machines.isEmpty()) {
            System.out.println("No vending machines registered yet.");
            return;
        }
        System.out.println("\n===== All Vending Machines =====");
        for (VendingMachine vm : machines) {
            System.out.println(vm);
            System.out.println("--------------------------------");
        }
    }

    private void viewAllFoods() {
        Set<Food> foods = adminController.getAllFoods();
        if (foods.isEmpty()) {
            System.out.println("No food items registered yet.");
            return;
        }
        System.out.println("\n===== All Food Items =====");
        for (Food food : foods) {
            System.out.println(food);
            System.out.println("-------------------------");
        }
    }

    private void viewProductCount() {
        System.out.println("\n--- Product Count at Machine ---");
        String vmId = prompt("Vending machine ID: ");

        Map<String, Integer> stockMap = adminController.getProductCountForMachine(vmId);

        if (stockMap.isEmpty()) {
            System.out.println("No products currently stocked in this machine.");
            return;
        }

        System.out.printf("\n  %-14s %-22s %8s  %6s%n", "Food ID", "Name", "Price", "Stock");
        System.out.println("  ──────────────────────────────────────────────────");
        for (Map.Entry<String, Integer> entry : stockMap.entrySet()) {
            Food food = adminController.getFoodById(entry.getKey());
            System.out.printf("  %-14s %-22s Rs.%-5s  %6d%n",
                    food.getProductId(),
                    food.getProductName(),
                    food.getPrice(),
                    entry.getValue());
        }
        System.out.println("  ──────────────────────────────────────────────────");
        int total = stockMap.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println("  Total units : " + total);
    }

    private void viewCashDrawer() {
        System.out.println("\n--- View Cash Drawer ---");
        String vmId = prompt("Vending machine ID: ");

        System.out.println("\n===== Cash Drawer — " + vmId + " =====");
        for (Map.Entry<util.IndianCurrency, Integer> entry : adminController.getDenominationBreakdown(vmId).entrySet()) {
            System.out.printf("  Rs.%-4d  x  %d%n", entry.getKey().getValue(), entry.getValue());
        }
        System.out.println("  ──────────────────────");
        System.out.println("  Total : Rs." + adminController.getTotalCashInMachine(vmId));
    }

    private void addCashToDrawer() {
        System.out.println("\n--- Add Cash to Drawer ---");
        String vmId = prompt("Vending machine ID: ");

        Map<util.IndianCurrency, Integer> denominations = new java.util.EnumMap<>(util.IndianCurrency.class);
        System.out.println("Enter how many of each denomination to add (Enter to skip):");
        for (util.IndianCurrency denom : util.IndianCurrency.values()) {
            String input = prompt("  Rs." + denom.getValue() + ": ");
            if (input.isEmpty()) continue;
            try {
                int count = Integer.parseInt(input);
                if (count > 0) denominations.put(denom, count);
                else System.out.println("  Skipped — must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input, skipping Rs." + denom.getValue());
            }
        }

        if (denominations.isEmpty()) {
            System.out.println("Nothing added.");
            return;
        }

        adminController.addCashToDrawer(vmId, denominations);

        System.out.println("\nCash added. Current drawer for " + vmId + ":");
        for (Map.Entry<util.IndianCurrency, Integer> entry : adminController.getDenominationBreakdown(vmId).entrySet()) {
            System.out.printf("  Rs.%-4d  x  %d%n", entry.getKey().getValue(), entry.getValue());
        }
        System.out.println("  Total : Rs." + adminController.getTotalCashInMachine(vmId));
    }

    private void viewPurchaseHistory() {
        Set<Purchase> purchases = adminController.getAllPurchases();
        if (purchases.isEmpty()) {
            System.out.println("No purchases recorded yet.");
            return;
        }
        System.out.println("\n===== Purchase History =====");
        for (Purchase p : purchases) {
            System.out.println("  ID     : " + p.getPurchaseId());
            System.out.println("  Time   : " + p.getPurchaseTime());
            System.out.println("  Items  : " + p.getQuantityOfProductsPurchased());
            System.out.println("  Total  : Rs." + p.getTotalAmount());
            System.out.println("  Paid   : Rs." + p.getMoneyPaidByCustomer());
            System.out.println("  Change : Rs." + p.getMoneyToBeReturnedByVendingMachine());
            System.out.println("  ────────────────────────────");
        }
    }
}