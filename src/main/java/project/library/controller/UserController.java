package project.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.library.dto.GenericReturn;
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
    public ResponseEntity<GenericReturn> addUser(@RequestBody @Validated UserCreationRequest req)
    {
        UserCreationResponse response = userService.addStudent(req);
        GenericReturn returnObject = GenericReturn.builder().data(response).build();

        if(response != null){
            returnObject.setCode(0);
            returnObject.setMsg("Its successful");
        }else{
            returnObject.setCode(1);
            returnObject.setMsg("Its failed");
        }
        return new ResponseEntity<>(returnObject, HttpStatus.OK);
    }

    @GetMapping("/filter") // http://localhost:6969/user/filter?filterBy=NAME&operator=EQUALS&value=aayu
    public List<UserFilterResponse> filterBy(@RequestParam("filterBy") UserFilter filterBy,
                                             @RequestParam("operator") Operator operator,
                                             @RequestParam("value") String value) {
        return userService.filterBy(filterBy, operator, value);
    }
}
