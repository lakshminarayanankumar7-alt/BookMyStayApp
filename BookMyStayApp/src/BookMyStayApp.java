import java.util.*;
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}
class RoomInventory {
    private Map<String, Integer> rooms;

    public RoomInventory() {
        rooms = new HashMap<>();
        rooms.put("Single", 2);
        rooms.put("Double", 2);
        rooms.put("Suite", 1);
    }

    public boolean isRoomAvailable(String roomType) {
        return rooms.containsKey(roomType) && rooms.get(roomType) > 0;
    }

    public void bookRoom(String roomType) throws InvalidBookingException {
        if (!rooms.containsKey(roomType)) {
            throw new InvalidBookingException("Invalid room type!");
        }

        int available = rooms.get(roomType);

        if (available <= 0) {
            throw new InvalidBookingException("No rooms available for " + roomType);
        }

        rooms.put(roomType, available - 1);
    }
}
class ReservationValidator {

    public void validate(String guestName, String roomType, RoomInventory inventory)
            throws InvalidBookingException {

        if (guestName == null || guestName.trim().isEmpty()) {
            throw new InvalidBookingException("Guest name cannot be empty!");
        }

        if (!inventory.isRoomAvailable(roomType)) {
            throw new InvalidBookingException("Invalid or unavailable room type!");
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Booking Validation");

        System.out.print("Enter guest name: ");
        String guestName = scanner.nextLine();

        System.out.print("Enter room type (Single/Double/Suite): ");
        String roomType = scanner.nextLine();

        RoomInventory inventory = new RoomInventory();
        ReservationValidator validator = new ReservationValidator();

        try {
            validator.validate(guestName, roomType, inventory);

            inventory.bookRoom(roomType);

            System.out.println("Booking successful!");

        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }

        scanner.close();
    }
}