package ru.sberclass.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.sberclass.test.entity.Book;
import ru.sberclass.test.model.save.Response;

import javax.validation.Valid;


@Tag(name = "sber-book")
public interface BookSaveApi {
    String SAVE = "/save";

    @Operation(
            summary = "Сохранить книгу",
            operationId = "save",
            description = "Сохранить книгу"
    )
    @PostMapping(SAVE)
    ResponseEntity<Response> save(@RequestBody @Valid Book book);
}
