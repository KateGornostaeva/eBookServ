package ru.kate.ebook.ebookserv.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kate.ebook.ebookserv.entity.Book;
import ru.kate.ebook.ebookserv.services.BooksService;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Книги")
public class BooksController {
    private final BooksService booksService;

    @GetMapping("/list")
    public Page<Book> list(Pageable pageable) {
        return Page.empty();

    }
}
