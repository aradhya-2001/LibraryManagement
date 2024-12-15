package project.library.dto.user;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserFilterResponse
{
    private String userName;
    private String userEmail;
    private String userPhone;
}
