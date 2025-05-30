/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package commissionmenu;

import java.util.*;

public class CommissionMenu {
    private Map<Integer, Double> salesRecords;
    private Scanner scanner;

    public CommissionMenu() {
        salesRecords = new HashMap<>();
        scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        while (true) {
            System.out.println("\nCommission System Menu");
            System.out.println("1. Add Sales Record");
            System.out.println("2. Display Sales Records");
            System.out.println("3. Remove Sales Record");
            System.out.println("4. Iterate Through Records");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addSalesRecord();
                    break;
                case 2:
                    displaySalesRecord();
                    break;
                case 3:
                    removeSalesRecord();
                    break;
                case 4:
                    iterateRecords();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addSalesRecord() {
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        System.out.print("Enter sales amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // consume newline
        
        salesRecords.put(id, amount);
        System.out.println("Sales record added successfully.");
    }

    private void displaySalesRecord() {
        System.out.print("Enter employee ID to display: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Double amount = salesRecords.get(id);
        if (amount != null) {
            System.out.printf("Employee %d sales: %.2f OMR%n", id, amount);
        } else {
            System.out.println("No record found for employee ID: " + id);
        }
    }

    private void removeSalesRecord() {
        System.out.print("Enter employee ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        if (salesRecords.remove(id) != null) {
            System.out.println("Sales record removed successfully.");
        } else {
            System.out.println("No record found for employee ID: " + id);
        }
    }

    private void iterateRecords() {
        System.out.println("\nAll Sales Records:");
        for (Map.Entry<Integer, Double> entry : salesRecords.entrySet()) {
            System.out.printf("Employee %d: %.2f OMR%n", entry.getKey(), entry.getValue());
        }
    }

    public static void main(String[] args) {
        CommissionMenu menu = new CommissionMenu();
        menu.displayMenu();
    }
}