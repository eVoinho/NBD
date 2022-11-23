package model;
import javax.persistence.*;
import lombok.*;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.bson.codecs.pojo.annotations.BsonCreator;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Client {
    public enum Type {NORMAL, STUDENT, PROFESSIONAL}
    @BsonId
    private ObjectId personalId;
    @BsonProperty("firstName")
    private String firstName;
    @BsonProperty("lastName")
    private String lastName;

    @BsonProperty("rents")
    private List<Rent> rents;

    @Enumerated(EnumType.STRING)
    @BsonProperty("clientType")
    private Type clientType;

    public void addRent(Rent rent) {
        if (rents == null) {
            rents = new ArrayList<>();
        }
        rents.add(rent);
        rent.setClient(this);
    }

    public void removeRent(Rent rent) {
        if (rents != null) {
            rents.remove(rent);
            rent.setClient(null);
        }
    }

    @BsonCreator
    public Client(@BsonId ObjectId personalId,
                  @BsonProperty("firstName") String firstName,
                  @BsonProperty("lastName") String lastName,
                  @BsonProperty("clientType") Type clientType
                  ) {
        this.personalId = personalId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientType = clientType;
    }

    public Client(String firstName, String lastName, Type clientType) {
        this.personalId = new ObjectId();
        this.firstName = firstName;
        this.lastName = lastName;
        this.clientType = clientType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        return new EqualsBuilder().append(personalId, client.personalId).append(firstName, client.firstName).append(lastName, client.lastName).append(rents, client.rents).append(clientType, client.clientType).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(personalId).append(firstName).append(lastName).append(rents).append(clientType).toHashCode();
    }

    @Override
    public String toString() {
        return "Client{" +
                "personalId=" + personalId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", rents=" + rents +
                ", clientType=" + clientType +
                '}';
    }
}
