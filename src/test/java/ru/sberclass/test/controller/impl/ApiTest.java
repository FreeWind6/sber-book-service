package ru.sberclass.test.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sberclass.test.entity.Book;
import ru.sberclass.test.model.save.Response;
import ru.sberclass.test.repositories.BookRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ApiTest {
    @Autowired
    BookRepository repository;

    private static RestTemplate restTemplate = null;

    @LocalServerPort
    private int PORT;

    private static final String BASE_URL = "http://localhost";

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @Test
    void testBooksByLetter() {
        save();

        String path = BASE_URL.concat(":" + PORT + "" + "/api/v1/sber-book/books-by-letter?start-with=Т&ignore-case=true");

        ResponseEntity<String> exchange = restTemplate.exchange(path,
                HttpMethod.GET, null, String.class);

        assertAll(
                () -> assertNotNull(exchange),
                () -> assertEquals(HttpStatus.OK, exchange.getStatusCode())
        );
    }

    @Test
    void testStatistic() {
        save();

        String path = BASE_URL.concat(":" + PORT + "" + "/api/v1/sber-book/statistic");

        ResponseEntity<String> exchange = restTemplate.exchange(path,
                HttpMethod.GET, null, String.class);

        assertAll(
                () -> assertNotNull(exchange),
                () -> assertEquals(HttpStatus.OK, exchange.getStatusCode())
        );
    }

    @Test
    void testBooks() {
        save();

        String path = BASE_URL.concat(":" + PORT + "" + "/api/v1/sber-book/books");

        ResponseEntity<String> exchange = restTemplate.exchange(path,
                HttpMethod.GET, null, String.class);

        assertAll(
                () -> assertNotNull(exchange),
                () -> assertEquals(HttpStatus.OK, exchange.getStatusCode())
        );
    }

    @Test
    void testSave() {
        Book book = new Book();
        book.setAuthor("Вася");
        book.setName("Весёлая жизнь");

        HttpEntity<Book> httpRequest = new HttpEntity<>(book, null);

        String path = BASE_URL.concat(":" + PORT + "" + "/api/v1/sber-book/save");

        ResponseEntity<Response> exchange = restTemplate.exchange(path,
                HttpMethod.POST, httpRequest, Response.class);

        assertAll(
                () -> assertNotNull(exchange),
                () -> assertEquals(HttpStatus.OK, exchange.getStatusCode())
        );
    }

    @Test
    void testErrorBooksByLetter() {

        String path = BASE_URL.concat(":" + PORT + "" + "/api/v1/sber-book/books-by-letter?start-with=С&ignore-case=false");

        assertAll(
                () -> {
                    HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class,
                            () -> restTemplate.exchange(path, HttpMethod.GET, null, String.class));
                    assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
                }
        );
    }

    private void save() {
        Book book = new Book();
        book.setAuthor("Тестовый автор");
        book.setName("Тестовое имя");
        repository.save(book);
    }
}
