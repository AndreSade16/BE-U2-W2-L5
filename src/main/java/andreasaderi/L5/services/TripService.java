package andreasaderi.L5.services;

import andreasaderi.L5.entities.Trip;
import andreasaderi.L5.exceptions.TripAlreadyExistsException;
import andreasaderi.L5.payloads.TripDTO;
import andreasaderi.L5.repositories.TripRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    private final TripRepository tripRepository;

    public TripService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    public Page<Trip> findAll(int page, int size) {
        if (size <= 0) size = 10;
        if (size > 30) size = 30;
        if (page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size);
        return tripRepository.findAll(pageable);
    }

    public Trip save(TripDTO body) {
        if (tripRepository.existsByDestinationIgnoreCaseAndDate(body.destination(), body.date()))
            throw new TripAlreadyExistsException(body.destination(), body.date());
        Trip newTrip = new Trip(body.destination(), body.date(), "In programma");
        return tripRepository.save(newTrip);
    }
}
