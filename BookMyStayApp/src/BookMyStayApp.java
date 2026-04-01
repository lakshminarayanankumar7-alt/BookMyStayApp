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
        inventory.put("Suite", 0);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public Map<String, Integer> getAllInventory() {
        return inventory;
    }
}
class RoomSearchService {
    public void searchAvailableRooms(RoomInventory inventory) {
        System.out.println("\nAvailable Rooms:\n");
        Room single = new SingleRoom();
        Room dbl = new DoubleRoom();
        Room suite = new SuiteRoom();
        if (inventory.getAvailability("Single") > 0) {
            System.out.println("Single Room:");
            single.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Single") + "\n");
        }

        if (inventory.getAvailability("Double") > 0) {
            System.out.println("Double Room:");
            dbl.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Double") + "\n");
        }

        if (inventory.getAvailability("Suite") > 0) {
            System.out.println("Suite Room:");
            suite.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Suite") + "\n");
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Room Search System\n");
        RoomInventory inventory = new RoomInventory();
        RoomSearchService searchService = new RoomSearchService();
        searchService.searchAvailableRooms(inventory);
    }
}