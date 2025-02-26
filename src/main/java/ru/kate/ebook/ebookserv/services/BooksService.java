package ru.kate.ebook.ebookserv.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kate.ebook.ebookserv.controllers.dtos.BookDto;
import ru.kate.ebook.ebookserv.entity.BookEntity;
import ru.kate.ebook.ebookserv.repository.BookRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {

    private final BookRepository bookRepository;
    private final FileService fileService;

    public UUID addBook(MultipartFile file, BookDto bookDto) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(bookDto.getTitle());
        bookEntity.setAuthor(bookDto.getAuthor());
        bookEntity.setPublisher(bookDto.getPublisher());
        bookEntity.setIsbn(bookDto.getIsbn());
        fileService.saveFile(file);
        return null;
    }


    public BookEntity getBook(UUID id) {
        return null;
    }

    public Page<BookEntity> getList(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
