package ru.kate.ebook.ebookserv.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kate.ebook.ebookserv.controllers.dtos.BookDto;
import ru.kate.ebook.ebookserv.entity.BookEntity;
import ru.kate.ebook.ebookserv.services.BooksService;

import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Книги")
public class BooksController {
    private final BooksService booksService;

    @GetMapping("/list")
    public Page<BookEntity> list(Pageable pageable) {
        return booksService.getList(pageable);
    }

    @PostMapping("/addBook")
    public UUID addBook(@RequestParam("file") MultipartFile file, @RequestBody BookDto book) {
        return booksService.addBook(file, book);
    }
}
