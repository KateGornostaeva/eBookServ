package ru.kate.ebook.ebookserv.controllers.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kate.ebook.ebookserv.entity.BookEntity;
import ru.kate.ebook.ebookserv.utils.ZipBook;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

/**
 * Dto для отправки в приложение
 */
@Data
@NoArgsConstructor
public class BookDto {

    private final static Base64.Encoder encoder = Base64.getEncoder();

    private UUID id;
    private String title;
    private String author;
    private Boolean isTestIn;
    private String description;
    private String code;
    private byte[] imageB64;

    /**
     * Создание dto из entity
     *
     * @param entity
     */
    public BookDto(BookEntity entity) {
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor();
        description = entity.getDescription();
        code = entity.getCode();
        isTestIn = entity.getIsTestIn();
        try {
            imageB64 = encoder.encode(ZipBook.getCover(new File(entity.getStoredFileName())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
