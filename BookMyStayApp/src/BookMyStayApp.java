import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class BookMyStayApp {

    // ================== Inventory मॉडल ==================
    static class Inventory implements Serializable {
        private static final long serialVersionUID = 1L;

        private Map<String, Integer> rooms = new HashMap<>();

        public void addRoomType(String type, int count) {
            rooms.put(type, count);
        }

        public void bookRoom(String type) {
            if (!rooms.containsKey(type) || rooms.get(type) <= 0) {
                throw new RuntimeException("Room not available: " + type);
            }
            rooms.put(type, rooms.get(type) - 1);
        }

        public Map<String, Integer> getRooms() {
            return rooms;
        }

        public void printInventory() {
            System.out.println("Current Inventory:");
            rooms.forEach((k, v) -> System.out.println(k + ": " + v));
        }
    }

    // ================== Persistence Service ==================
    static class FilePersistenceService {
        private static final String FILE_NAME = "inventory.dat";

        // Save
        public void saveInventory(Inventory inventory) {
            try (ObjectOutputStream oos =
                         new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

                oos.writeObject(inventory);
                System.out.println("System: State saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
            }
        }

        // Load
        public Inventory loadInventory() {
            File file = new File(FILE_NAME);

            if (!file.exists()) {
                System.out.println("No saved inventory data found. Starting fresh.");
                return new Inventory();
            }

            try (ObjectInputStream ois =
                         new ObjectInputStream(new FileInputStream(FILE_NAME))) {

                System.out.println("System: Loaded saved inventory data.");
                return (Inventory) ois.readObject();

            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Corrupted data. Starting fresh.");
                return new Inventory();
            }
        }
    }

    // ================== Main (Use Case 12) ==================
    public static void main(String[] args) {

        FilePersistenceService persistenceService = new FilePersistenceService();

        // 🔁 Step 1: Recover state
        Inventory inventory = persistenceService.loadInventory();

        // 🆕 Initialize if first run
        if (inventory.getRooms().isEmpty()) {
            inventory.addRoomType("Single", 5);
            inventory.addRoomType("Double", 3);
            inventory.addRoomType("Suite", 2);
        }

        // 📊 Show current inventory
        inventory.printInventory();

        // 🏨 Simulate booking
        try {
            inventory.bookRoom("Single");
            System.out.println("Booked 1 Single room.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 💾 Step 2: Save state before shutdown
        persistenceService.saveInventory(inventory);
    }
}