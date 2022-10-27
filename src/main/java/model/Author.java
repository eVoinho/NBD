package model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
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
