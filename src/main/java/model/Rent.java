package model;

import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rent {

    private UUID rentId;
    private LocalDate begin;
    private LocalDate end;
    private UUID clientId;
    private UUID bookId;


}
