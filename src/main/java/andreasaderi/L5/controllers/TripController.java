package andreasaderi.L5.controllers;

import andreasaderi.L5.services.TripService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController {

    private final TripService tripService;

    public TripController(TripService tripService) {
        this.tripService = tripService;
    }
}
