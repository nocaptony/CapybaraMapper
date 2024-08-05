package learn.capybara_mapper.domain;

import learn.capybara_mapper.data.UserRepository;
import learn.capybara_mapper.models.PetType;
import learn.capybara_mapper.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Result<User> findById(int userId) {
        Result<User> result = new Result<>();

        if (userId < 1) {
            result.addMessage( "Pet Id must be great than 0", ResultType.INVALID);
            return result;
        }

        User tempUser = repository.findById(userId);
        if (tempUser == null) {
            result.addMessage( "User not found", ResultType.NOT_FOUND);
            return result;
        }

        result.setPayload(tempUser);
        return result;
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}
