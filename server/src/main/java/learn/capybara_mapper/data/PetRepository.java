package learn.capybara_mapper.data;

import learn.capybara_mapper.models.Location;
import learn.capybara_mapper.models.Pet;
import learn.capybara_mapper.models.PetType;

import java.util.List;

public interface PetRepository {

    List<Pet> findAll();
    List<Pet> findByLocation(Location location);
    List<Pet> findByType(PetType petType);
    Pet findById(int petId);
}
