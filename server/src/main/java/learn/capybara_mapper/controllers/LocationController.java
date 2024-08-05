package learn.capybara_mapper.controllers;

import learn.capybara_mapper.domain.LocationService;
import learn.capybara_mapper.domain.Result;
import learn.capybara_mapper.models.Location;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/api/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    
    @GetMapping
    public List<Location> findAll() {
        return locationService.findAll();
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<Location> findById(@PathVariable int locationId) {
        Result<Location> result = locationService.findById(locationId);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(result.getPayload());
    }

    @GetMapping("/{latitude}/{longitude}/{radius}")
    public List<Location> findByProximity(@PathVariable double latitude, @PathVariable double longitude, @PathVariable double radius) {
        return locationService.findByProximity(latitude, longitude, radius);
    }
}
