package model;


import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;


@SuperBuilder
@NoArgsConstructor

public class Normal extends ClientType {
    private String typeName = "Normal";
}
