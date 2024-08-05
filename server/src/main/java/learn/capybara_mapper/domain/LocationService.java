package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.LocationRepository;
import learn.capybara_mapper.data.PetRepository;
import learn.capybara_mapper.models.Location;
import learn.capybara_mapper.models.Pet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationService {

    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public List<Location> findByProximity(double latitude, double longitude, double proximity) {
        List<Location> result = new ArrayList<>();
        // latitude must be between -90 and 90
        if (Math.abs(latitude) > 90) {
            return result;
        }
        // longitude must be between -180 and 180
        else if (Math.abs(longitude) > 180) {
            return result;
        }
        else if (proximity > 20000) {
            return result;
        }

        return repository.findByProximity(latitude, longitude, proximity);
    }
    public List<Location> findAll() {
        return repository.findAll();
    }

    public Result<Location> findById(int locationId) {
        Result<Location> result = new Result<>();

        if (locationId < 1) {
            result.addMessage( "Location Id must be great than 0", ResultType.INVALID);
            return result;
        }

        Location tempLocation = repository.findById(locationId);
        if (tempLocation == null) {
            result.addMessage( "Location not found", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(tempLocation);
        return result;
    }
}
