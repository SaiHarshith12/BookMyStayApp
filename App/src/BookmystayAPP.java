// Abstract Room class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected int size; // in sqft
    protected double pricePerNight;
    protected int availableRooms;

    public Room(String roomType, int beds, int size, double pricePerNight, int availableRooms) {
        this.roomType = roomType;
        this.beds = beds;
        this.size = size;
        this.pricePerNight = pricePerNight;
        this.availableRooms = availableRooms;
    }

    // Display room details
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

// Main application class
public class BookmystayAPP {
    public static void main(String[] args) {
        // Initialize rooms with availability
        SingleRoom singleRoom = new SingleRoom(5);
        DoubleRoom doubleRoom = new DoubleRoom(3);
        SuiteRoom suiteRoom = new SuiteRoom(2);

        // Display all room details
        System.out.println("=== Hotel Room Initialization ===\n");
        singleRoom.displayDetails();
        doubleRoom.displayDetails();
        suiteRoom.displayDetails();
    }
}
