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

    public Optional<String> addBook(File file, UserEntity currentUser) throws IOException {
        BookEntity bookEntity = new BookEntity();
        BookMeta bookMeta = ZipBook.getBookMeta(file);
        bookEntity.setTitle(bookMeta.getTitle());
        bookEntity.setAuthor(bookMeta.getAuthor());
        bookEntity.setDescription(bookMeta.getDescription());
        bookEntity.setIsTestIn(bookMeta.getIsTestIn());
        bookEntity.setOwner(currentUser);
        bookEntity.setStoredFileName(fileService.saveFile(file).toString());
        bookEntity.setCode(getRandomNumber());
        bookRepository.save(bookEntity);
        return Optional.of(bookEntity.getCode());
    }


    public Optional<Resource> getBook(UUID id) {
        Optional<BookEntity> optional = bookRepository.findById(id);
        return optional.map(bookEntity -> fileService.loadFile(bookEntity.getStoredFileName()));
    }

    public Page<BookDto> getList(Pageable pageable) {
        Page<BookEntity> entities = bookRepository.findAll(pageable);
        List<BookDto> dtos = new ArrayList<>();
        entities.forEach(entity -> {
            dtos.add(new BookDto(entity));
        });
        return new PageImpl<>(dtos, pageable, dtos.size());
    }

    private String getRandomNumber() {
        // Создаем объект Random для генерации случайных чисел
        Random rnd = new Random();

        // Генерируем случайное число от 0 до 999999
        int number = rnd.nextInt(999999);

        // Форматируем число до 6 цифр, добавляя нули в начало при необходимости
        return String.format("%06d", number);
    }
}
