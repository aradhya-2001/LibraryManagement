package project.library.service.impl.user;

import org.springframework.stereotype.Component;
import project.library.enums.UserFilter;
import project.library.service.UserFilterStrategy;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserFilterFactory
{
    private final Map<UserFilter, UserFilterStrategy> strategyMap= new HashMap<>();

    public UserFilterFactory(UserNameFilterImpl userNameFilter, UserEmailFilterImpl userEmailFilter, UserPhoneFilterImpl userPhoneFilter) // constructor dependency injection
    {
        strategyMap.put(UserFilter.NAME, userNameFilter);
        strategyMap.put(UserFilter.EMAIL, userEmailFilter);
        strategyMap.put(UserFilter.PHONE_NUM, userPhoneFilter);
    }

    public UserFilterStrategy getStrategy (UserFilter filter) {
        return strategyMap.get(filter);
    }
}
