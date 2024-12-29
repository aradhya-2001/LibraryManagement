package project.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.library.model.Author;
import project.library.repository.AuthorRepository;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author findAuthorInDb(String authorEmail) {
        return authorRepository.findByEmail(authorEmail);
    }
}
