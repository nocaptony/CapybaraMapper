package learn.capybara_mapper.data;

import learn.capybara_mapper.models.Adoption;
import learn.capybara_mapper.models.Location;
import learn.capybara_mapper.models.Pet;
import learn.capybara_mapper.models.PetType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PetJdbcTemplateRepositoryTest {

    @Autowired
    PetJdbcTemplateRepository petRepository;

    @Autowired
    PetTypeJdbcTemplateRepository typeRepository;

    @Autowired
    LocationJdbcTemplateRepository locationRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }
    @Test
    void shouldFindAll() {
        List<Pet> pets = petRepository.findAll();
        assertNotNull(pets);
        assertEquals(3, pets.size());
    }

    @Test
    void shouldFindById() {
        Pet pet = petRepository.findById(3);
        assertNotNull(pet);
        assertEquals(3, pet.getPetId());
        assertEquals("Oakly", pet.getName());
    }


    @Test
    void shouldFindByLocation() {
        Location location = new Location(1, 1, 39.887264, -74.741545);
        List<Pet> pets = petRepository.findByLocation(location);
        assertNotNull(pets);
        assertEquals(1, pets.size());
    }

    @Test
    void shouldFindByType() {
        PetType petType = typeRepository.findById(1);
        List<Pet> pets = petRepository.findByType(petType);
        assertNotNull(pets);
        assertEquals(2, pets.size());
    }
}