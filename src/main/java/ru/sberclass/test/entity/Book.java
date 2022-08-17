package ru.sberclass.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    @JsonIgnore
    private UUID id;

    @Column(columnDefinition = "TEXT")
    @NotNull
    public String name;

    @Column(columnDefinition = "TEXT")
    @NotNull
    public String author;

    @Column(columnDefinition = "timestamp default now()")
    @JsonIgnore
    private Timestamp dateCreate = new Timestamp(System.currentTimeMillis());

    @LastModifiedDate
    @JsonIgnore
    private Timestamp lastUpdate;
}
