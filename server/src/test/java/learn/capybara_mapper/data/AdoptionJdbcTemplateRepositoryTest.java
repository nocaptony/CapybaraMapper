package learn.capybara_mapper.data;

import learn.capybara_mapper.models.Adoption;
import learn.capybara_mapper.models.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AdoptionJdbcTemplateRepositoryTest {
    final static int NEXT_ADOPTION_ID = 6;


    @Autowired
    AdoptionJdbcTemplateRepository adoptionRepository;

    @Autowired
    UserJdbcTemplateRepository userRepository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }
    @Test
    void shouldFindAll() {
        List<Adoption> adoptions = adoptionRepository.findAll();
        assertNotNull(adoptions);
        assertTrue(adoptions.size() >= 4 && adoptions.size() <= 6);
    }

    @Test
    void shouldFindByUser() {
        User user = userRepository.findById(3);
        assertNotNull(user);
        List<Adoption> adoptions = adoptionRepository.findByUser(user);
        assertNotNull(adoptions);
        assertEquals(3, adoptions.size());
    }

    @Test
    void shouldFindById() {
        Adoption adoption = adoptionRepository.findById(5);
        assertNotNull(adoption);
        assertEquals(5, adoption.getAdoptionId());
        assertEquals(3, adoption.getPetId());
        assertEquals(4, adoption.getUserId());
    }

    @Test
    void shouldAdd() {
        Adoption adoption = makeAdoption();
        Adoption actual = adoptionRepository.add(adoption);
        assertNotNull(actual);
        assertEquals(NEXT_ADOPTION_ID, actual.getAdoptionId());
    }

    @Test
    void shouldUpdate() {
        Adoption adoption = makeAdoption();
        adoption.setAdoptionId(1);
        adoption.setRequestDescription("Updated Description");
        Adoption actual = adoptionRepository.update(adoption);
        assertNotNull(actual);
        assertEquals("Updated Description", actual.getRequestDescription());
    }

    @Test
    void shouldDeleteById() {
        assertTrue(adoptionRepository.deleteById(5));
        assertFalse(adoptionRepository.deleteById(5));
    }

    Adoption makeAdoption() {
        Adoption adoption = new Adoption();
        adoption.setPetId(3);
        adoption.setUserId(4);
        adoption.setStreetAddress("4");
        adoption.setCity("NYC");
        adoption.setState("NY");
        adoption.setZipCode("08971");
        adoption.setEmail("bijki@gmail.com");
        adoption.setPhoneNumber("609-609-6096");
        adoption.setRequestDescription("I would like to adopt a pet");
        return adoption;
    }
}