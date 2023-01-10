package model;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private UUID clientId;

    private String firstName;

    private String lastName;

}
