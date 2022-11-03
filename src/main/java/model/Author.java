package model;

import javax.persistence.Embeddable;
import lombok.*;


@Embeddable
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author {
    private String firstName;
    private String lastName;
}
