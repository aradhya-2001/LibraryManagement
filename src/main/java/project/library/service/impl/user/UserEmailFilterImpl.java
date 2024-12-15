package project.library.service.impl.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.library.dto.user.UserFilterResponse;
import project.library.enums.Operator;
import project.library.model.User;
import project.library.repository.UserRepository;
import project.library.service.UserFilterStrategy;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserEmailFilterImpl implements UserFilterStrategy
{
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserFilterResponse> getFilteredUser(Operator operator, String value)
    {
        List<User> users = new ArrayList<>();

        users = switch (operator) {
            case EQUALS -> userRepository.findByEmail(value);
            case LIKE -> userRepository.findByEmailContains(value);
            default -> users;
        };

        return User.toUserFilterResponse(users);
    }
}
