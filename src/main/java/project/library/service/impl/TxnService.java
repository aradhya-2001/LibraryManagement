package project.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import project.library.dto.txn.TxnCreateRequest;
import project.library.dto.txn.TxnReturnRequest;
import project.library.enums.TxnStatus;
import project.library.exception.BookException;
import project.library.exception.TxnException;
import project.library.exception.UserException;
import project.library.model.Book;
import project.library.model.Txn;
import project.library.model.User;
import project.library.repository.TxnRepository;
import project.library.service.impl.book.BookService;
import project.library.service.impl.user.UserService;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TxnService
{
    @Autowired
    private TxnRepository txnRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Value("${book.validIssueDays}") // book.validIssueDays defined in application.properties
    private int validDays;

    @Value("${book.fineRate}")
    private int fineRate;

    @Transactional
    public String create(TxnCreateRequest req) throws BookException, UserException
    {
        User userFomDB = userService.isValid(req.getUserEmail());
        Book bookFromDb = bookService.isValid(req.getBookNum());

        nullCheck(userFomDB, bookFromDb);

        if(bookFromDb.getUser() != null) { // check if the book asked by the user is already assigned to another user
            throw new BookException("Book is not free to be issued");
        }

        String txnId = UUID.randomUUID().toString();
        Txn txn = Txn.builder()
                .txnId(txnId)
                .book(bookFromDb)
                .user(userFomDB)
                .txnStatus(TxnStatus.ISSUED)
                .issueDate(new Date())
                .build();

        txnRepository.save(txn);
        bookService.mapBookToUser(bookFromDb, userFomDB); // mark book as unavailable
        return txnId;
    }

    @Transactional
    public int returnTxn(TxnReturnRequest req) throws BookException, UserException
    {
        Txn txnFromDb = txnRepository.findByTxnId(req.getTxnId());
        if(txnFromDb == null) {
            throw new TxnException("No txn has been found in the db with txnId: " + req.getTxnId()); // this is a run time exception, so it will by default gets rolled out if exception comes.
        }

        User userFromDb = userService.isValid(txnFromDb.getUser().getEmail());
        Book bookFromDb = bookService.isValid(req.getBookNum());

        nullCheck(userFromDb, bookFromDb);

        if(bookFromDb.getUser() != null && bookFromDb.getUser().equals(userFromDb))
        {
            Integer securityAmt = bookFromDb.getSecurityAmt();
            int amt = settlementAmt(txnFromDb.getIssueDate(), securityAmt);
            txnFromDb.setSettlementAmt(amt);

            if(amt == securityAmt) {
                txnFromDb.setTxnStatus(TxnStatus.RETURNED);
            } else if (amt < securityAmt) {
                txnFromDb.setTxnStatus(TxnStatus.FINED);
            }

            bookFromDb.setUser(null); // book is being returned so, making it available
            txnFromDb.setSubmitDate(new Date());
            txnRepository.save(txnFromDb); // this will also save book in Db coz of Cascade.ALL for Book in Txn entity

            return amt;
        }
        throw new TxnException("Book is assigned to someone else or not assigned to user: " + userFromDb.getName());
    }

    // no interaction with db so no need of @Transactional
    private int settlementAmt(Date issueDate, Integer securityAmt)
    {
        long issueTime = issueDate.getTime(); // gives milliseconds
        long returnTime = System.currentTimeMillis(); // gives milliseconds
        long timeDiff = returnTime - issueTime;
        int daysPassed = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

        if(daysPassed > validDays)
        {
            int fineAmt = (daysPassed - validDays) * fineRate;
            return securityAmt - fineAmt;
        }

        return securityAmt;
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = {BookException.class, UserException.class})
    private void nullCheck(User user, Book book) throws UserException, BookException
    {
        if(user == null) { // check if user is valid (exist in DB) or not
            throw new UserException("User is not valid");
        }

        if(book == null) {
            throw new BookException("Book is not valid");
        }
    }
}

/*
@Transactional: -
Until reached to last line of func, not do anything.
By default, if there come some Runtime exceptions anywhere in func, nothing will be committed /it will get roll backed.
But if it encounters a Compile time exception, statements after that exception will not be executed, but after compilation, it will not get rolled back i.e., statements before exception will get executed.

createTransactionIfNecessary();
try {
    callMethod();
    commitTransactionAfterReturning();
} catch (exception) {
  completeTransactionAfterThrowing();
    throw exception;
}
Above is how @Transactional is working.
A proxy class is created which will have code like above and spring will run that class at runtime.
AOP works in the same way, so @Transactional is internally using AOP.
*/
