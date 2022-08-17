package ru.sberclass.test.controller.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberclass.test.controller.BookApi;
import ru.sberclass.test.entity.Book;
import ru.sberclass.test.service.BooksService;

import java.util.HashMap;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = BookApi.BASE_PATH)
@AllArgsConstructor
public class BookApiController implements BookApi {
    BooksService booksService;

    @Override
    public ResponseEntity<List<Book>> getBooks() {
        try {
            log.info("Request {}", BookApi.BOOKS);

            List<Book> response = booksService.findAll();

            if (response.isEmpty()) {
                log.info("Response {}: {}", BookApi.BOOKS, ResponseEntity.notFound().build());
                return ResponseEntity.notFound().build();
            }

            log.info("Response {}: {}", BookApi.BOOKS, response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getBooks: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<HashMap<Character, Integer>> getStatistic() {
        try {
            log.info("Request {}", BookApi.STATISTIC);

            HashMap<Character, Integer> response = booksService.getStatistic();

            if (response.isEmpty()) {
                log.info("Response {}: {}", BookApi.STATISTIC, ResponseEntity.notFound().build());
                return ResponseEntity.notFound().build();
            }

            log.info("Response {}: {}", BookApi.STATISTIC, response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getStatistic: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<List<Book>> getBooksByLetter(String startWith, boolean ignoreCase) {
        try {
            log.info("Request {}", BookApi.BOOKS_BY_LETTER);

            List<Book> response = booksService.getBooksByLetter(startWith, ignoreCase);

            if (response.isEmpty()) {
                log.info("Response {}: {}", BookApi.BOOKS_BY_LETTER, ResponseEntity.notFound().build());

                return ResponseEntity.notFound().build();
            }
            log.info("Response {}: {}", BookApi.BOOKS_BY_LETTER, response);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error in getBooksByLetter: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
