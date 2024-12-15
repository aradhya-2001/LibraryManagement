package project.library.dto.book;

import lombok.*;
import project.library.enums.BookType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BookFilterResponse
{
    private String bookNo;
    private String bookName;
    private BookType bookType;
    private String authorName;
    private String authorEmail;
}
