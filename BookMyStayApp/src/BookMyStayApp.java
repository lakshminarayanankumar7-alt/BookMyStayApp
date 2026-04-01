import java.util.*;
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

class BookingRequestQueue {
    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
        System.out.println("Booking request added for Guest: "
                + reservation.getGuestName()
                + ", Room Type: "
                + reservation.getRoomType());
    }
    public void viewRequests() {
        System.out.println("\nProcessing booking requests in order:");

        if (requestQueue.isEmpty()) {
            System.out.println("No booking requests available.");
            return;
        }

        for (Reservation r : requestQueue) {
            System.out.println("Guest: " + r.getGuestName()
                    + ", Room Type: " + r.getRoomType());
        }
    }
}

public class BookMyStayApp {
    public static void main(String[] args) {

        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        Reservation r1 = new Reservation("Ram", "Single");
        Reservation r2 = new Reservation("Sam", "Double");
        Reservation r3 = new Reservation("Gowtham", "Suite");
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);
        bookingQueue.viewRequests();
    }
}