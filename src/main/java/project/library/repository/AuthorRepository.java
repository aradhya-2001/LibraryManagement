package project.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.library.model.Author;
import project.library.model.AuthorCompositeKey;

@Repository
public interface AuthorRepository extends JpaRepository<Author, AuthorCompositeKey>
{
    Author findByEmail(String email);
}
