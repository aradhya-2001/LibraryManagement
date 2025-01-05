package project.library.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import project.library.enums.UserType;
import project.library.model.User;
import project.library.enums.UserStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserCreationRequest
{
    private String userName;

    @NotBlank(message = "user email must not be blank")
    private String userEmail;

    private String userAddress;

    private String userPhone;

    @NotNull(message = "user type cant be null")
    private UserType userType;

    public User toUser()
    {
        return User
                .builder()
                .name(this.userName)
                .email(this.userEmail)
                .address(this.userAddress)
                .phoneNum(this.userPhone)
                .userStatus(UserStatus.ACTIVE) // ACTIVE by default
                .build();
    }
}
