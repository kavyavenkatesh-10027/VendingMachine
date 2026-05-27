package ui;

import controller.ConsumerController;
import model.*;
import util.*;

import java.math.BigDecimal;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsumerUI {
    private final Scanner scanner;
    private final ConsumerController controller = new ConsumerController();

    public ConsumerUI(Scanner scanner) {
        this.scanner = scanner;
    }

    public void show() {
        boolean running = true;
        while (running) {
            printHeader("CUSTOMER MENU");
            System.out.println("  1. View all vending machines");
            System.out.println("  2. Browse products at a machine");
            System.out.println("  3. Buy products");
            System.out.println("  0. Exit");
            System.out.println("=====================================");

            try {
                switch (prompt("Your choice")) {
                    case "1":
                        viewAllMachines();
                        break;
                    case "2":
                        viewProducts();
                        break;
                    case "3":
                        buyProducts();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        System.out.println("  Invalid choice, please enter a choice from 0–3.");
                        break;
                }
            } catch (VendingMachineException e) {
                System.out.println("\n  [!] " + e.getMessage());
            }
        }
        System.out.println("\n  Goodbye!\n");
    }

    private void viewAllMachines() {
        List<VendingMachine> machines = controller.viewAllVendingMachines();
        printHeader("VENDING MACHINES");

        if (machines.isEmpty()) {
            System.out.println("  No machines available yet.");
            return;
        }

        for (VendingMachine vm : machines) {
            System.out.println("  ID       : " + vm.getVendingMachineId());
            System.out.println("  Location : " + vm.getVendingMachineLocation());
            System.out.println("  Since    : " + vm.getEstablishedOn());
            System.out.println("  Slots    : " + vm.getSlotsInVendingMachine().size());
            System.out.println("─────────────────────────────────────");
        }
    }


    private void viewProducts() {
        String vmId = prompt("Vending machine ID");
        printAvailableProducts(vmId);
    }

    private void printAvailableProducts(String vmId) {
        List<Food> products = controller.viewAvailableProducts(vmId);
        printHeader("AVAILABLE PRODUCTS");

        if (products.isEmpty()) {
            System.out.println("  No products in stock at this machine.");
            return;
        }

        System.out.printf("  %-14s %-22s %8s  %6s  %s%n",
                "Food ID", "Name", "Price", "Stock", "Type");
        System.out.println("─────────────────────────────────────");

        for (Food food : products) {
            int qty = controller.getAvailableStock(vmId, food.getProductId());
            System.out.printf("  %-14s %-22s Rs.%-5s  %6d  %s / %s%n",
                    food.getProductId(),
                    food.getProductName(),
                    food.getPrice(),
                    qty,
                    food.getFoodType(),
                    food.getVegOrNonVeg());
        }
    }

    private void buyProducts() {
        String vmId = prompt("Vending machine ID");

        List<Food> products = controller.viewAvailableProducts(vmId);
        if (products.isEmpty()) {
            System.out.println("\n  No products in stock at this machine.");
            return;
        }

        printAvailableProducts(vmId);

        Map<String, Integer> cart = buildCart(vmId);
        if (cart.isEmpty()) {
            System.out.println("\n  Nothing added to cart. Returning to menu.");
            return;
        }

        BigDecimal total = controller.getCartTotal(cart);
        System.out.println("\n  Cart total : Rs." + total);

        Map<IndianCurrency, Integer> payment = collectPayment(total);
        if (payment.isEmpty()) {
            System.out.println("  Purchase cancelled — no payment received.");
            return;
        }

        Purchase purchase = controller.buyProducts(vmId, cart, payment);
        printReceipt(purchase);
    }

    private Map<String, Integer> buildCart(String vmId) {
        Map<String, Integer> cart = new HashMap<>();
        System.out.println("\n  Add items to cart (leave Food ID blank when done):");

        while (true) {
            String foodId = prompt("    Food ID");
            if (foodId.trim().isEmpty()){
                break;
            }

            int available;
            try {
                available = controller.getAvailableStock(vmId, foodId);
            } catch (VendingMachineException e) {
                System.out.println("    [!] " + e.getMessage());
                continue;
            }

            if (available == 0) {
                System.out.println("    [!] That item is out of stock.");
                continue;
            }

            int qty = readInt("    Quantity (available: " + available + ")");
            if (qty <= 0) {
                System.out.println("    [!] Quantity must be at least 1.");
                continue;
            }
            if (qty > available) {
                System.out.println("    [!] Only " + available + " units available.");
                continue;
            }

            cart.merge(foodId, qty, Integer::sum);
            System.out.println("    Added " + qty + " × " + foodId);
        }

        return cart;
    }

    private Map<IndianCurrency, Integer> collectPayment(BigDecimal totalRequired) {
        Map<IndianCurrency, Integer> payment = new EnumMap<>(IndianCurrency.class);
        BigDecimal paid = BigDecimal.ZERO;

        System.out.println("\n  Accepted denominations:");
        for (IndianCurrency c : IndianCurrency.values()) {
            System.out.print("  " + c.name() + "(Rs." + c.getValue() + ")");
        }
        System.out.println();
        System.out.println("  Type a denomination name to insert it, or DONE to cancel.\n");

        while (paid.compareTo(totalRequired) < 0) {
            BigDecimal remaining = totalRequired.subtract(paid);
            System.out.printf("  Paid: Rs.%-8s  Still needed: Rs.%s%n", paid, remaining);
            String input = prompt("  Insert").toUpperCase();

            if (input.equals("DONE")) {
                System.out.println("  Payment cancelled.");
                return new EnumMap<>(IndianCurrency.class);
            }

            try {
                IndianCurrency coin = IndianCurrency.valueOf(input);
                payment.merge(coin, 1, Integer::sum);
                paid = paid.add(BigDecimal.valueOf(coin.getValue()));
                System.out.println("  Accepted Rs." + coin.getValue()
                        + "  |  Total inserted so far: Rs." + paid);
            } catch (IllegalArgumentException e) {
                System.out.println("  [!] Unknown denomination. Try again.");
            }
        }

        return payment;
    }

    private void printReceipt(Purchase purchase) {
        System.out.println("\n=====================================");
        System.out.println("              RECEIPT");
        System.out.println("=====================================");
        System.out.println("  Purchase ID : " + purchase.getPurchaseId());
        System.out.println("  Time        : " + purchase.getPurchaseTime());
        System.out.println("  Items       : " + purchase.getQuantityOfProductsPurchased());
        System.out.println("  Total       : Rs." + purchase.getTotalAmount());
        System.out.println("  Paid        : Rs." + purchase.getMoneyPaidByCustomer());
        System.out.println("  Change      : Rs." + purchase.getMoneyToBeReturnedByVendingMachine());
        System.out.println("=====================================");
        if (purchase.getMoneyToBeReturnedByVendingMachine().compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("  Please collect your change: Rs."
                    + purchase.getMoneyToBeReturnedByVendingMachine());
        }
        System.out.println("  Thank you for your purchase!");
        System.out.println("=====================================" + "\n");
    }

    //These methods seemed like utils for this particular class hence writing them here
    private void printHeader(String title) {
        System.out.println("\n" + "=====================================");
        System.out.printf("  %s%n", title);
        System.out.println("=====================================");
    }

    private String prompt(String label) {
        System.out.print(label + ": ");
        return scanner.nextLine().trim();
    }

    private int readInt(String label) {
        while (true) {
            System.out.print(label + ": ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("  [!] Please enter a whole number.");
            }
        }
    }
}