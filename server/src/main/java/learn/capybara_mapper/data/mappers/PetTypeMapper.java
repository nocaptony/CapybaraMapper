package learn.capybara_mapper.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import learn.capybara_mapper.models.PetType;
import org.springframework.jdbc.core.RowMapper;

public class PetTypeMapper implements RowMapper<PetType> {

    @Override
    public PetType mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PetType petType = new PetType();
        petType.setPetTypeId(resultSet.getInt("pet_type_id"));
        petType.setName(resultSet.getString("name"));
        return petType;
    }
}