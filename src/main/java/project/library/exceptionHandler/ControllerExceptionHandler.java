package project.library.exceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.library.exception.BookException;
import project.library.exception.TxnException;
import project.library.exception.UserException;

import java.lang.reflect.Array;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ControllerExceptionHandler
{
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleNotBlankException(MethodArgumentNotValidException e){
        return new ResponseEntity<>(e.getBindingResult().getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleNotNullException(HttpMessageNotReadableException e)
    {
        Throwable cause = e.getCause();
        if(cause instanceof InvalidFormatException exp) // On evaluating e, we can find in the cause block, issue is InvalidFormatException
        {
            Class<?> targetType = exp.getTargetType();
            Object[] enumConstants = targetType.getEnumConstants();
            List<String> values = new ArrayList<>();
            for(Object enumConstant : enumConstants){
                values.add(enumConstant.toString());
            }

            String field = exp.getPath().get(0).getFieldName();
            String message = String.format(
                    "Invalid value provided for field '%s'. Allowed values are: %s",
                    field,
                    values
            );
            return  new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>("Invalid input provided.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleUserPhoneNumException(DataIntegrityViolationException e)
    {
        Throwable cause = e.getCause();
        if(cause instanceof ConstraintViolationException exp) { // not working, don't know why
            return new ResponseEntity<>(exp.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Invalid input provided.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = TxnException.class)
    public ResponseEntity<Object> handleTxnException(TxnException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = BookException.class)
    public ResponseEntity<Object> handleBookException(BookException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = UserException.class)
    public ResponseEntity<Object> handleUserException(UserException e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
