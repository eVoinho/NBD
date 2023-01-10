package model;

import lombok.*;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private UUID bookId;
    private String title;
    private String genre;
    private String author;
    private Integer pageNumber;


}