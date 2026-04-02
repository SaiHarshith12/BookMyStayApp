import java.util.*;

// Booking class to represent a confirmed reservation
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

// Booking history manager
class BookingHistory {
    private List<Booking> confirmedBookings = new ArrayList<>();

    // Add a confirmed reservation
    public void addBooking(Booking booking) {
        confirmedBookings.add(booking);
    }

    // Retrieve all bookings (read-only)
    public List<Booking> getConfirmedBookings() {
        return Collections.unmodifiableList(confirmedBookings);
    }

    // Generate a summary report
    public void generateReport() {
        System.out.println("=== Booking History Report ===\n");
        for (Booking booking : confirmedBookings) {
            System.out.println("Guest: " + booking.getGuestName() +
                    ", Room Type: " + booking.getRoomType());
        }
        System.out.println();
    }
}

// Main application class
public class BookmystayAPP {
    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();

        // Simulate confirmed bookings
        history.addBooking(new Booking("Abhi", "Single", "S100"));
        history.addBooking(new Booking("Subha", "Double", "D101"));
        history.addBooking(new Booking("Vanmathi", "Suite", "SU102"));

        // Generate a report without modifying stored data
        history.generateReport();

        // Example of retrieving the bookings for review
        List<Booking> bookings = history.getConfirmedBookings();
        System.out.println("Total confirmed bookings: " + bookings.size());
    }
}
