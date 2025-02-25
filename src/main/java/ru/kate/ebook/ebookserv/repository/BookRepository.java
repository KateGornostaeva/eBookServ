package ru.kate.ebook.ebookserv.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kate.ebook.ebookserv.entity.Book;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

    public List<Book> getList();
}
