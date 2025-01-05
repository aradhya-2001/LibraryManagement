package project.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.library.dto.GenericReturn;
import project.library.dto.txn.TxnCreateRequest;
import project.library.dto.txn.TxnReturnRequest;
import project.library.exception.BookException;
import project.library.exception.UserException;
import project.library.service.impl.TxnService;

@RestController
@Validated // applies @Validated to all the methods inside the class
@RequestMapping("/txn")
public class TxnController
{
    @Autowired
    private TxnService txnService;

    /*
     If email or book num is left blank, then code will not even reach inside the below function.
     It will fail to get inside coz of @Validated.
     In order for logic to reach inside block, @ControllerAdvice and @ExceptionHandler have to be used
    */
    @PostMapping("/issue")
    public ResponseEntity<GenericReturn> create(@RequestBody TxnCreateRequest request) throws UserException, BookException
    {
        String response = txnService.create(request);
        GenericReturn returnObject = GenericReturn.builder().data(response).build();

        if(response != null){
            returnObject.setCode(0);
            returnObject.setMsg("Its successful");
        }else{
            returnObject.setCode(1);
            returnObject.setMsg("Its failed");
        }
        return new ResponseEntity<>(returnObject, HttpStatus.OK);
    }

    @PutMapping("/return") // Put req coz-only already filled data will be modified
    public int returnTxn(@RequestBody TxnReturnRequest request) throws BookException, UserException {
        return txnService.returnTxn(request);
    }
}
