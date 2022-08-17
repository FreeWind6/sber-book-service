package ru.sberclass.test.entity;

import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "book")
@EntityListeners(AuditingEntityListener.class)
public class Book {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(columnDefinition = "TEXT")
    public String name;

    @Column(columnDefinition = "TEXT")
    public String author;

    @Column(columnDefinition = "timestamp default now()")
    private Timestamp dateCreate = new Timestamp(System.currentTimeMillis());

    @LastModifiedDate
    private Timestamp lastUpdate;
}
