package learn.capybara_mapper.data;

import learn.capybara_mapper.data.mappers.AdoptionMapper;
import learn.capybara_mapper.data.mappers.PetMapper;
import learn.capybara_mapper.data.mappers.PetTypeMapper;
import learn.capybara_mapper.data.mappers.UserMapper;
import learn.capybara_mapper.models.Adoption;
import learn.capybara_mapper.models.Pet;
import learn.capybara_mapper.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class AdoptionJdbcTemplateRepository implements AdoptionRepository{

    private final JdbcTemplate jdbcTemplate;

    public AdoptionJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Adoption> findAll() {
        final String sql = "select adoption_id, pet_id, user_id, street_address, city, state, " +
                "zip_code, email, phone_number, request_description from adoption limit 1000;";



        var adoptions = jdbcTemplate.query(sql, new AdoptionMapper());

        for (Adoption adoption : adoptions) {
            addPet(adoption);
            addUser(adoption);
        }
        return adoptions;
    }

    @Override
    public List<Adoption> findByUser(User user) {
        final String sql = "select adoption_id, pet_id, user_id, street_address, city, state, zip_code, email, phone_number, request_description " +
                "from adoption where user_id = ?;";
        var adoptions = jdbcTemplate.query(sql, new AdoptionMapper(), user.getUserId());

        for (Adoption adoption : adoptions) {
            addPet(adoption);
            addUser(adoption);
        }
        return adoptions;
    }

    @Override
    public Adoption findById(int id) {
        final String sql = "select adoption_id, pet_id, user_id, street_address, city, state, zip_code, email, phone_number, request_description " +
                "from adoption where adoption_id = ?;";

        Adoption adoption = jdbcTemplate.query(sql, new AdoptionMapper(), id).stream()
                .findAny().orElse(null);

        if (adoption != null) {
            addPet(adoption);
            addUser(adoption);
        }
        return adoption;
    }

    @Override
    public Adoption add(Adoption adoption) {
        final String sql = "insert into adoption (pet_id, user_id, street_address, city, state, zip_code, email, phone_number, request_description) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, adoption.getPetId());
            ps.setInt(2, adoption.getUserId());
            ps.setString(3, adoption.getStreetAddress());
            ps.setString(4, adoption.getCity());
            ps.setString(5, adoption.getState());
            ps.setString(6, adoption.getZipCode());
            ps.setString(7, adoption.getEmail());
            ps.setString(8, adoption.getPhoneNumber());
            ps.setString(9, adoption.getRequestDescription());
            return ps;
        }, keyHolder);

        adoption.setAdoptionId(keyHolder.getKey().intValue());
        return adoption;
    }

    @Override
    public Adoption update(Adoption adoption) {
        final String sql = "update adoption set " +
                "pet_id = ?, " +
                "user_id = ?, " +
                "street_address = ?, " +
                "city = ?, " +
                "state = ?, " +
                "zip_code = ?, " +
                "email = ?, " +
                "phone_number = ?, " +
                "request_description = ? " +
                "where adoption_id = ?;";
        if( jdbcTemplate.update(sql, adoption.getPetId(), adoption.getUserId(), adoption.getStreetAddress(),
                adoption.getCity(), adoption.getState(), adoption.getZipCode(),
                adoption.getEmail(), adoption.getPhoneNumber(), adoption.getRequestDescription(),
                adoption.getAdoptionId()) > 0) {
            return adoption;
        }
        return null;
    }

    @Override
    public boolean deleteById(int adoptionId) {
        final String sql = "delete from adoption where adoption_id = ?;";
        return jdbcTemplate.update(sql, adoptionId) > 0;
    }

    private void addUser(Adoption adoption) {
        final String sql = "select user_id, date_of_birth, name, email, phone_number from user where user_id = ?;";
        var user = jdbcTemplate.queryForObject(sql, new UserMapper(), adoption.getPetId());
        adoption.setUser(user);
    }

    private void addPet(Adoption adoption) {
        final String sql = "select pet_id, pet_type_id, location_id, name, description, photo_link from pet where pet_id = ?;";
        var pet = jdbcTemplate.queryForObject(sql, new PetMapper(), adoption.getPetId());
        if (pet != null) {
            addPetType(pet);
        }

        adoption.setPet(pet);
    }

    private void addPetType(Pet pet) {
        final String sql = "select pet_type_id, name from pet_type where pet_type_id = ?;";
        var petType = jdbcTemplate.queryForObject(sql, new PetTypeMapper(), pet.getPetTypeId());

        pet.setPetTypeObj(petType);
    }
}
