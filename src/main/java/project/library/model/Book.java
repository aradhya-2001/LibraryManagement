package project.library.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import project.library.dto.book.BookCreationResponse;
import project.library.dto.book.BookFilterResponse;
import project.library.enums.BookType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Book extends TimeStamp
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50)
    private String title;

    @Column(length = 20, unique = true)
    private String bookNum;

    @Enumerated
    private BookType bookType;

    @Column(nullable = false)
    private Integer securityAmt;

    @ManyToOne // Many Book, One User
    @JoinColumn // This will make @ID (by default) of User as a column in the Book db table as a foreign key. This is only used in the owning side of the relationship.
    @JsonIgnoreProperties(value = "bookList") // working the same as JsonIgnore but here told in advance not to include bookList member of User entity if the whole Book entity is being returned as response
    private User user;

    @ManyToOne(cascade = CascadeType.ALL) // update Author DB table whenever Book entity is being updated
    @JoinColumns({
            @JoinColumn(name = "author_id", referencedColumnName = "id"), // db column name will be author_id
            @JoinColumn(name = "author_email", referencedColumnName = "email")
    })
    private Author author;

    @OneToMany(mappedBy = "book")
    private List<Txn> txnList;

    public static List<BookFilterResponse> toBookFilterResponseList (List<Book> books)
    {
        List<BookFilterResponse> bookFilterResponses = new ArrayList<>();
        for(Book book : books) {
            bookFilterResponses.add(new BookFilterResponse(book.getBookNum(), book.getTitle(), book.getBookType(), book.getAuthor().getName(), book.getAuthor().getEmail()));
        }

        return bookFilterResponses;
    }

    public BookCreationResponse toBookCreationResponse()
    {
        return BookCreationResponse
                .builder()
                .bookTitle(this.getTitle())
                .bookNum(this.getBookNum())
                .securityAmt(this.getSecurityAmt())
                .build();
    }
}
