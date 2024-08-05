package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.PetRepository;
import learn.capybara_mapper.models.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PetServiceTest {
    @MockBean
    PetRepository repository;

    @Autowired
    PetService service;

    @Test
    void shouldFindAll() {
        List<Pet> expected = List.of(
                new Pet(1, 1, 1, "Reginald", "description1", "Link3"),
                new Pet(2, 1, 1, "Turkey", "description2", "Link2"),
                new Pet(3, 1, 2, "Oakly", "description3", "Link1")
        );
        when(repository.findAll()).thenReturn(expected);
        List<Pet> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByLocation() {
        List<Pet> expected = List.of(new Pet(1, 1, 1, "Reginald", "description1", "Link3"));
        Location location = new Location(1, 1, 10,10);
        when(repository.findByLocation(location)).thenReturn(expected);
        List<Pet> actual = service.findByLocation(location);
        assertEquals(actual, expected);
    }

    @Test
    void shouldNotFindByUserWhenLocationIsNull() {
        List<Pet> expected = new ArrayList<>();
        List<Pet> actual = service.findByLocation(null);

        assertEquals(actual, expected);
    }

    @Test
    void shouldNotFindByLocationWhenUserIdIs0() {
        List<Pet> expected = new ArrayList<>();
        Location location = new Location(0, 1, 10,10);
        List<Pet> actual = service.findByLocation(location);
        assertEquals(actual, expected);
    }

    @Test
    void shouldFindByType() {
        PetType petType = new PetType(1, "Capybara");
        List<Pet> expected = List.of(
                new Pet(1, 1, 1, "Reginald", "description1", "Link3"),
                new Pet(2, 1, 1, "Turkey", "description2", "Link2"),
                new Pet(3, 1, 2, "Oakly", "description3", "Link1")
        );
        when(repository.findByType(petType)).thenReturn(expected);
        List<Pet> actual = service.findByType(petType);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByType() {
        PetType petType = new PetType(10, "FakeType");
        List<Pet> expected = new ArrayList<Pet>();
        when(repository.findByType(petType)).thenReturn(expected);
        List<Pet> actual = service.findByType(petType);
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        Pet expected = new Pet(1, 1, 2, "Oakly", "description3", "Link1");
        when(repository.findById(1)).thenReturn(expected);
        Result<Pet> actual = service.findById(1);
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotFindByIdWhenIdIs0() {
        Result<Pet> actual = service.findById(0);
        assertFalse(actual.isSuccess());

        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotFindByIdWhenPetTypeDoesNotExist() {
        Result<Pet> actual = service.findById(1);
        when(repository.findById(1)).thenReturn(null);

        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}