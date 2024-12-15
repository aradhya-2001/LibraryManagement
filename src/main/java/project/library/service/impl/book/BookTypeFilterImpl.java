package project.library.service.impl.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.library.dto.book.BookFilterResponse;
import project.library.enums.BookType;
import project.library.enums.Operator;
import project.library.model.Book;
import project.library.repository.BookRepository;
import project.library.service.BookFilterStrategy;

import java.util.List;

@Component
public class BookTypeFilterImpl implements BookFilterStrategy
{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookFilterResponse> getFilteredBook(Operator operator, String value)
    {
        if(operator != Operator.EQUALS) {
            throw new IllegalArgumentException("Only Equals (=) is expected with Book Type");
        }

        // Convert String value to Book type
        BookType bookType;
        try {
            bookType = BookType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid Book Type: " + value, e);
        }

        List<Book> books = bookRepository.findByBookType(bookType);
        return Book.toBookFilterResponseList(books);
    }
}
