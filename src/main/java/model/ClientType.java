package model;

//import javax.persistence.Entity;
import javax.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

//@Entity
@Getter
@Setter
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor

@Inheritance(strategy = InheritanceType.JOINED)

public class ClientType {;

//    NORMAL("Normal", 3, 14, 1),
//    STUDENT("Student", 5, 21, 1),
//    PROFESSIONAL("Professional", 7, 30, 0.5);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer maxBooks;
    private Integer maxRentLength;
    private Double penalty;

//    ClientType(String typeName, int maxBooks, int maxRentLength, double penalty){
//        this.typeName = typeName;
//        this.maxBooks = maxBooks;
//        this. maxRentLength = maxRentLength;
//        this.penalty = penalty;
//    }
}
