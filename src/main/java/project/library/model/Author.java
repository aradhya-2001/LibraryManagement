package project.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
@IdClass(AuthorCompositeKey.class)
public class Author extends TimeStamp
{
    @Id
    private String id;

    @Id
    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL) // update Book DB table whenever Author entity is being updated
    @JsonIgnore
    private List<Book> bookList;
}

    /*
    @JsonIgnore:-
    If List<Book> is being returned, then Book entity has Author object and Author entity has List<Book> object.
    So infinite loop will be created btw Book and Author and infinite responses will be generated.
    To avoid that, @JsonIgnore is used
    */
