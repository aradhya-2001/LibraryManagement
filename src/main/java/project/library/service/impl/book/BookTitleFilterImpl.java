package project.library.service.impl.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.library.dto.book.BookFilterResponse;
import project.library.enums.Operator;
import project.library.model.Book;
import project.library.repository.BookRepository;
import project.library.service.BookFilterStrategy;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookTitleFilterImpl implements BookFilterStrategy {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookFilterResponse> getFilteredBook(Operator operator, String value)
    {
        List<Book> books = new ArrayList<>();

        books = switch (operator) {
            case EQUALS -> bookRepository.findByTitle(value);
            case LIKE -> bookRepository.findByTitleContains(value);
            default -> books;
        };

        return Book.toBookFilterResponseList(books);
    }
}
