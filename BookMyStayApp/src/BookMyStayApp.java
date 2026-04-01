import java.util.*;
public class BookMyStayApp {
    static class BookingRequest {
        String guestName;
        String roomType;

        public BookingRequest(String guestName, String roomType) {
            this.guestName = guestName;
            this.roomType = roomType;
        }
    }
    static class RoomAllocationService {

        private Set<String> allocatedRooms = new HashSet<>();
        private Map<String, Set<String>> assignedRoomsByType = new HashMap<>();
        private Map<String, Integer> inventory = new HashMap<>();

        public RoomAllocationService() {
            inventory.put("Deluxe", 2);
            inventory.put("Suite", 1);
        }

        public void allocateReservation(BookingRequest request) {

            String roomType = request.roomType;
            if (!inventory.containsKey(roomType) || inventory.get(roomType) <= 0) {
                System.out.println("No rooms available for " + roomType +
                        " (Guest: " + request.guestName + ")");
                return;
            }
            String roomId = generateRoomId(roomType);
            allocatedRooms.add(roomId);

            assignedRoomsByType
                    .computeIfAbsent(roomType, k -> new HashSet<>())
                    .add(roomId);
            inventory.put(roomType, inventory.get(roomType) - 1);
            System.out.println("Booking confirmed for " + request.guestName +
                    ", " + roomType + " Room ID: " + roomId);
        }

        private String generateRoomId(String roomType) {
            int counter = 1;
            String roomId;

            do {
                roomId = roomType.charAt(0) + String.valueOf(counter++);
            } while (allocatedRooms.contains(roomId));

            return roomId;
        }
    }
    static class BookingService {

        private Queue<BookingRequest> queue = new LinkedList<>();
        private RoomAllocationService allocationService = new RoomAllocationService();

        public void addRequest(String guestName, String roomType) {
            queue.offer(new BookingRequest(guestName, roomType));
        }

        public void processRequests() {
            while (!queue.isEmpty()) {
                BookingRequest request = queue.poll();
                allocationService.allocateReservation(request);
            }
        }
    }

    public static void main(String[] args) {

        BookingService service = new BookingService();

        service.addRequest("Alice", "Deluxe");
        service.addRequest("Bob", "Suite");
        service.addRequest("Charlie", "Deluxe");
        service.addRequest("David", "Suite"); // should fail (no inventory)

        service.processRequests();
    }
}