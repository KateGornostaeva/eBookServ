package ru.kate.ebook.ebookserv.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kate.ebook.ebookserv.controllers.dtos.BookDto;
import ru.kate.ebook.ebookserv.services.BooksService;
import ru.kate.ebook.ebookserv.services.SearchService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
@Tag(name = "Поиск")
public class SearchController {

    private final SearchService searchService;
    private final BooksService booksService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getBooks(@RequestParam String query) {
        return ResponseEntity.of(searchService.search(query));
    }

    @GetMapping("/getBook")
    public ResponseEntity<Resource> getBook(@RequestParam("id") UUID id) {
        Optional<Resource> optionalResource = booksService.getBook(id);
        if (optionalResource.isPresent()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                            + optionalResource.get().getFilename() + "\"")
                    .body(optionalResource.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
