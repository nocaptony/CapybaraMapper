package learn.capybara_mapper.data;

import learn.capybara_mapper.data.mappers.PetMapper;
import learn.capybara_mapper.data.mappers.PetTypeMapper;
import learn.capybara_mapper.models.Location;
import learn.capybara_mapper.models.Pet;
import learn.capybara_mapper.models.PetType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetJdbcTemplateRepository implements PetRepository{

    private final JdbcTemplate jdbcTemplate;

    public PetJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Pet> findAll() {
        final String sql = "select pet_id, pet_type_id, location_id, name, description, photo_link from pet;";
        return jdbcTemplate.query(sql, new PetMapper());
    }

    @Override
    public List<Pet> findByLocation(Location location) {
        final String sql = "select pet_id, pet_type_id, location_id, name, description, photo_link from pet where location_id = ?;";
        return jdbcTemplate.query(sql, new PetMapper(), location.getLocationId());
    }

    @Override
    public List<Pet> findByType(PetType petType) {
        final String sql = "select pet_id, pet_type_id, location_id, name, description, photo_link from pet where pet_type_id = ?;";
        return jdbcTemplate.query(sql, new PetMapper(), petType.getPetTypeId());
    }
    @Override
    public Pet findById(int petId) {
        final String sql = "select pet_id, pet_type_id, location_id, name, description, photo_link from pet where pet_id = ?;";
        var pet = jdbcTemplate.queryForObject(sql, new PetMapper(), petId);
        if (pet != null) {
            addPetType(pet);
        }
        return pet;
    }


    private void addPetType(Pet pet) {
        final String sql = "select pet_type_id, name from pet_type where pet_type_id = ?;";
        var petType = jdbcTemplate.queryForObject(sql, new PetTypeMapper(), pet.getPetTypeId());

        pet.setPetTypeObj(petType);
    }



    }

