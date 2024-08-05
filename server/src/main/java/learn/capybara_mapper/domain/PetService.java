package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.PetRepository;
import learn.capybara_mapper.data.PetTypeRepository;
import learn.capybara_mapper.models.Location;
import learn.capybara_mapper.models.Pet;
import learn.capybara_mapper.models.PetType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetService {

    private final PetRepository repository;

    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> findAll(){
        return repository.findAll();
    }

    public List<Pet> findByLocation(Location location) {

        if (location == null || location.getLocationId() == 0) {
            return new ArrayList<>();
        }
        return repository.findByLocation(location);
    }

    public List<Pet> findByType(PetType type){
        return repository.findByType(type);
    }

    public Result<Pet> findById(int petId) {
        Result<Pet> result = new Result<>();

        if (petId < 1) {
            result.addMessage( "Pet Id must be great than 0", ResultType.INVALID);
            return result;
        }

        Pet tempPet = repository.findById(petId);
        if (tempPet == null) {
            result.addMessage( "Pet not found", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(tempPet);
        return result;
    }
}
