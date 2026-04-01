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
class BookingHistory {
    private List<Reservation> confirmedReservations;
    public BookingHistory() {
        confirmedReservations = new ArrayList<>();
    }
    public void addReservation(Reservation reservation) {
        confirmedReservations.add(reservation);
    }
    public List<Reservation> getAllReservations() {
        return confirmedReservations;
    }
}
class BookingReportService {
    public void generateReport(BookingHistory history) {

        List<Reservation> reservations = history.getAllReservations();

        System.out.println("Booking History Report");

        for (Reservation r : reservations) {
            System.out.println("Guest: " + r.getGuestName() +
                    ", Room Type: " + r.getRoomType());
        }
    }
}
public class BookMyStayApp {

    public static void main(String[] args) {
        BookingHistory history = new BookingHistory();
        history.addReservation(new Reservation("John Doe", "Single"));
        history.addReservation(new Reservation("Jane Smith", "Double"));
        history.addReservation(new Reservation("Robert Brown", "Suite"));
        BookingReportService reportService = new BookingReportService();
        reportService.generateReport(history);
    }
}