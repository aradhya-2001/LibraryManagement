package project.library.service;

import project.library.dto.book.BookFilterResponse;
import project.library.enums.Operator;

import java.util.List;

public interface BookFilterStrategy {
    List<BookFilterResponse> getFilteredBook (Operator operator, String value);
}
