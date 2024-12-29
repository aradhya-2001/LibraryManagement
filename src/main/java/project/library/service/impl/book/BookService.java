package project.library.service.impl.book;

import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.library.dto.book.BookCreationRequest;
import project.library.dto.book.BookCreationResponse;
import project.library.dto.book.BookFilterResponse;
import project.library.enums.BookFilter;
import project.library.enums.Operator;
import project.library.model.Author;
import project.library.model.Book;
import project.library.model.User;
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

    public Book isValid( String bookNum)
    {
        List<Book> books = bookRepository.findByBookNum(bookNum);
        if(books.isEmpty()) {
            return null;
        }
        return books.get(0);
    }

    public void mapBookToUser(Book bookFromDb, User userFomDB) // updating a row (user_id) into the book table
    {
        bookFromDb.setUser(userFomDB);
        bookRepository.save(bookFromDb);
    }
}
