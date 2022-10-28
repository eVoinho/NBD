package model;

import jakarta.persistence.*;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDateTime begin;
    private LocalDateTime end;
    private Double totalPenalty;

    @ManyToOne(fetch = FetchType.LAZY)
    @Cascade(CascadeType.ALL)
    private Client client;

    @OneToMany
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 15)
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private List<Book> book;
}