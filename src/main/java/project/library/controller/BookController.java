package project.library.controller;


import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.library.dto.GenericReturn;
import project.library.dto.book.BookCreationRequest;
import project.library.dto.book.BookCreationResponse;
import project.library.dto.book.BookFilterResponse;
import project.library.enums.BookFilter;
import project.library.enums.Operator;
import project.library.service.impl.book.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookController
{
    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<GenericReturn> addBook(@RequestBody BookCreationRequest req)
    {
        BookCreationResponse response = bookService.addBook(req);
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

    @GetMapping("/filter")
    public List<BookFilterResponse> filterBy(@NotNull(message = "filter by must not be null") @RequestParam("filterBy") BookFilter filterBy,
                                             @NotNull(message = "operator by must not be null") @RequestParam("operator") Operator operator,
                                             @NotNull(message = "value by must not be blank") @RequestParam("value") String value) {
        return bookService.filterBy(filterBy, operator, value);
    }
}
