package project.library.service.impl.user;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.library.dto.user.UserCreationRequest;
import project.library.dto.user.UserCreationResponse;
import project.library.dto.user.UserFilterResponse;
import project.library.enums.Operator;
import project.library.enums.UserFilter;
import project.library.model.User;
import project.library.enums.UserType;
import project.library.repository.UserRepository;
import project.library.service.UserFilterStrategy;

import java.util.List;

@Service
public class UserService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserFilterFactory userFilterFactory;

    public UserCreationResponse addStudent(UserCreationRequest req)
    {
        User user = req.toUser();
        userRepository.save(user);

        return user.toUserCreationResponse();
    }

    public List<UserFilterResponse>filterBy(UserFilter filterBy, Operator operator, String value)
    {
        UserFilterStrategy strategy = userFilterFactory.getStrategy(filterBy);
        return strategy.getFilteredUser(operator, value);
    }

    public User isValid(String userEmail)
    {
      List<User> users = userRepository.findByEmail(userEmail);
      if(users.isEmpty()) {
          return null;
      }
      return users.get(0);
    }
}
