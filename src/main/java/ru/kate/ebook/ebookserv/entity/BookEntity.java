package ru.kate.ebook.ebookserv.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "books")
public class BookEntity {

    @Id
    private UUID id;

    private String title;
    private String author;
    private String isbn;
    private String publisher;
    private Boolean isTestIn;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity owner;

    private String originalFileName;
    private String storedFileName;

}
