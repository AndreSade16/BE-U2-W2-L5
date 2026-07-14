package andreasaderi.L5.repositories;

import andreasaderi.L5.entities.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<Booking, UUID> {
    boolean existsByTripTripIdAndEmployeeEmployeeId(UUID tripId, UUID employeeId);

    boolean existsByEmployeeEmployeeIdAndTripDate(UUID employeeId, LocalDate date);

    Page<Booking> findByEmployeeEmployeeId(UUID employeeId, Pageable pageable);
}
