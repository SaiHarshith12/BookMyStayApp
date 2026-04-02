abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size; // in sqft
    protected double pricePerNight;
    protected int availableRooms;

    public Room(String roomType, int beds, int size, double pricePerNight, int availableRooms) {
        // Defensive programming: validate inputs
        if (beds <= 0) throw new IllegalArgumentException("Beds must be > 0");
        if (size <= 0) throw new IllegalArgumentException("Size must be > 0");
        if (pricePerNight < 0) throw new IllegalArgumentException("Price cannot be negative");
        if (availableRooms < 0) throw new IllegalArgumentException("Available rooms cannot be negative");

        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
    }

    public void displayDetails() {
        System.out.println(roomType + ":");
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sqft");
        System.out.println("Price per night: " + pricePerNight);
        System.out.println("Available: " + availableRooms + "\n");
    }
}

// Concrete room classes
class SingleRoom extends Room {
    public SingleRoom(int availableRooms) {
        super("Single Room", 1, 250, 1500.0, availableRooms);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom(int availableRooms) {
        super("Double Room", 2, 400, 2500.0, availableRooms);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(int availableRooms) {
        super("Suite Room", 3, 750, 5000.0, availableRooms);
    }
}

// Main application
public class BookmystayAPP {
    public static void main(String[] args) {
        try {
            Room[] rooms = {
                    new SingleRoom(5),
                    new DoubleRoom(3),
                    new SuiteRoom(2)
            };

            System.out.println("=== Hotel Room Initialization ===\n");
            for (Room room : rooms) {
                room.displayDetails();
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Error initializing rooms: " + e.getMessage());
        }
    }
}
