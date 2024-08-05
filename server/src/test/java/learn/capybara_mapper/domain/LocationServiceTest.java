package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.LocationRepository;
import learn.capybara_mapper.data.PetRepository;
import learn.capybara_mapper.models.Location;
import learn.capybara_mapper.models.Pet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LocationServiceTest {
    @MockBean
    LocationRepository repository;

    @Autowired
    LocationService service;

    @Test
    void shouldFindAll() {
        List<Location> expected = List.of(
                new Location(1, 1, 40.730610, -73.935242),
                new Location(2, 2, 40.730412, -73.935231)
        );
        when(repository.findAll()).thenReturn(expected);
        List<Location> actual = service.findAll();
        assertEquals(expected, actual);
    }


    @Test
    void shouldFindByProximity() {
        List<Location> expected = List.of(
                new Location(1, 1, 40.730610, -73.935242),
                new Location(2, 2, 40.730412, -73.935231)
        );
        when(repository.findByProximity(10,10,10)).thenReturn(expected);
        List<Location> actual = service.findByProximity(10,10,10);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByProximityInvalidLatitude() {
        // latitude must be between -90 and 90

        List<Location> actual = service.findByProximity(100,10,10);
        assertEquals(new ArrayList<Location>(), actual);
    }

    @Test
    void shouldNotFindByProximityInvalidLongitude() {
        // longitude must be between -180 and 180
        List<Location> actual = service.findByProximity(10,1000,10);
        assertEquals(new ArrayList<Location>(), actual);
    }

    @Test
    void shouldNotFindByProximityInvalidProximity() {
        // proximity cannot be greater than 20000
        List<Location> actual = service.findByProximity(10,10,200000);
        assertEquals(new ArrayList<Location>(), actual);
    }

    @Test
    void shouldFindById() {
        Location expected = new Location(1, 1, 40.730610, -73.935242);
        when(repository.findById(1)).thenReturn(expected);
        Result<Location> actual = service.findById(1);
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotFindByIdWhenIdIs0() {
        Result<Location> actual = service.findById(0);
        assertFalse(actual.isSuccess());

        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotFindByIdWhenLocationDoesNotExist() {
        Result<Location> actual = service.findById(1);
        when(repository.findById(1)).thenReturn(null);

        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}