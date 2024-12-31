package project.library.dto.txn;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TxnReturnRequest
{
    @NotBlank(message = "Book Number cannot be blank")
    private String bookNum;

    @NotBlank(message = "TxnID cannot be blank")
    private String txnId;
}
