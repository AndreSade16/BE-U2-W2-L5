package andreasaderi.L5.controllers;

import andreasaderi.L5.entities.Trip;
import andreasaderi.L5.exceptions.ValidationException;
import andreasaderi.L5.payloads.TripDTO;
import andreasaderi.L5.payloads.TripResponseDTO;
import andreasaderi.L5.payloads.TripUpdateDTO;
import andreasaderi.L5.services.TripService;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/trips")
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @GetMapping
    public Page<Trip> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return tripService.findAll(page, size);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TripResponseDTO save(@RequestBody @Validated TripDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors()) {
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        }
        Trip saved = tripService.save(body);
        return new TripResponseDTO(saved.getTripId());
    }

    @GetMapping("/{tripId}")
    public Trip findById(@PathVariable UUID tripId) {
        return tripService.findById(tripId);
    }

    @PutMapping("/{tripId}")
    public Trip findByIdAndUpdate(@PathVariable UUID tripId, @RequestBody @Validated TripUpdateDTO body, BindingResult validationResult) {
        if (validationResult.hasErrors())
            throw new ValidationException(validationResult.getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
        return tripService.findByIdAndUpdate(tripId, body);
    }

    @DeleteMapping("/{tripId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable UUID tripId) {
        tripService.findByIdAndDelete(tripId);
    }
}
