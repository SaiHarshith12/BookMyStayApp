import java.util.*;
import java.util.concurrent.*;

// Booking class
class Booking {
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

// Thread-safe Inventory Manager
class HotelInventory {
    private Map<String, Integer> roomInventory = new HashMap<>();
    private Map<String, Integer> roomCounters = new HashMap<>(); // for unique IDs
    private List<Booking> confirmedBookings = new ArrayList<>();

    public HotelInventory() {
        roomInventory.put("Single Room", 5);
        roomInventory.put("Double Room", 3);
        roomInventory.put("Suite Room", 2);
        roomCounters.put("Single Room", 1);
        roomCounters.put("Double Room", 1);
        roomCounters.put("Suite Room", 1);
    }

    // Thread-safe booking method
    public synchronized Booking confirmBooking(String guestName, String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            System.out.println("No available rooms for Guest: " + guestName + " (" + roomType + ")");
            return null;
        }
        // Allocate room ID
        int counter = roomCounters.get(roomType);
        String roomID = roomType.split(" ")[0] + "-" + counter;
        roomCounters.put(roomType, counter + 1);

        // Update inventory and bookings
        roomInventory.put(roomType, available - 1);
        Booking booking = new Booking(guestName, roomType, roomID);
        confirmedBookings.add(booking);
        System.out.println("Booking confirmed for Guest: " + guestName + ", Room ID: " + roomID);
        return booking;
    }

    public synchronized void displayInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : roomInventory.keySet()) {
            System.out.println(type.split(" ")[0] + ": " + roomInventory.get(type));
        }
        System.out.println();
    }

    public List<Booking> getConfirmedBookings() {
        return Collections.unmodifiableList(confirmedBookings);
    }
}

// Booking task to run in threads
class BookingTask implements Runnable {
    private HotelInventory inventory;
    private String guestName;
    private String roomType;

    public BookingTask(HotelInventory inventory, String guestName, String roomType) {
        this.inventory = inventory;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    @Override
    public void run() {
        inventory.confirmBooking(guestName, roomType);
    }
}

// Main application
public class BookmystayAPP {
    public static void main(String[] args) throws InterruptedException {
        HotelInventory inventory = new HotelInventory();

        // Simulate multiple concurrent booking requests
        ExecutorService executor = Executors.newFixedThreadPool(4); // 4 concurrent threads

        executor.submit(new BookingTask(inventory, "Abhi", "Single Room"));
        executor.submit(new BookingTask(inventory, "Vanmathi", "Double Room"));
        executor.submit(new BookingTask(inventory, "Kural", "Suite Room"));
        executor.submit(new BookingTask(inventory, "Subha", "Single Room"));

        // Shutdown executor and wait for tasks to finish
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        // Display remaining inventory
        inventory.displayInventory();
    }
}
