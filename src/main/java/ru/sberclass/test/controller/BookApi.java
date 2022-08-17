package ru.sberclass.test.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sberclass.test.entity.Book;

import java.util.HashMap;
import java.util.List;

@OpenAPIDefinition(
        info = @Info(
                title = "API Сервиса книг",
                version = "1.0",
                description = "API Сервиса книг"
        )
)

@Tag(name = "sber-book")
public interface BookApi {
    String BASE_PATH = "/api/v1/sber-book";
    String BOOKS = "/books";
    String STATISTIC = "/statistic";
    String BOOKS_BY_LETTER = "/books-by-letter";

    @Operation(
            summary = "Получение всех кних",
            operationId = "getBooks",
            description = "Получение всех кних"
    )
    @GetMapping(BOOKS)
    ResponseEntity<List<Book>> getBooks();

    @Operation(
            summary = "Получение статистики (игнорирование регистра)",
            operationId = "getStatistic",
            description = "Получение статистики (игнорирование регистра)"
    )
    @GetMapping(STATISTIC)
    ResponseEntity<HashMap<Character, Integer>> getStatistic();

    @Operation(
            summary = "Получение книги по параметрам",
            operationId = "getBooksByLetter",
            description = "Получение книги по параметрам"
    )
    @GetMapping(BOOKS_BY_LETTER)
    ResponseEntity<List<Book>> getBooksByLetter(@RequestParam("start-with") String startWith,
                                                @RequestParam("ignore-case") boolean ignoreCase);
}
