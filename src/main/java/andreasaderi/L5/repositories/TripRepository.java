package andreasaderi.L5.repositories;

import andreasaderi.L5.entities.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {
    boolean existsByDestinationIgnoreCaseAndDate(String destination, LocalDate date);
}
