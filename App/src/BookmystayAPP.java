import java.util.*;

// Service class
class Service {
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() { return serviceName; }
    public double getPrice() { return price; }
}

// Add-on Service Manager
class AddOnServiceManager {
    // Map reservation ID -> list of services
    private Map<String, List<Service>> reservationServices = new HashMap<>();

    // Add a service to a reservation
    public void addService(String reservationID, Service service) {
        reservationServices.computeIfAbsent(reservationID, k -> new ArrayList<>()).add(service);
    }

    // Calculate total add-on cost for a reservation
    public double calculateTotalCost(String reservationID) {
        List<Service> services = reservationServices.getOrDefault(reservationID, Collections.emptyList());
        double total = 0.0;
        for (Service s : services) {
            total += s.getPrice();
        }
        return total;
    }

    // Display services for a reservation
    public void displayServices(String reservationID) {
        List<Service> services = reservationServices.getOrDefault(reservationID, Collections.emptyList());
        System.out.println("Reservation ID: " + reservationID);
        if (services.isEmpty()) {
            System.out.println("No add-on services selected.");
        } else {
            System.out.println("Selected Add-On Services:");
            for (Service s : services) {
                System.out.println("- " + s.getServiceName() + " ($" + s.getPrice() + ")");
            }
            System.out.println("Total Add-On Cost: " + calculateTotalCost(reservationID));
        }
        System.out.println();
    }
}

// Main application demonstrating add-on service selection
public class BookmystayAPP {
    public static void main(String[] args) {
        AddOnServiceManager addOnManager = new AddOnServiceManager();

        // Create some services
        Service breakfast = new Service("Breakfast", 500.0);
        Service airportPickup = new Service("Airport Pickup", 1000.0);
        Service spa = new Service("Spa Package", 2000.0);

        // Attach services to reservations (without touching inventory)
        addOnManager.addService("Single-1", breakfast);
        addOnManager.addService("Single-1", airportPickup);
        addOnManager.addService("Double-1", spa);

        // Display selected services for each reservation
        addOnManager.displayServices("Single-1");
        addOnManager.displayServices("Double-1");
        addOnManager.displayServices("Suite-1"); // no services
    }
}

