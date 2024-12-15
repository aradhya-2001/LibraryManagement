package project.library.dto.book;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class BookCreationResponse
{
    private String bookTitle;
    private String bookNum;
    private Integer securityAmt;
}
