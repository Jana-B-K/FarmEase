import java.util.*;
import java.time.LocalDate;

public class FarmEase {
    private static List<Crop> crops = new ArrayList<>();
    private static List<InventoryItem> inventory = new ArrayList<>();
    private static List<Sale> sales = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to FarmEase - Farm Management System");
        
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Manage Crops");
            System.out.println("2. Manage Inventory");
            System.out.println("3. View Sales");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    manageCrops();
                    break;
                case 2:
                    manageInventory();
                    break;
                case 3:
                    viewSales();
                    break;
                case 4:
                    System.out.println("Exiting FarmEase. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Combined crop management in one method
    private static void manageCrops() {
        while (true) {
            System.out.println("\nCrop Management:");
            System.out.println("1. Add Crop");
            System.out.println("2. View Crops");
            System.out.println("3. Remove Crop");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter crop name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter crop type: ");
                    String type = scanner.nextLine();
                    Crop crop = new Crop(name, type);
                    crops.add(crop);
                    System.out.println("Crop added successfully!");
                    break;
                case 2:
                    if (crops.isEmpty()) {
                        System.out.println("No crops available.");
                    } else {
                        System.out.println("\nCurrent Crops:");
                        for (int i = 0; i < crops.size(); i++) {
                            System.out.println((i+1) + ". " + crops.get(i));
                        }
                    }
                    break;
                case 3:
                    if (crops.isEmpty()) {
                        System.out.println("No crops to remove.");
                    } else {
                        System.out.print("Enter crop number to remove: ");
                        int index = scanner.nextInt() - 1;
                        if (index >= 0 && index < crops.size()) {
                            crops.remove(index);
                            System.out.println("Crop removed successfully!");
                        } else {
                            System.out.println("Invalid crop number.");
                        }
                    }
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Simplified inventory management
    private static void manageInventory() {
        while (true) {
            System.out.println("\nInventory Management:");
            System.out.println("1. Add Item");
            System.out.println("2. View Inventory");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    inventory.add(new InventoryItem(name, quantity));
                    System.out.println("Item added to inventory!");
                    break;
                case 2:
                    if (inventory.isEmpty()) {
                        System.out.println("Inventory is empty.");
                    } else {
                        System.out.println("\nCurrent Inventory:");
                        for (InventoryItem item : inventory) {
                            System.out.println(item);
                        }
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // Basic sales viewing
    private static void viewSales() {
        if (sales.isEmpty()) {
            System.out.println("No sales recorded yet.");
        } else {
            System.out.println("\nSales History:");
            double total = 0;
            for (Sale sale : sales) {
                System.out.println(sale);
                total += sale.getTotalPrice();
            }
            System.out.printf("Total Revenue: $%.2f\n", total);
        }
        
        // Simple option to add a sale
        System.out.print("\nRecord a new sale? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            if (crops.isEmpty()) {
                System.out.println("No crops available to sell.");
                return;
            }
            
            System.out.println("Available Crops:");
            for (int i = 0; i < crops.size(); i++) {
                System.out.println((i+1) + ". " + crops.get(i).getName());
            }
            
            System.out.print("Select crop: ");
            int cropIndex = scanner.nextInt() - 1;
            scanner.nextLine();
            
            if (cropIndex >= 0 && cropIndex < crops.size()) {
                System.out.print("Enter quantity sold: ");
                double quantity = scanner.nextDouble();
                System.out.print("Enter price per unit: ");
                double price = scanner.nextDouble();
                scanner.nextLine();
                
                sales.add(new Sale(crops.get(cropIndex), quantity, price));
                System.out.println("Sale recorded!");
            } else {
                System.out.println("Invalid crop selection.");
            }
        }
    }
}

// Simplified model classes
class Crop {
    private String name;
    private String type;
    
    public Crop(String name, String type) {
        this.name = name;
        this.type = type;
    }
    
    public String getName() { return name; }
    public String getType() { return type; }
    
    @Override
    public String toString() {
        return name + " (" + type + ")";
    }
}

class InventoryItem {
    private String name;
    private int quantity;
    
    public InventoryItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }
    
    @Override
    public String toString() {
        return name + " - Qty: " + quantity;
    }
}

class Sale {
    private Crop crop;
    private double quantity;
    private double pricePerUnit;
    private LocalDate date;
    
    public Sale(Crop crop, double quantity, double pricePerUnit) {
        this.crop = crop;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.date = LocalDate.now();
    }
    
    public double getTotalPrice() {
        return quantity * pricePerUnit;
    }
    
    @Override
    public String toString() {
        return String.format("Sold: %s, Qty: %.1f @ $%.2f on %s (Total: $%.2f)",
                crop.getName(), quantity, pricePerUnit, date, getTotalPrice());
    }
}