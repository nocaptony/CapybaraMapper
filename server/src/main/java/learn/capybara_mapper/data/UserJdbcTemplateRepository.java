package learn.capybara_mapper.data;

import learn.capybara_mapper.data.mappers.UserMapper;
import learn.capybara_mapper.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserJdbcTemplateRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }



    @Override
    public List<User> findAll() {
        final String sql = "select user_id, date_of_birth, name, email, phone_number from user;";
        return jdbcTemplate.query(sql, new UserMapper());
    }

    @Override
    public User findById(int userId) {
        final String sql = "select user_id, date_of_birth, name, email, phone_number from user where user_id = ?;";
        return jdbcTemplate.queryForObject(sql, new UserMapper(), userId);
    }
}
