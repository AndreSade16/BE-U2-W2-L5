package andreasaderi.L5.controllers;

import andreasaderi.L5.entities.Booking;
import andreasaderi.L5.entities.Employee;
import andreasaderi.L5.exceptions.ValidationException;
import andreasaderi.L5.payloads.BookingDTO;
import andreasaderi.L5.payloads.BookingResponseDTO;
import andreasaderi.L5.payloads.BookingSearchDTO;
import andreasaderi.L5.services.BookingService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Page<Booking> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return bookingService.findAll(page, size);
    }

    @GetMapping("/me")
    public Page<Booking> findOwnBookings(@AuthenticationPrincipal Employee employee, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return bookingService.findByEmployeeId(employee.getEmployeeId(), page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponseDTO save(@AuthenticationPrincipal Employee employee, @RequestBody @Validated BookingDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        Booking saved = bookingService.save(employee, body);
        return new BookingResponseDTO(saved.getBookingId());
    }

    @GetMapping("/{bookingId}")
    public Booking findById(@PathVariable UUID bookingId) {
        return bookingService.findById(bookingId);
    }

    @PutMapping("/{bookingId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public Booking findByIdAndUpdate(@PathVariable UUID bookingId, @RequestBody @Validated BookingSearchDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        return bookingService.findByIdAndUpdate(bookingId, body);
    }

    @DeleteMapping("/{bookingId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public void findByIdAndDelete(@PathVariable UUID bookingId) {
        bookingService.findByIdAndDelete(bookingId);
    }

}
