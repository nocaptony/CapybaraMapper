package learn.capybara_mapper.data;

import learn.capybara_mapper.data.mappers.PetTypeMapper;
import learn.capybara_mapper.models.PetType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PetTypeJdbcTemplateRepository implements PetTypeRepository {

    private final JdbcTemplate jdbcTemplate;

    public PetTypeJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<PetType> findAll() {
        final String sql = "select pet_type_id, name from pet_type;";
        return jdbcTemplate.query(sql, new PetTypeMapper());
    }

    @Override
    public PetType findById(int petTypeId) {
        final String sql = "select pet_type_id, name from pet_type where pet_type_id = ?;";
        return jdbcTemplate.queryForObject(sql, new PetTypeMapper(), petTypeId);
    }
}
