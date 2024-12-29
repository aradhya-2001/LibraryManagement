package project.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.library.dto.TxnRequest;
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
    public String create(@RequestBody TxnRequest txnRequest) throws UserException, BookException {
        return txnService.create(txnRequest);
    }
}
