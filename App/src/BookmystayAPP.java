import java.util.HashMap;
import java.util.Map;

// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size; // in sqft
    protected double pricePerNight;

    public Room(String roomType, int beds, int size, double pricePerNight) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.pricePerNight = pricePerNight;
    }

    // Display room details with availability
    public void displayDetails(int availableRooms) {
        System.out.println(roomType + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + pricePerNight);
        System.out.println("Available Rooms: " + availableRooms + "\n");
    }

    public String getRoomType() {
        return roomType;
    }
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 250, 1500.0);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 400, 2500.0);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 750, 5000.0);
    }
}

// Inventory management class
class HotelInventory {
    private Map<String, Integer> roomInventory = new HashMap<>();

    public HotelInventory() {
        roomInventory.put("Single Room", 5);
        roomInventory.put("Double Room", 3);
        roomInventory.put("Suite Room", 2);
    }

    // Retrieve current availability (read-only)
    public int getAvailability(String roomType) {
        return roomInventory.getOrDefault(roomType, 0);
    }

    // Controlled booking logic
    public boolean bookRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    // Search method: returns room types with availability > 0
    public void searchAvailableRooms(Room[] rooms) {
        System.out.println("=== Available Rooms ===\n");
        for (Room room : rooms) {
            int available = getAvailability(room.getRoomType());
            if (available > 0) { // Only show rooms with availability
                room.displayDetails(available);
            }
        }
    }
}

// Main application class
public class BookmystayAPP {
    public static void main(String[] args) {
        // Initialize inventory
        HotelInventory inventory = new HotelInventory();

        // Create room objects (domain objects)
        Room[] rooms = {new SingleRoom(), new DoubleRoom(), new SuiteRoom()};

        // Search for available rooms (does NOT modify inventory)
        inventory.searchAvailableRooms(rooms);

        // Example: Booking a room (separate booking logic)
        System.out.println("Booking a Double Room...\n");
        if (inventory.bookRoom("Double Room")) {
            System.out.println("Booking successful!\n");
        } else {
            System.out.println("No Double Rooms available!\n");
        }

        // Display updated available rooms after booking
        inventory.searchAvailableRooms(rooms);
    }
}
