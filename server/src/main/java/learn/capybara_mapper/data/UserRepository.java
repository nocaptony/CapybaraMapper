package learn.capybara_mapper.data;

import learn.capybara_mapper.models.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(int userId);
}
