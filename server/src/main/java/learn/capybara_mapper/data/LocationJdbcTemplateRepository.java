package learn.capybara_mapper.data;

import learn.capybara_mapper.data.mappers.LocationMapper;
import learn.capybara_mapper.models.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocationJdbcTemplateRepository implements LocationRepository {

    private final JdbcTemplate jdbcTemplate;

    public LocationJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Location> findAll() {
        final String sql = "SELECT location_id, user_id, ST_AsText(location) AS location FROM location;";
        return jdbcTemplate.query(sql, new LocationMapper());
    }


    @Override
    public Location findById(int locationId) {
        final String sql = "select location_id, user_id, ST_AsText(location) as location from location where location_id = ?;";
        return jdbcTemplate.queryForObject(sql, new LocationMapper(), locationId);
    }

    public List<Location> findByProximity(double latitude, double longitude, double proximityInMiles) {
        double proximityInMeters = convertMilesToMeters(proximityInMiles);

        // Convert the proximity to meters if it's not already (assuming input is in meters)
        final String sql = "SELECT location_id, user_id, ST_AsText(location) as location " +
                "FROM location " +
                "WHERE ST_Distance_Sphere(location, point(?, ?)) <= ?;";

        return jdbcTemplate.query(sql, new LocationMapper(), latitude, longitude, proximityInMeters);
    }

    private double convertMilesToMeters(double input) {
        return input * 1609.34;
    }
}
