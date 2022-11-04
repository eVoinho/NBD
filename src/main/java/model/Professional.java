package model;


import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;

@SuperBuilder
@NoArgsConstructor

public class Professional extends ClientType{
    private String typeName = "Professional";
}
