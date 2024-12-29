package project.library.service.impl.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.library.dto.book.BookCreationRequest;
import project.library.dto.book.BookCreationResponse;
import project.library.dto.book.BookFilterResponse;
import project.library.enums.BookFilter;
import project.library.enums.Operator;
import project.library.model.Author;
import project.library.model.Book;
import project.library.repository.BookRepository;
import project.library.service.BookFilterStrategy;
import project.library.service.impl.AuthorService;

import java.util.List;
import java.util.UUID;

@Service
public class BookService
{
    @Autowired
    private AuthorService authorService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookFilterFactory bookFilterFactory;

    public BookCreationResponse addBook(BookCreationRequest req)
    {
        Author authorFromDb = authorService.findAuthorInDb(req.getAuthorEmail()); // if author is not present then only save data

        if(authorFromDb == null) {
            authorFromDb = req.toAuthor();
        }

        Book book = req.toBook();
        book.setAuthor(authorFromDb);
        bookRepository.save(book); // while saving the book, author will also get saved coz of CASCADE.ALL
        return book.toBookCreationResponse();
    }

    public List<BookFilterResponse> filterBy(BookFilter filterBy, Operator operator, String value)
    {
        BookFilterStrategy strategy = bookFilterFactory.getStrategy(filterBy);
        return strategy.getFilteredBook(operator, value);
    }
}
