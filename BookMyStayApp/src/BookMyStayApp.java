import java.util.HashMap;
import java.util.Map;
abstract class Room {
    protected int numberOfBeds;
    protected int squareFeet;
    protected double pricePerNight;

    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}
class RoomInventory {
    private HashMap<String, Integer> inventory;
    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Invalid room type!");
        }
    }
    public boolean bookRoom(String roomType) {
        int available = getAvailability(roomType);

        if (available > 0) {
            inventory.put(roomType, available - 1);
            return true;
        } else {
            return false;
        }
    }
    public void displayInventory() {
        System.out.println("\nCurrent Room Inventory:");
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + " Rooms Available: " + entry.getValue());
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Hotel Room Inventory System\n");
        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();
        RoomInventory inventory = new RoomInventory();
        System.out.println("Single Room:");
        single.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Single") + "\n");
        System.out.println("Double Room:");
        dbl.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Double") + "\n");
        System.out.println("Suite Room:");
        suite.displayRoomDetails();
        System.out.println("Available: " + inventory.getAvailability("Suite"));
        inventory.displayInventory();
        System.out.println("\nBooking a Single Room...");
        if (inventory.bookRoom("Single")) {
            System.out.println("Booking successful!");
        } else {
            System.out.println("No rooms available!");
        }
        inventory.displayInventory();
    }
}