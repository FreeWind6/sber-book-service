package ru.sberclass.test.controller.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sberclass.test.controller.BookApi;
import ru.sberclass.test.controller.BookSaveApi;
import ru.sberclass.test.entity.Book;
import ru.sberclass.test.model.save.Response;
import ru.sberclass.test.service.BooksService;

import java.util.UUID;

@RestController
@Slf4j
@RequestMapping(value = BookApi.BASE_PATH)
@AllArgsConstructor
@ConditionalOnProperty(name = "app.save.enabled", havingValue = "true")
public class BookSaveApiController implements BookSaveApi {
    BooksService booksService;

    @Override
    public ResponseEntity<Response> save(Book book) {
        try {
            log.info("Request {}, Body: {}", BookApi.SAVE, book);
            UUID id = booksService.save(book);
            ResponseEntity<Response> reponse = ResponseEntity.ok(Response.builder()
                    .uuid(id)
                    .build());
            log.info("Response {}, {}", BookApi.SAVE, reponse);
            return reponse;
        } catch (Exception e) {
            log.error("Error in save: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
