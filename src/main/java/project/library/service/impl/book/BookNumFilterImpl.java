package project.library.service.impl.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import project.library.dto.book.BookFilterResponse;
import project.library.enums.Operator;
import project.library.model.Book;
import project.library.repository.BookRepository;
import project.library.service.BookFilterStrategy;

import java.util.List;

@Component
public class BookNumFilterImpl implements BookFilterStrategy
{
    @Autowired
    private BookRepository bookRepository;

    @Override
    public List<BookFilterResponse> getFilteredBook(Operator operator, String value)
    {
        if(operator != Operator.EQUALS) {
            throw new IllegalArgumentException("Only Equals (=) is expected with Book Num");
        }

        List<Book> books = bookRepository.findByBookNum(value);
        return Book.toBookFilterResponseList(books);
    }
}
