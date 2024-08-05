package learn.capybara_mapper.data;

import learn.capybara_mapper.models.Adoption;
import learn.capybara_mapper.models.User;

import java.util.List;

public interface AdoptionRepository {
    List<Adoption> findAll();
    List<Adoption> findByUser(User user);
    Adoption findById(int id);
    Adoption add(Adoption adoption);
    Adoption update(Adoption adoption);
    boolean deleteById(int adoptionId);
}
