import java.util.*;

// Room and Booking classes (simplified for cancellation demo)
class Booking {
    private String guestName;
    private String roomType;
    private String reservationID;

    public Booking(String guestName, String roomType, String reservationID) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationID = reservationID;
    }

    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }
    public String getReservationID() { return reservationID; }
}

// Inventory manager handling bookings and cancellations
class HotelInventory {
    private Map<String, Integer> roomInventory = new HashMap<>();
    private Set<String> allocatedRoomIDs = new HashSet<>();
    private List<Booking> confirmedBookings = new ArrayList<>();
    private int roomCounter = 100; // for unique room IDs

    public HotelInventory() {
        roomInventory.put("Single Room", 5);
        roomInventory.put("Double Room", 3);
        roomInventory.put("Suite Room", 2);
    }

    // Allocate a room
    public Booking confirmBooking(String guestName, String roomType) {
        int available = roomInventory.getOrDefault(roomType, 0);
        if (available <= 0) {
            System.out.println("No available rooms for " + guestName + " (" + roomType + ")");
            return null;
        }
        String roomID = generateRoomID(roomType);
        roomInventory.put(roomType, available - 1);
        Booking booking = new Booking(guestName, roomType, roomID);
        confirmedBookings.add(booking);
        System.out.println("Booking confirmed: " + guestName + ", " + roomType + " (" + roomID + ")");
        return booking;
    }

    // Generate unique room ID
    private String generateRoomID(String roomType) {
        String id;
        do {
            id = roomType.substring(0, 1).toUpperCase() + roomCounter++;
        } while (allocatedRoomIDs.contains(id));
        allocatedRoomIDs.add(id);
        return id;
    }

    // Cancel a booking
    public void cancelBooking(String reservationID) {
        Booking bookingToCancel = null;
        for (Booking b : confirmedBookings) {
            if (b.getReservationID().equals(reservationID)) {
                bookingToCancel = b;
                break;
            }
        }

        if (bookingToCancel == null) {
            System.out.println("Cancellation failed: Reservation ID " + reservationID + " does not exist.");
            return;
        }

        // Restore inventory and release room ID
        String roomType = bookingToCancel.getRoomType();
        roomInventory.put(roomType, roomInventory.getOrDefault(roomType, 0) + 1);
        allocatedRoomIDs.remove(reservationID);
        confirmedBookings.remove(bookingToCancel);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
        System.out.println("Released Reservation ID: " + reservationID);
        System.out.println("Updated " + roomType + " Availability: " + roomInventory.get(roomType) + "\n");
    }

    // Display current bookings
    public void displayBookings() {
        System.out.println("=== Current Confirmed Bookings ===");
        for (Booking b : confirmedBookings) {
            System.out.println(b.getGuestName() + " - " + b.getRoomType() + " (" + b.getReservationID() + ")");
        }
        System.out.println();
    }
}

// Main application
public class BookmystayAPP {
    public static void main(String[] args) {
        HotelInventory inventory = new HotelInventory();

        // Confirm bookings
        Booking b1 = inventory.confirmBooking("Abhi", "Single Room");
        Booking b2 = inventory.confirmBooking("Subha", "Double Room");

        inventory.displayBookings();

        // Cancel a booking
        if (b1 != null) {
            inventory.cancelBooking(b1.getReservationID());
        }

        inventory.displayBookings();

        // Attempt to cancel a non-existent booking
        inventory.cancelBooking("NonExistent-999");
    }
}
