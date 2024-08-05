package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.UserRepository;
import learn.capybara_mapper.models.User;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class UserServiceTest {
    @MockBean
    UserRepository repository;

    @Autowired
    UserService service;

    @Test
    void shouldFindAll() {
        List<User> expected = List.of(
                new User(1, LocalDate.parse("2000-01-01"), "Sherman Animal Shelter", "info@shermananimals.org", "313-839-1283"),
                new User(2, LocalDate.parse("2008-01-01"), "Mansfield County Animal Shelter", "info@mansfieldcountyAS.com", "804-512-2154"),
                new User(3, LocalDate.parse("1997-07-04"), "Ryann Jacobs", "tommie1984@gmail.com", "973-969-2480"),
                new User(4, LocalDate.parse("1971-06-11"), "Catherine R Chapman", "estefania_he@yahoo.com", "717-939-1241")
        );
        when(repository.findAll()).thenReturn(expected);
        List<User> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindById() {
        User expected = new User(1, LocalDate.parse("2000-01-01"), "Sherman Animal Shelter", "info@shermananimals.org", "313-839-1283");
        when(repository.findById(1)).thenReturn(expected);
        Result<User> actual = service.findById(1);
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotFindByIdWhenIdIs0() {
        Result<User> actual = service.findById(0);
        assertFalse(actual.isSuccess());

        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotFindByIdWhenUserDoesNotExist() {
        Result<User> actual = service.findById(1);
        when(repository.findById(1)).thenReturn(null);

        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }
}