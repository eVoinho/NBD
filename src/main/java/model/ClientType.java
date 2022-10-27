package model;

import jakarta.persistence.Entity;
import lombok.Getter;

//@Entity
@Getter
public class ClientType {

    NORMAL("Normal", 3, 14, 1),
    STUDENT("Student", 5, 21, 1),
    PROFESSIONAL("Professional", 7, 30, 0.5);
    private String typeName;
    private Integer maxBooks;
    private Integer maxRentLength;
    private Double penalty;

    ClientType(String typeName, int maxBooks, int maxRentLength, double penalty){
        this.typeName = typeName;
        this.maxBooks = maxBooks;
        this. maxRentLength = maxRentLength;
        this.penalty = penalty;
    }
}
