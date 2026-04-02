import java.util.LinkedList;
import java.util.Queue;

// Booking request class (domain object)
class BookingRequest {
    private String guestName;
    private String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

// Main application class
public class BookmystayAPP {
    public static void main(String[] args) {
        // Create a queue to store booking requests
        Queue<BookingRequest> bookingQueue = new LinkedList<>();

        // Accept booking requests (order preserved)
        bookingQueue.add(new BookingRequest("Abhi", "Single"));
        bookingQueue.add(new BookingRequest("Subha", "Double"));
        bookingQueue.add(new BookingRequest("Vanmathi", "Suite"));

        // Simulate processing the booking queue (no inventory update yet)
        System.out.println("=== Booking Request Queue ===\n");
        while (!bookingQueue.isEmpty()) {
            BookingRequest request = bookingQueue.poll(); // Retrieves and removes head
            System.out.println("Processing booking for Guest: " + request.getGuestName() +
                    ", Room Type: " + request.getRoomType());
        }
    }
}
