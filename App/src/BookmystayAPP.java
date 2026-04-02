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

// Main application class
public class BookmystayAPP {
    // Store room availability in a HashMap
    private Map<String, Integer> roomInventory = new HashMap<>();

    // Initialize room availability using constructor
    public BookmystayAPP() {
        roomInventory.put("Single Room", 5);
        roomInventory.put("Double Room", 3);
        roomInventory.put("Suite Room", 2);
    }

    // Retrieve current availability
    public int getAvailability(String roomType) {
        return roomInventory.getOrDefault(roomType, 0);
    }

    // Controlled update of availability
    public boolean bookRoom(String roomType) {
        int available = getAvailability(roomType);
        if (available > 0) {
            roomInventory.put(roomType, available - 1);
            return true;
        } else {
            return false; // No rooms available
        }
    }

    // Display full inventory
    public void displayInventory(Room[] rooms) {
        System.out.println("=== Hotel Room Inventory Status ===\n");
        for (Room room : rooms) {
            room.displayDetails(getAvailability(room.getRoomType()));
        }
    }

    public static void main(String[] args) {
        // Initialize the app
        BookmystayAPP app = new BookmystayAPP();

        // Create room objects
        Room[] rooms = {new SingleRoom(), new DoubleRoom(), new SuiteRoom()};

        // Display initial inventory
        app.displayInventory(rooms);

        // Example: Book a single room
        System.out.println("Booking a Single Room...");
        if (app.bookRoom("Single Room")) {
            System.out.println("Booking successful!\n");
        } else {
            System.out.println("No Single Rooms available!\n");
        }

        // Display updated inventory
        app.displayInventory(rooms);
    }
}
