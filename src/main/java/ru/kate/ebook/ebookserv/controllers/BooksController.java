package ru.kate.ebook.ebookserv.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.kate.ebook.ebookserv.controllers.dtos.BookDto;
import ru.kate.ebook.ebookserv.entity.BookEntity;
import ru.kate.ebook.ebookserv.services.BooksService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Книги")
public class BooksController {
    private final BooksService booksService;

    @GetMapping("/list")
    public ResponseEntity<Page<BookEntity>> list(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.of(Optional.ofNullable(booksService.getList(PageRequest.of(page, size))));
    }

    @PostMapping("/addBook")
    public UUID addBook(@RequestParam("file") MultipartFile file, @RequestBody BookDto book) {
        return booksService.addBook(file, book);
    }
}
