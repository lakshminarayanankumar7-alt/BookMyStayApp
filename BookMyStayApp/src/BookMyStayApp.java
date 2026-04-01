import java.util.*;
class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public void bookRoom(String roomType) {
        if (rooms.containsKey(roomType)) {
            rooms.put(roomType, rooms.get(roomType) - 1);
        }
    }

    public void releaseRoom(String roomType) {
        if (rooms.containsKey(roomType)) {
            rooms.put(roomType, rooms.get(roomType) + 1);
        }
    }

    public int getAvailability(String roomType) {
        return rooms.getOrDefault(roomType, 0);
    }
}


class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    private Map<String, String> reservationMap = new HashMap<>();

    public void registerBooking(String reservationId, String roomType) {
        reservationMap.put(reservationId, roomType);
    }

    public void cancelBooking(String reservationId, RoomInventory inventory) {

        if (!reservationMap.containsKey(reservationId)) {
            System.out.println("Invalid reservation ID!");
            return;
        }

        String roomType = reservationMap.get(reservationId);
        rollbackStack.push(roomType);


        inventory.releaseRoom(roomType);


        reservationMap.remove(reservationId);

        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);
    }

    public void showLastRollback() {
        if (!rollbackStack.isEmpty()) {
            System.out.println("Latest rolled-back room type: " + rollbackStack.peek());
        } else {
            System.out.println("No rollback history available.");
        }
    }
}
public class BookMyStayApp {
    public static void main(String[] args) {
        System.out.println("Booking Cancellation");
        RoomInventory inventory = new RoomInventory();
        CancellationService service = new CancellationService();
        String reservationId = "RES101";
        String roomType = "Single";
        inventory.bookRoom(roomType);
        service.registerBooking(reservationId, roomType);
        service.cancelBooking(reservationId, inventory);
        service.showLastRollback();
        System.out.println("Updated " + roomType + " Room Availability: "
                + inventory.getAvailability(roomType));
    }
}