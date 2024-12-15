package project.library.service;

import project.library.dto.user.UserFilterResponse;
import project.library.enums.Operator;

import java.util.List;

public interface UserFilterStrategy {
    List<UserFilterResponse> getFilteredUser (Operator operator, String value);
}
