package project.library.model;

import jakarta.persistence.*;
import lombok.*;
import project.library.enums.TxnStatus;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Txn extends TimeStamp
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String txnId; // Db table id is highly confidential, and it shouldn't be revealed to the front end. Instead of that, txnId would be exposed to the front end.

    @Enumerated
    private TxnStatus txnStatus;

    private Double settlementAmt; // depends upon when the book is being returned.

    private Date issueDate;
    private Date submitDate;

    @ManyToOne
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinColumn
    private Book book;
}
