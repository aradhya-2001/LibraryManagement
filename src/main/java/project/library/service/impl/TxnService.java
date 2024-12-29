package project.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.library.dto.TxnRequest;
import project.library.enums.TxnStatus;
import project.library.exception.BookException;
import project.library.exception.UserException;
import project.library.model.Book;
import project.library.model.Txn;
import project.library.model.User;
import project.library.repository.TxnRepository;
import project.library.service.impl.book.BookService;
import project.library.service.impl.user.UserService;

import java.util.Date;
import java.util.UUID;

@Service
public class TxnService
{
    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Transactional(rollbackFor = {BookException.class, UserException.class})
    public String create(TxnRequest txnRequest) throws UserException, BookException
    {
        User userFomDB = userService.isValid(txnRequest.getUserEmail());
        if(userFomDB == null) { // check if user is valid (exist in DB) or not
            throw new UserException("User is not valid");
        }

        Book bookFromDb = bookService.isValid(txnRequest.getBookNum());
        if(bookFromDb == null) {
            throw new BookException("Book is not valid");
        }

        if(bookFromDb.getUser() != null) { // check if the book asked by the user is already assigned to another user
            throw new BookException("Book is not free to be issued");
        }

        return createTnx(bookFromDb, userFomDB);
    }

    @Transactional
    private String createTnx(Book book, User user)
    {
        String txnId = UUID.randomUUID().toString();
        Txn txn = Txn.builder()
                .txnId(txnId)
                .book(book)
                .user(user)
                .txnStatus(TxnStatus.ISSUED)
                .issueDate(new Date())
                .build();

        txnRepository.save(txn);
        bookService.mapBookToUser(book, user);
        return txnId;
    }
}

/*
@Transactional: -
Until reached to last line of func, not do anything.
By default, if there come some Runtime exceptions anywhere in func, nothing will be committed /it will get roll backed.
But if it encounters a Compile time exception, statements after that exception will not be executed, but after compilation, it will not get rolled back i.e., statements before exception will get executed.
*/
