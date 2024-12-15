package project.library.controller;


import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
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
    public BookCreationResponse addBook(@RequestBody BookCreationRequest req) {
        return bookService.addBook(req);
    }

    @GetMapping("/filter")
    public List<BookFilterResponse> filterBy(@NotNull(message = "filter by must not be null") @RequestParam("filterBy") BookFilter filterBy,
                                             @NotNull(message = "operator by must not be null") @RequestParam("operator") Operator operator,
                                             @NotNull(message = "value by must not be blank") @RequestParam("value") String value) {
        return bookService.filterBy(filterBy, operator, value);
    }
}
