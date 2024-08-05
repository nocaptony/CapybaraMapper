package learn.capybara_mapper.domain;

import learn.capybara_mapper.models.Pet;
import learn.capybara_mapper.models.PetType;
import learn.capybara_mapper.data.PetTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetTypeService {
    private final PetTypeRepository repository;

    public PetTypeService(PetTypeRepository repository) {
        this.repository = repository;
    }

    public Result<PetType> findById(int petTypeId) {
        Result<PetType> result = new Result<>();

        if (petTypeId < 1) {
            result.addMessage( "Pet Id must be great than 0", ResultType.INVALID);
            return result;
        }

        PetType tempPetType = repository.findById(petTypeId);
        if (tempPetType == null) {
            result.addMessage( "Pet Type not found", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(tempPetType);
        return result;
    }

    public List<PetType> findAll() {
        return repository.findAll();
    }
}
