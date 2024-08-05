package learn.capybara_mapper.data;

import learn.capybara_mapper.models.PetType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PetTypeJdbcTemplateRepositoryTest {

    @Autowired
    PetTypeJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }
    @Test
    void shouldFindAll() {
        List<PetType> petTypes = repository.findAll();
        assertNotNull(petTypes);
        assertEquals(10, petTypes.size());
    }

    @Test
    void shouldFindById() {
        PetType petType = repository.findById(3);
        assertNotNull(petType);
        assertEquals(3, petType.getPetTypeId());
        assertEquals("Cat", petType.getName());
    }
}