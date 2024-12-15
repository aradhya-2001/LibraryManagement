package project.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.library.enums.BookType;
import project.library.model.Book;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer>
{
    List<Book> findByTitle(String value);

    List<Book> findByTitleContains(String value);

    List<Book> findByBookType(BookType value);

    List<Book> findByBookNum(String value);
}
