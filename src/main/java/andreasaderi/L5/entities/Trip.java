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
@Table(name = "trips")
@Getter
@Setter
public class Trip {
    @Id
    @GeneratedValue
    @Column(name = "trip_id")
    @Setter(AccessLevel.NONE)
    private UUID tripId;
    @Column(nullable = false)
    private String destination;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String state;

    public Trip(String destination, LocalDate date, String state) {
        this.destination = destination;
        this.date = date;
        this.state = state;
    }


}
