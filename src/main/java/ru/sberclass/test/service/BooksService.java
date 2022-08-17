package ru.sberclass.test.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberclass.test.entity.Book;
import ru.sberclass.test.repositories.BookRepository;

import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class BooksService {
    private final BookRepository bookRepository;

    /**
     * @return Список всех книг
     */
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * @return Возващает статистику книг по буквенно. Регистр игнорируется.
     * Например: Буква А - 10 книг
     */
    @Transactional(readOnly = true)
    public HashMap<Character, Integer> getStatistic() {
        HashMap<Character, Integer> response = new HashMap<>();
        for (char ch = 'А'; ch <= 'Я'; ch++) {
            response.put(ch,
                    bookRepository.startWithGetCount(String.valueOf(ch).toUpperCase()) +
                            bookRepository.startWithGetCount(String.valueOf(ch).toLowerCase()));
        }

        // Буквы Ё нет. Добавляем вручную
        response.put("Ё".toCharArray()[0],
                bookRepository.startWithGetCount("Ё") +
                        bookRepository.startWithGetCount("ё"));

        return response;
    }

    /**
     * @param startWith  стартовая буква
     * @param ignoreCase игнорирование регистра буквы
     * @return список книг
     */
    @Transactional(readOnly = true)
    public List<Book> getBooksByLetter(String startWith, boolean ignoreCase) {
        if (ignoreCase) {
            List<Book> booksUpperCase = bookRepository.startWithGetList(startWith.toUpperCase());
            List<Book> booksLowerCase = bookRepository.startWithGetList(startWith.toLowerCase());
            booksUpperCase.addAll(booksLowerCase);
            return booksUpperCase;

        }
        return bookRepository.startWithGetList(startWith);
    }
}
