package andreasaderi.L5.controllers;

import andreasaderi.L5.entities.Trip;
import andreasaderi.L5.payloads.TripDTO;
import andreasaderi.L5.payloads.TripResponseDTO;
import andreasaderi.L5.services.TripService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public TripResponseDTO save(@RequestBody TripDTO body) {
        Trip saved = tripService.save(body);
        return new TripResponseDTO(saved.getTripId());
    }
}
