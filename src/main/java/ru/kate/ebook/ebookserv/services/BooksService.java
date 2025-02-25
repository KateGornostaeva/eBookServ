package ru.kate.ebook.ebookserv.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BooksService {

    public UUID addBook(Book book) {

    }


    public Book getBook(UUID id) {}

    public Page<Book> getList(Pageable pageable) {

    }
}
