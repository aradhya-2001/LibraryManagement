package project.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.library.dto.txn.TxnCreateRequest;
import project.library.dto.txn.TxnReturnRequest;
import project.library.exception.BookException;
import project.library.exception.UserException;
import project.library.service.impl.TxnService;

@RestController
@Validated
@RequestMapping("/txn")
public class TxnController
{
    @Autowired
    private TxnService txnService;

    @PostMapping("/issue")
    public String create(@RequestBody TxnCreateRequest request) throws UserException, BookException {
        return txnService.create(request);
    }

    @PutMapping("/return") // Put req coz-only already filled data will be modified
    public int returnTxn(@RequestBody TxnReturnRequest request) throws BookException, UserException {
        return txnService.returnTxn(request);
    }
}
