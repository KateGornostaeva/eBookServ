package ru.kate.ebook.ebookserv.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kate.ebook.ebookserv.controllers.dtos.BookDto;
import ru.kate.ebook.ebookserv.entity.UserEntity;
import ru.kate.ebook.ebookserv.security.Role;
import ru.kate.ebook.ebookserv.services.BooksService;
import ru.kate.ebook.ebookserv.services.UserService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

/**
 * Отвечает за передачу данных связанных с книгами
 */
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Tag(name = "Книги")
public class BooksController {
    private final BooksService booksService;
    private final UserService userService;

    /**
     * отдать список книг всем пользователям
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseEntity<Page<BookDto>> list(@RequestParam Integer page, @RequestParam Integer size) {
        return ResponseEntity.of(Optional.ofNullable(booksService.getList(PageRequest.of(page, size))));
    }

    /**
     * сохраняет книгу на сервере, нужна авторизация и только для учителя
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = "/addBook", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<String> addBook(HttpServletRequest request) throws IOException {
        UserEntity currentUser = userService.getCurrentUser(); // получаем текущего пользователя из контекста
        if (currentUser.getRole().equals(Role.ROLE_TEACHER)) { // проверяем роль текущего пользователя
            Path tempFilePath = Paths.get(System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID() + ".zip");
            Files.copy(request.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.of(booksService.addBook(tempFilePath.toFile(), currentUser));
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }

    /**
     * скачать книгу, только учитель и студент
     * @param id
     * @return
     */
    @GetMapping("/getBook")
    public ResponseEntity<Resource> getBook(@RequestParam("id") UUID id) {
        UserEntity currentUser = userService.getCurrentUser();
        if (currentUser.getRole().equals(Role.ROLE_TEACHER) || currentUser.getRole().equals(Role.ROLE_STUDENT)) {

            Optional<Resource> optionalResource = booksService.getBook(id);
            if (optionalResource.isPresent()) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                                + optionalResource.get().getFilename() + "\"")
                        .body(optionalResource.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }
    }
}
