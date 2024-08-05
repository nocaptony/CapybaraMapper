package learn.capybara_mapper.data;

import learn.capybara_mapper.models.Location;
import java.util.List;



public interface LocationRepository {

    List<Location> findByProximity(double latitude, double longitude, double proximity);
    List<Location> findAll();
    Location findById(int locationId);
}
