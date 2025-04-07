package ru.kate.ebook.ebookserv.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class BookMeta {

    public final static String META_NAME = "bookMeta.json";
    public final static String COVER_NAME = "cover.png";
    public final static String TEST_NAME = "test.json";
    private final static ObjectMapper mapper = new ObjectMapper();

    //название учебника
    private String title;

    //автор учебника (берётся из кредов пользователя)
    private String author;

    //имя файла учебника в архиве
    private String bookFileName;

    @JsonIgnore
    //указывает полный путь на файл с zip архивом с учебником и прочим
    private Path path;

    //признак наличия теста
    private Boolean isTestIn;

    //Краткое описание учебника
    private String description;

    @JsonIgnore
    //признак черновика
    private Boolean isDraft;

    @JsonIgnore
    public File getFile() {
        try {
            File file = new File(Paths.get(System.getProperty("java.io.tmpdir")) + File.separator + META_NAME);
            mapper.writeValue(file, this);
            return file;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
