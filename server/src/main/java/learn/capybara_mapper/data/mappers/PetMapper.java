package learn.capybara_mapper.data.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import learn.capybara_mapper.models.Pet;
import org.springframework.jdbc.core.RowMapper;

public class PetMapper implements RowMapper<Pet> {

    @Override
    public Pet mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Pet pet = new Pet();

        pet.setPetId(resultSet.getInt("pet_id"));
        pet.setPetTypeId(resultSet.getInt("pet_type_id"));
        pet.setLocationId(resultSet.getInt("location_id"));
        pet.setDescription(resultSet.getString("description"));
        pet.setName(resultSet.getString("name"));
        pet.setPhotoLink(resultSet.getString("photo_link"));

        return pet;
    }
}