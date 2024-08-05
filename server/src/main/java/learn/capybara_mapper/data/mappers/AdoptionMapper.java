package learn.capybara_mapper.data.mappers;

import learn.capybara_mapper.models.Adoption;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AdoptionMapper  implements RowMapper<Adoption> {
    @Override
    public Adoption mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Adoption adoption = new Adoption();
        adoption.setAdoptionId(resultSet.getInt("adoption_id"));
        adoption.setPetId(resultSet.getInt("pet_id"));
        adoption.setUserId(resultSet.getInt("user_id"));
        adoption.setStreetAddress(resultSet.getString("street_address"));
        adoption.setCity(resultSet.getString("city"));
        adoption.setState(resultSet.getString("state"));
        adoption.setZipCode(resultSet.getString("zip_code"));
        adoption.setEmail(resultSet.getString("email"));
        adoption.setPhoneNumber(resultSet.getString("phone_number"));
        if (resultSet.getString("request_description") != null) {
            adoption.setRequestDescription(resultSet.getString("request_description"));
        }
        return adoption;
    }
}
