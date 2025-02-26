package ru.kate.ebook.ebookserv.controllers.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class BookDto {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Boolean isTestIn;
    private String originalFileName;
}
