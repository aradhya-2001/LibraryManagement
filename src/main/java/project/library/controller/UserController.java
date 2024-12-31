package project.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.library.dto.user.UserCreationRequest;
import project.library.dto.user.UserCreationResponse;
import project.library.dto.user.UserFilterResponse;
import project.library.enums.Operator;
import project.library.enums.UserFilter;
import project.library.service.impl.user.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{

    @Autowired
    private UserService userService;

    @PostMapping("/addUser")
    public UserCreationResponse addUser(@RequestBody @Validated UserCreationRequest req) {
        return userService.addStudent(req);
    }

    @GetMapping("/filter") // http://localhost:6969/user/filter?filterBy=NAME&operator=EQUALS&value=aayu
    public List<UserFilterResponse> filterBy(@RequestParam("filterBy") UserFilter filterBy,
                                             @RequestParam("operator") Operator operator,
                                             @RequestParam("value") String value) {
        return userService.filterBy(filterBy, operator, value);
    }
}
