package project.library.model;

import jakarta.persistence.*;
import lombok.*;
import project.library.dto.user.UserCreationResponse;
import project.library.dto.user.UserFilterResponse;
import project.library.enums.UserStatus;
import project.library.enums.UserType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class User extends TimeStamp
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String name;

    @Column(unique = true, length = 15)
    private String phoneNum;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    private String address;

    @Enumerated
    private UserStatus userStatus;

    @Enumerated
    private UserType userType;

    /*
    @OneToMany:
    One User, Many Books.
    (mappedBy = "user"):
    This tells JPA/Hibernate that in the bidirectional relationship btw User and Book, the owning side is Book (the owner will be a field/variable "user") and the inverse side is User.
    Owning side means a foreign key column will be created in the Book table db with id of User like user_id.
    So User will be linked to Book via the foreign key present in the Book table.
    And also, it tells JPA to not create a new column in User for the relationship instead to use the existing foreign key (user_id) in Book table.
    */
    @OneToMany(mappedBy = "user")
    private List<Book> bookList;

    @OneToMany(mappedBy = "user")
    private List<Txn> txnList;

    public static List<UserFilterResponse> toUserFilterResponse(List<User> users)
    {
        List<UserFilterResponse> userFilterResponses = new ArrayList<>();
        for(User user : users) {
            userFilterResponses.add(new UserFilterResponse(user.getName(), user.getEmail(), user.getPhoneNum()));
        }

        return userFilterResponses;
    }

    public UserCreationResponse toUserCreationResponse()
    {
        return UserCreationResponse
                .builder()
                .userName(this.getName())
                .userAddress(this.getAddress())
                .userEmail(this.getEmail())
                .userPhone(this.getPhoneNum())
                .build();
    }
}
