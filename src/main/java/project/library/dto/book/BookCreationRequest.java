package project.library.dto.book;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import project.library.enums.BookType;
import project.library.model.Author;
import project.library.model.Book;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookCreationRequest
{
    @NotBlank(message = "book title must not be blank")
    private String bookTitle;

    @NotBlank(message = "book number must not be blank")
    private String bookNum;

    @NotNull(message = "book type must not be null")
    private BookType bookType;

    @Positive(message = "positive values are expected")
    private Integer securityAmt;

    @NotBlank(message = "author name must not be blank")
    private String authorName;

    @NotBlank(message = "author email must not be blank")
    private String authorEmail;

    public Book toBook()
    {
        return Book
                .builder()
                .title(this.bookTitle)
                .bookNum(this.bookNum)
                .bookType(this.bookType)
                .securityAmt(this.securityAmt)
                .build();
    }

    public Author toAuthor()
    {
       return Author.builder()
               .id(UUID.randomUUID().toString())
               .email(this.authorEmail)
               .name(this.authorName)
               .build();
    }
}
