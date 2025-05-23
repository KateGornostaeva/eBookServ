package ru.kate.ebook.ebookserv.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.kate.ebook.ebookserv.controllers.dtos.BookDto;
import ru.kate.ebook.ebookserv.entity.BookEntity;
import ru.kate.ebook.ebookserv.repository.BookRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {

    private final BookRepository bookRepository;

    public Optional<List<BookDto>> search(String query) {
        List<BookDto> dtos = new ArrayList<>();
        List<BookEntity> bookEntities = bookRepository.findByDescriptionContainingIgnoreCaseOrCodeContainingIgnoreCaseOrTitleContainingIgnoreCase(query, query, query);
        for (BookEntity bookEntity : bookEntities) {
            dtos.add(new BookDto(bookEntity));
        }
        return Optional.of(dtos);
    }
}
