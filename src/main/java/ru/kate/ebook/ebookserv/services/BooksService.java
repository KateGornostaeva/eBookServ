package ru.kate.ebook.ebookserv.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.kate.ebook.ebookserv.controllers.dtos.BookDto;
import ru.kate.ebook.ebookserv.entity.BookEntity;
import ru.kate.ebook.ebookserv.entity.UserEntity;
import ru.kate.ebook.ebookserv.repository.BookRepository;
import ru.kate.ebook.ebookserv.utils.BookMeta;
import ru.kate.ebook.ebookserv.utils.ZipBook;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {

    private final BookRepository bookRepository;
    private final FileService fileService;

    public Optional<UUID> addBook(File file, UserEntity currentUser) throws IOException {
        BookEntity bookEntity = new BookEntity();
        BookMeta bookMeta = ZipBook.getBookMeta(file);
        bookEntity.setTitle(bookMeta.getTitle());
        bookEntity.setAuthor(bookMeta.getAuthor());
        bookEntity.setDescription(bookMeta.getDescription());
        bookEntity.setIsTestIn(bookMeta.getIsTestIn());
        bookEntity.setOwner(currentUser);
        bookEntity.setStoredFileName(fileService.saveFile(file).toString());
        bookRepository.save(bookEntity);
        return Optional.of(bookEntity.getId());
    }


    public Optional<Resource> getBook(UUID id) {
        Optional<BookEntity> optional = bookRepository.findById(id);
        return optional.map(bookEntity -> fileService.loadFile(bookEntity.getStoredFileName()));
    }

    public Page<BookDto> getList(Pageable pageable) {
        Base64.Encoder encoder = Base64.getEncoder();
        Page<BookEntity> entities = bookRepository.findAll(pageable);
        List<BookDto> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            BookDto dto = new BookDto();
            dto.setId(entity.getId());
            dto.setTitle(entity.getTitle());
            dto.setAuthor(entity.getAuthor());
            dto.setDescription(entity.getDescription());
            dto.setIsTestIn(entity.getIsTestIn());
            try {
                byte[] encodedBytes = encoder.encode(ZipBook.getCover(new File(entity.getStoredFileName())));
                dto.setImageB64(encodedBytes);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            dtos.add(dto);
        });
        return new PageImpl<>(dtos, pageable, dtos.size());
    }
}
