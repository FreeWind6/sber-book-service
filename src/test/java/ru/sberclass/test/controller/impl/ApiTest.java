package ru.sberclass.test.controller.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sberclass.test.entity.Book;
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
    void testApi() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpRequest = new HttpEntity<>(httpHeaders);

        Book book = new Book();
        book.setAuthor("Тестовый автор");
        book.setName("Тестовое имя");
        repository.save(book);

        String path = BASE_URL.concat(":" + PORT + "" + "/api/v1/sber-book/books-by-letter?start-with=Т&ignore-case=true");

        ResponseEntity<String> exchange = restTemplate.exchange(path,
                HttpMethod.GET, httpRequest, String.class);

        assertAll(
                () -> assertNotNull(exchange),
                () -> assertEquals(HttpStatus.OK, exchange.getStatusCode())
        );
    }

    @Test
    void testErrorApi() {
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity<String> httpRequest = new HttpEntity<>(httpHeaders);

        String path = BASE_URL.concat(":" + PORT + "" + "/api/v1/sber-book/books-by-letter?start-with=С&ignore-case=false");

        assertAll(
                () -> {
                    HttpClientErrorException httpClientErrorException = assertThrows(HttpClientErrorException.class,
                            () -> restTemplate.exchange(path, HttpMethod.GET, httpRequest, String.class));
                    assertEquals(HttpStatus.NOT_FOUND, httpClientErrorException.getStatusCode());
                }
        );
    }
}
