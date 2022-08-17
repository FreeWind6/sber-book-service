package ru.sberclass.test.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.sberclass.test.entity.Book;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT count(b) from Book b where b.name like :str%")
    int startWithGetCount(String str);

    @Query("SELECT b from Book b where b.name like :str%")
    List<Book> startWithGetList(String str);
}
