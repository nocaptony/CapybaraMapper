package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.PetTypeRepository;
import learn.capybara_mapper.data.UserRepository;
import learn.capybara_mapper.models.PetType;
import learn.capybara_mapper.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PetTypeServiceTest {
    @MockBean
    PetTypeRepository repository;

    @Autowired
    PetTypeService service;

    @Test
    void shouldFindAll() {
        List<PetType> expected = List.of(
                new PetType(1, "Capybara"),
                new PetType(2, "Dog"),
                new PetType(3, "Cat"),
                new PetType(4, "Zebra")
        );
        when(repository.findAll()).thenReturn(expected);
        List<PetType> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        PetType expected = new PetType(1, "Capybara");
        when(repository.findById(1)).thenReturn(expected);
        Result<PetType> actual = service.findById(1);
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotFindByIdWhenIdIs0() {
        Result<PetType> actual = service.findById(0);
        assertFalse(actual.isSuccess());

        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotFindByIdWhenPetTypeDoesNotExist() {
        Result<PetType> actual = service.findById(1);
        when(repository.findById(1)).thenReturn(null);

        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }



}