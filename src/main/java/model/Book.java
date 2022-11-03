package model;

import javax.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")

public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String genre;
    private Integer pageNumber;

    @Version
    private Integer version;

    @Embedded
    private Author author;
}
