import java.util.*;
import java.util.concurrent.*;

// Booking Request Model
class BookingRequest {
    String guestName;
    String roomType;

    public BookingRequest(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// Shared Inventory (Thread-Safe)
class Inventory {
    private Map<String, Integer> rooms = new HashMap<>();

    public Inventory() {
        rooms.put("Single", 4);
        rooms.put("Double", 3);
        rooms.put("Suite", 2);
    }

    // Critical Section (SYNCHRONIZED)
    public synchronized boolean bookRoom(String roomType) {
        int available = rooms.getOrDefault(roomType, 0);

        if (available > 0) {
            rooms.put(roomType, available - 1);
            return true;
        }
        return false;
    }

    public void printInventory() {
        System.out.println("\nRemaining Inventory:");
        for (String type : rooms.keySet()) {
            System.out.println(type + ": " + rooms.get(type));
        }
    }
}

// Booking Processor (Runnable Thread)
class BookingProcessor implements Runnable {
    private Queue<BookingRequest> queue;
    private Inventory inventory;

    public BookingProcessor(Queue<BookingRequest> queue, Inventory inventory) {
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {
        while (true) {
            BookingRequest request;

            // Synchronize queue access
            synchronized (queue) {
                if (queue.isEmpty()) return;
                request = queue.poll();
            }

            // Process booking
            boolean success = inventory.bookRoom(request.roomType);

            if (success) {
                System.out.println("Booking confirmed for Guest: " +
                        request.guestName + ", Room ID: " +
                        request.roomType + "-1");
            } else {
                System.out.println("Booking failed for Guest: " +
                        request.guestName + " (No rooms available)");
            }
        }
    }
}

// Main Class
public class BookMyStayApp {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("Concurrent Booking Simulation\n");

        Queue<BookingRequest> queue = new LinkedList<>();

        // Simulated concurrent requests
        queue.add(new BookingRequest("Abhi", "Single"));
        queue.add(new BookingRequest("Vanmathi", "Double"));
        queue.add(new BookingRequest("Kural", "Suite"));
        queue.add(new BookingRequest("Subha", "Single"));

        Inventory inventory = new Inventory();

        // Create multiple threads
        Thread t1 = new Thread(new BookingProcessor(queue, inventory));
        Thread t2 = new Thread(new BookingProcessor(queue, inventory));
        Thread t3 = new Thread(new BookingProcessor(queue, inventory));

        // Start threads
        t1.start();
        t2.start();
        t3.start();

        // Wait for completion
        t1.join();
        t2.join();
        t3.join();

        // Print final inventory
        inventory.printInventory();
    }
}