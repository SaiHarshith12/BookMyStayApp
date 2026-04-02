import java.util.*;

// Room domain classes
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

    public String getRoomType() {
        return roomType;
    }

    public void displayDetails() {
        System.out.println(roomType + " - Beds: " + beds + ", Size: " + size + " sqft, Price: " + pricePerNight);
    }
}

class SingleRoom extends Room {
    public SingleRoom() { super("Single Room", 1, 250, 1500.0); }
}

class DoubleRoom extends Room {
    public DoubleRoom() { super("Double Room", 2, 400, 2500.0); }
}

class SuiteRoom extends Room {
    public SuiteRoom() { super("Suite Room", 3, 750, 5000.0); }
}

// Booking request object
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
}

// Inventory and Booking Manager
class HotelInventory {
    private Map<String, Integer> roomInventory = new HashMap<>();
    private Set<String> allocatedRoomIDs = new HashSet<>();
    private int roomCounter = 100; // for unique room IDs

    public HotelInventory() {
        roomInventory.put("Single Room", 5);
        roomInventory.put("Double Room", 3);
        roomInventory.put("Suite Room", 2);
    }

    // Retrieve availability (read-only)
    public int getAvailability(String roomType) {
        return roomInventory.getOrDefault(roomType, 0);
    }

    // Generate unique room ID
    private String generateRoomID(String roomType) {
        String id;
        do {
            id = roomType.substring(0,1).toUpperCase() + roomCounter++;
        } while (allocatedRoomIDs.contains(id));
        allocatedRoomIDs.add(id);
        return id;
    }

    // Allocate room from booking request
    public void processBookingRequest(BookingRequest request) {
        String type = request.getRoomType();
        int available = getAvailability(type);
        if (available > 0) {
            String roomID = generateRoomID(type);
            roomInventory.put(type, available - 1); // Update inventory
            System.out.println("Allocated Room ID: " + roomID + " to Guest: " + request.getGuestName() +
                    " (" + type + ")");
        } else {
            System.out.println("No available rooms for Guest: " + request.getGuestName() +
                    " (" + type + ")");
        }
    }
}

// Main application
public class BookmystayAPP {
    public static void main(String[] args) {
        // Initialize inventory
        HotelInventory inventory = new HotelInventory();

        // Create booking queue
        Queue<BookingRequest> bookingQueue = new LinkedList<>();
        bookingQueue.add(new BookingRequest("Abhi", "Single Room"));
        bookingQueue.add(new BookingRequest("Subha", "Double Room"));
        bookingQueue.add(new BookingRequest("Vanmathi", "Suite Room"));
        bookingQueue.add(new BookingRequest("Ravi", "Single Room"));
        bookingQueue.add(new BookingRequest("Anu", "Suite Room"));

        // Process bookings in FIFO order
        System.out.println("=== Processing Booking Requests ===\n");
        while (!bookingQueue.isEmpty()) {
            BookingRequest request = bookingQueue.poll();
            inventory.processBookingRequest(request);
        }
    }
}

