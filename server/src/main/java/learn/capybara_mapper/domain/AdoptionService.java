package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.AdoptionRepository;
import learn.capybara_mapper.data.LocationRepository;
import learn.capybara_mapper.models.Adoption;
import learn.capybara_mapper.models.Location;
import learn.capybara_mapper.models.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AdoptionService {
    private final AdoptionRepository repository;

    public AdoptionService(AdoptionRepository repository) {
        this.repository = repository;
    }

    public List<Adoption> findAll() {
        return repository.findAll();
    }
    public List<Adoption> findByUser(User user) {
        if (user == null || user.getUserId() == 0) {
            return new ArrayList<>();
        }
        return repository.findByUser(user);
    }
    public Result<Adoption> findById(int adoptionId) {
        Result<Adoption> result = new Result<>();

        if (adoptionId < 1) {
            result.addMessage( "Adoption Id must be great than 0", ResultType.INVALID);
            return result;
        }

        Adoption tempAdoption = repository.findById(adoptionId);
        if (tempAdoption == null) {
            result.addMessage( "Adoption not found", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(tempAdoption);
        return result;
    }

    public Result<Adoption> add(Adoption adoption) {
        Result<Adoption> result = validate(adoption);
        if (result.isSuccess()) {
            if (adoption.getAdoptionId() != 0) {
                result.addMessage("Adoption ID cannot be set for `add` operation", ResultType.INVALID);
            } else {
                adoption = repository.add(adoption);
                result.setPayload(adoption);
            }
        }
        return result;

    }

    public Result<Adoption> update(Adoption adoption) {
        Result<Adoption> result = validate(adoption);
        if (result.isSuccess()) {
            if (adoption.getAdoptionId() <= 0) {
                result.addMessage("Adoption ID must be set for `update` operation", ResultType.INVALID);
            }
            else if (repository.update(adoption) == null) {
                result.addMessage(String.format("Adoption ID: %s, not found", adoption.getAdoptionId()), ResultType.NOT_FOUND);
            }
        }

        return result;
    }

    public boolean deleteById(int adoptionId) {
        return repository.deleteById(adoptionId);
    }

    private Result<Adoption> validate(Adoption adoption) {
        Result<Adoption> result = new Result<>();
        if (adoption == null) {
            result.addMessage("Adoption cannot be null", ResultType.INVALID);
        } else if (adoption.getPetId() == 0) {
            result.addMessage("Pet is required", ResultType.INVALID);
        } else if (adoption.getUserId() == 0) {
            result.addMessage("User is required", ResultType.INVALID);
        } else if (Validations.isNullOrBlank(adoption.getStreetAddress())) {
            result.addMessage("Street Address is required", ResultType.INVALID);
        } else if (Validations.isNullOrBlank(adoption.getCity())) {
            result.addMessage("City is required", ResultType.INVALID);
        } else if (Validations.isNullOrBlank(adoption.getState())) {
            result.addMessage("State is required", ResultType.INVALID);
        } else if (Validations.isNullOrBlank(adoption.getZipCode())) {
            result.addMessage("Zip Code is required", ResultType.INVALID);
        } else if (Validations.isNullOrBlank(adoption.getEmail())) {
            result.addMessage("Email is required", ResultType.INVALID);
        } else if (Validations.isNullOrBlank(adoption.getPhoneNumber())) {
            result.addMessage("Phone Number is required", ResultType.INVALID);
        } else if (adoption.getRequestDescription() == null) {
            result.addMessage("Request Description cannot be null", ResultType.INVALID);
        } else {
            // check if adoption already exists between this user and this pet
            for (Adoption tempAdoption : findAll()) {
                if (tempAdoption.getUserId() == adoption.getUserId()
                        && tempAdoption.getPetId() == adoption.getPetId()
                        && tempAdoption.getAdoptionId() != adoption.getAdoptionId()) {
                    result.addMessage("Adoption already exists", ResultType.INVALID);
                    return result;
                }
            }
        }

        return result;
    }
}
