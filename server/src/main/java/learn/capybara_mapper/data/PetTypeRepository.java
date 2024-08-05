package learn.capybara_mapper.data;

import learn.capybara_mapper.models.PetType;

import java.util.List;

public interface PetTypeRepository {
    List<PetType> findAll();
    PetType findById(int petTypeId);
}
