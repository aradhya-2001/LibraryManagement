package project.library.dto.txn;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TxnCreateRequest
{
    @NotBlank(message = "User Email must not be blank")
    private String userEmail;

    @NotBlank(message = "Book Number must not be blank")
    private String bookNum;
}
