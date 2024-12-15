package project.library.service.impl.book;

import org.springframework.stereotype.Component;
import project.library.enums.BookFilter;
import project.library.service.BookFilterStrategy;

import java.util.HashMap;
import java.util.Map;

@Component
public class BookFilterFactory
{
    private final Map<BookFilter, BookFilterStrategy> strategyMap = new HashMap<>();

    public BookFilterFactory(BookNumFilterImpl bookNumFilter, BookTitleFilterImpl bookTitleFilter, BookTypeFilterImpl bookTypeFilter) // constructor dependency injection
    {
        strategyMap.put(BookFilter.BOOK_NUM, bookNumFilter);
        strategyMap.put(BookFilter.BOOK_TITLE, bookTitleFilter);
        strategyMap.put(BookFilter.BOOK_TYPE, bookTypeFilter);
    }

    public BookFilterStrategy getStrategy(BookFilter filter) {
        return strategyMap.get(filter);
    }
}
