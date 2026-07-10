package andreasaderi.L5.services;

import andreasaderi.L5.entities.Booking;
import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.entities.Trip;
import andreasaderi.L5.exceptions.EmployeeAlreadyHasBookingForThisDateException;
import andreasaderi.L5.payloads.BookingDTO;
import andreasaderi.L5.repositories.BookingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final TripService tripService;
    private final EmployeeService employeeService;

    public BookingService(BookingRepository bookingRepository, TripService tripService, EmployeeService employeeService) {
        this.bookingRepository = bookingRepository;
        this.tripService = tripService;
        this.employeeService = employeeService;
    }

    public Page<Booking> findAll(int page, int size) {
        if (size <= 0) size = 10;
        if (size > 30) size = 30;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size);
        return bookingRepository.findAll(pageable);
    }

    public Booking save(BookingDTO body) {
        Trip trip = tripService.findById(body.tripId());
        Employee employee = employeeService.findById(body.employeeId());
        if (bookingRepository.existsByEmployeeEmployeeIdAndTripDate(body.employeeId(), trip.getDate())) {
            throw new EmployeeAlreadyHasBookingForThisDateException(body.employeeId(), trip.getDate());
        }

        return bookingRepository.save(new Booking(trip, employee, body.notes()));

    }
}
