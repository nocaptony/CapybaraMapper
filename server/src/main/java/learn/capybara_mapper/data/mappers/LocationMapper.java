package learn.capybara_mapper.data.mappers;

import learn.capybara_mapper.models.Location;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LocationMapper implements RowMapper<Location> {
    @Override
    public Location mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Location location = new Location();

        location.setLocationId(resultSet.getInt("location_id"));
        location.setUserId(resultSet.getInt("user_id"));

        String point = resultSet.getString("location");
        if (point != null) {
            point = point.replace("POINT(", "").replace(")", "");
            String[] parts = point.split(" ");
            double latitude = Double.parseDouble(parts[0]);
            double longitude = Double.parseDouble(parts[1]);

            location.setLatitude(latitude);
            location.setLongitude(longitude);
        }

        return location;
    }
}
