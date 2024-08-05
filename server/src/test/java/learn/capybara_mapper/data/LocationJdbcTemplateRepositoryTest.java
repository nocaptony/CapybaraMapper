package learn.capybara_mapper.data;

import learn.capybara_mapper.models.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class LocationJdbcTemplateRepositoryTest {

    @Autowired
    LocationJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindAll() {
        List<Location> locations = repository.findAll();
        assertNotNull(locations);
        assertEquals(3, locations.size());
    }

    @Test
    void shouldFindById() {
        Location location = repository.findById(1);
        assertNotNull(location);
        assertEquals(39.887264, location.getLatitude());
        assertEquals(-74.741545, location.getLongitude());
    }

    @Test
    void shouldFindByProximity() {
        List<Location> locations = repository.findByProximity(39.887264, -74.741545, 2);
        assertNotNull(locations);
        assertEquals(2, locations.size());
        locations = repository.findByProximity(39.816760, -74.485988, 10);
        assertEquals(1, locations.size());
    }
}