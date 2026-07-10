package andreasaderi.L5.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Table(name = "bookings")
@Setter
@Getter
public class Booking {

    @Id
    @GeneratedValue
    @Setter(AccessLevel.NONE)
    @Column(name = "booking_id")
    private UUID bookingId;

    @ManyToOne
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "request_date", nullable = false)
    private LocalDate requestDate;

    private String notes;

    public Booking(Trip trip, Employee employee, String notes) {
        this.trip = trip;
        this.employee = employee;
        this.notes = notes;
        this.requestDate = LocalDate.now();
    }
}
