package ru.kate.ebook.ebookserv.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/*
 ** Служебный клас по работе с zip файлом
 *
 */
public class ZipBook {

    private final static ObjectMapper mapper = new ObjectMapper();

    //получить метаданные книги из zip архива
    public static BookMeta getBookMeta(File zipBookFile) throws IOException {

        BookMeta bookMeta = new BookMeta();
        bookMeta.setPath(zipBookFile.toPath());

        try (ZipFile zipFile = new ZipFile(zipBookFile)) {

            for (ZipEntry entry : Collections.list(zipFile.entries())) {
                if (!entry.isDirectory() && entry.getName().equals(BookMeta.META_NAME)) {
                    InputStream inputStream = zipFile.getInputStream(entry);
                    BookMeta rawMeta = mapper.readValue(inputStream, BookMeta.class);
                    bookMeta.setAuthor(rawMeta.getAuthor());
                    bookMeta.setTitle(rawMeta.getTitle());
                    bookMeta.setBookFileName(rawMeta.getBookFileName());
                    bookMeta.setIsTestIn(rawMeta.getIsTestIn());
                }
            }
        }
        return bookMeta;
    }

    public static byte[] getCover(File zipBookFile) throws IOException {
        byte[] cover = null;
        try (ZipFile zipFile = new ZipFile(zipBookFile)) {
            for (ZipEntry entry : Collections.list(zipFile.entries())) {
                if (!entry.isDirectory() && entry.getName().equals(BookMeta.COVER_NAME)) {
                    InputStream inputStream = zipFile.getInputStream(entry);
                    cover = inputStream.readAllBytes();
                }
            }
        }
        return cover;
    }

    //извлечь файл книги по данным из метаданных книги
    public static File getBookFile(BookMeta bookMeta) throws IOException {
        File outputFile = null;
        try (ZipFile zipFile = new ZipFile(String.valueOf(bookMeta.getPath()))) {
            for (ZipEntry entry : Collections.list(zipFile.entries())) {
                if (!entry.isDirectory() && entry.getName().equals(bookMeta.getBookFileName())) {
                    InputStream inputStream = zipFile.getInputStream(entry);
                    Path tempDir = Files.createTempDirectory(Paths.get(System.getProperty("java.io.tmpdir")), "ebookTemp");
                    outputFile = new File(tempDir + "/" + bookMeta.getBookFileName());
                    Files.copy(inputStream, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
        return outputFile;
    }
}
