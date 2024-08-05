package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.AdoptionRepository;
import learn.capybara_mapper.data.LocationRepository;
import learn.capybara_mapper.models.Adoption;
import learn.capybara_mapper.models.Location;
import learn.capybara_mapper.models.User;
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
class AdoptionServiceTest {
    @MockBean
    AdoptionRepository repository;

    @Autowired
    AdoptionService service;

    @Test
    void shouldFindAll() {
        List<Adoption> expected = List.of(
                new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1"),
                new Adoption(2, 2, 1, "5678 Road", "Princeton", "NJ", "16003", "1234email@gmail.com", "567-0385-4567", "description2"),
                new Adoption(3, 3, 2, "0987 Street", "Montreal", "QC", "H2X 3V2", "emailemail@gmail.com", "972-969-7643", "description3")
        );
        when(repository.findAll()).thenReturn(expected);
        List<Adoption> actual = service.findAll();
        assertEquals(expected, actual);
    }

    @Test
    void shouldFindByUser() {
        User user = new User(2, LocalDate.parse("2000-01-01"), "Sherman Animal Shelter", "info@shermananimals.org", "313-839-1283");
        List<Adoption> expected = List.of(
                new Adoption(2, 2, 2, "5678 Road", "Princeton", "NJ", "16003", "1234email@gmail.com", "567-0385-4567", "description2"),
                new Adoption(3, 3, 2, "0987 Street", "Montreal", "QC", "H2X 3V2", "emailemail@gmail.com", "972-969-7643", "description3")
        );
        when(repository.findByUser(user)).thenReturn(expected);
        List<Adoption> actual = service.findByUser(user);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotFindByUserWhenUserIsNull() {
        List<Adoption> expected = new ArrayList<>();
        List<Adoption> actual = service.findByUser(null);

        assertEquals(actual, expected);
    }

    @Test
    void shouldNotFindByUserWhenUserIdIs0() {
        List<Adoption> expected = new ArrayList<>();
        User user = new User(0, LocalDate.parse("2000-01-01"), "Sherman Animal Shelter", "info@shermananimals.org", "313-839-1283");
        List<Adoption> actual = service.findByUser(user);
        assertEquals(actual, expected);
    }

    @Test
    void shouldFindById() {
        Adoption expected = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        when(repository.findById(1)).thenReturn(expected);
        Result<Adoption> actual = service.findById(1);
        assertEquals(expected, actual.getPayload());
    }

    @Test
    void shouldNotFindByIdWhenIdIs0() {
        Result<Adoption> actual = service.findById(0);
        assertFalse(actual.isSuccess());

        assertEquals(ResultType.INVALID, actual.getType());
    }

    @Test
    void shouldNotFindByIdWhenAdoptionDoesNotExist() {
        Result<Adoption> actual = service.findById(1);
        when(repository.findById(1)).thenReturn(null);

        assertFalse(actual.isSuccess());
        assertEquals(ResultType.NOT_FOUND, actual.getType());
    }

    @Test
    void shouldAdd() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");

        Adoption expectedAdoption = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> expected = new Result<>();
        expected.setPayload(expectedAdoption);

        when(repository.findAll()).thenReturn(new ArrayList<>());
        Result<Adoption> actual = service.add(adoption);
        assertTrue(actual.isSuccess());

    }
    @Test
    void shouldNotAddIfAdoptionNull() {
        Result<Adoption> actual = service.add(null);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Adoption cannot be null", actual.getMessages().get(0));
    }
    @Test
    void shouldNotAddIfPetIdIs0() {
        Adoption adoption = new Adoption(0, 0, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Pet is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfUserIdIs0() {
        Adoption adoption = new Adoption(0, 1, 0, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("User is required", actual.getMessages().get(0));
    }
    @Test
    void shouldNotAddIfStreetAddressIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, null, "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Street Address is required", actual.getMessages().get(0));
    }
    @Test
    void shouldNotAddIfStreetAddressIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Street Address is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfCityIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", null, "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("City is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfCityIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("City is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfStateIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", null, "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("State is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfStateIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("State is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfZipCodeIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", null, "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Zip Code is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfZipCodeIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Zip Code is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfEmailIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", null, "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Email is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfEmailIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "", "973-969-2480", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Email is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfPhoneNumberIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", null, "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Phone Number is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfPhoneNumberIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "", "description1");
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Phone Number is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotAddIfRequestDescriptionIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", null);
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Request Description cannot be null", actual.getMessages().get(0));
    }
    @Test
    void shouldNotAddIfAdoptionAlreadyExists() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");

        Adoption expectedAdoption = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> expected = new Result<>();
        expected.setPayload(expectedAdoption);

        when(repository.findAll()).thenReturn(List.of(new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1")));
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals("Adoption already exists", actual.getMessages().get(0));
    }

    @Test
    void shouldNotAddIfAdoptionIdIsSet() {
        Adoption adoption = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");

        Adoption expectedAdoption = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> expected = new Result<>();
        expected.setPayload(expectedAdoption);

        when(repository.findAll()).thenReturn(new ArrayList<>());
        Result<Adoption> actual = service.add(adoption);
        assertFalse(actual.isSuccess());
        assertEquals("Adoption ID cannot be set for `add` operation", actual.getMessages().get(0));
    }

    @Test
    void shouldUpdate() {
        Adoption adoption = new Adoption(1, 1, 1, "1234 Street Road", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");

        Adoption oldAdoption = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> expected = new Result<>();
        expected.setPayload(oldAdoption);

        when(repository.findAll()).thenReturn(List.of(oldAdoption));
        when(repository.update(adoption)).thenReturn(adoption);
        Result<Adoption> actual = service.update(adoption);
        assertTrue(actual.isSuccess());

    }
    @Test
    void shouldNotUpdateIfAdoptionNull() {
        Result<Adoption> actual = service.update(null);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Adoption cannot be null", actual.getMessages().get(0));
    }
    @Test
    void shouldNotUpdateIfPetIdIs0() {
        Adoption adoption = new Adoption(0, 0, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Pet is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfUserIdIs0() {
        Adoption adoption = new Adoption(0, 1, 0, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("User is required", actual.getMessages().get(0));
    }
    @Test
    void shouldNotUpdateIfStreetAddressIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, null, "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Street Address is required", actual.getMessages().get(0));
    }
    @Test
    void shouldNotUpdateIfStreetAddressIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Street Address is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfCityIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", null, "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("City is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfCityIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("City is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfStateIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", null, "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("State is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfStateIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("State is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfZipCodeIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", null, "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Zip Code is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfZipCodeIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Zip Code is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfEmailIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", null, "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Email is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfEmailIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "", "973-969-2480", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Email is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfPhoneNumberIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", null, "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Phone Number is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfPhoneNumberIsMissing() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "", "description1");
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Phone Number is required", actual.getMessages().get(0));

    }
    @Test
    void shouldNotUpdateIfRequestDescriptionIsNull() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", null);
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals(ResultType.INVALID, actual.getType());
        assertEquals("Request Description cannot be null", actual.getMessages().get(0));
    }
    @Test
    void shouldNotUpdateIfAdoptionAlreadyExists() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");

        Adoption expectedAdoption = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        Result<Adoption> expected = new Result<>();
        expected.setPayload(expectedAdoption);

        when(repository.findAll()).thenReturn(List.of(new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1")));
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals("Adoption already exists", actual.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateIfAdoptionIdIsNotSet() {
        Adoption adoption = new Adoption(0, 1, 1, "1234 Street road", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");

        Adoption oldAdoption = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");
        //Result<Adoption> expected = new Result<>();
        //expected.setPayload(expectedAdoption);

        when(repository.findAll()).thenReturn(new ArrayList<>());
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals("Adoption ID must be set for `update` operation", actual.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateIfAdoptionIdNotFound() {
        Adoption adoption = new Adoption(1, 1, 1, "1234 Street", "New York", "NY", "10008", "tommie1984@gmail.com", "973-969-2480", "description1");

        when(repository.findAll()).thenReturn(new ArrayList<>());
        when(repository.update(adoption)).thenReturn(null);
        Result<Adoption> actual = service.update(adoption);
        assertFalse(actual.isSuccess());
        assertEquals("Adoption ID: 1, not found", actual.getMessages().get(0));
    }

    @Test
    void shouldDeleteById() {
        when(repository.deleteById(1)).thenReturn(true);
        boolean actual = service.deleteById(1);
        assertTrue(actual);

    }
    @Test
    void shouldNotDeleteById() {
        when(repository.deleteById(0)).thenReturn(false);
        boolean actual = service.deleteById(0);
        assertFalse(actual);
    }


}