import java.io.*;
import java.util.*;

// Booking class (Serializable)
class Booking implements Serializable {
    private static final long serialVersionUID = 1L;
    private String guestName;
    private String roomType;
    private String roomID;

    public Booking(String guestName, String roomType, String roomID) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomID = roomID;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getRoomID() { return roomID; }
}

// HotelInventory with persistence
class HotelInventory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Integer> roomInventory = new HashMap<>();
    private Map<String, Integer> roomCounters = new HashMap<>();
    private List<Booking> confirmedBookings = new ArrayList<>();

    public HotelInventory() {
        roomInventory.put("Single Room", 5);
        roomInventory.put("Double Room", 3);
        roomInventory.put("Suite Room", 2);
        roomCounters.put("Single Room", 1);
        roomCounters.put("Double Room", 1);
        roomCounters.put("Suite Room", 1);
    }

    // Load inventory from file
    public static HotelInventory loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (HotelInventory) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("No valid inventory data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error reading inventory data. Starting fresh.");
        }
        return new HotelInventory();
    }

    // Save inventory to file
    public void saveToFile(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("Inventory saved successfully.\n");
        } catch (IOException e) {
            System.out.println("Error saving inventory data: " + e.getMessage());
        }
    }

    // Display current inventory
    public void displayInventory() {
        System.out.println("Current Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type.split(" ")[0] + ": " + roomInventory.get(type));
        }
        System.out.println();
    }

    // Thread-safe booking
    public synchronized Booking confirmBooking(String guestName, String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            System.out.println("No available rooms for Guest: " + guestName + " (" + roomType + ")");
            return null;
        }
        int counter = roomCounters.get(roomType);
        String roomID = roomType.split(" ")[0] + "-" + counter;
        roomCounters.put(roomType, counter + 1);

        roomInventory.put(roomType, available - 1);
        Booking booking = new Booking(guestName, roomType, roomID);
        confirmedBookings.add(booking);
        System.out.println("Booking confirmed for Guest: " + guestName + ", Room ID: " + roomID);
        return booking;
    }

    public List<Booking> getConfirmedBookings() {
        return Collections.unmodifiableList(confirmedBookings);
    }
}

// Main application
public class BookmystayAPP {
    private static final String DATA_FILE = "hotel_inventory.dat";

    public static void main(String[] args) {
        // Load inventory from file (recovery)
        HotelInventory inventory = HotelInventory.loadFromFile(DATA_FILE);
        inventory.displayInventory();

        // Simulate some bookings
        inventory.confirmBooking("Abhi", "Single Room");
        inventory.confirmBooking("Vanmathi", "Double Room");

        inventory.displayInventory();

        // Save inventory and booking history to file
        inventory.saveToFile(DATA_FILE);
    }
}
