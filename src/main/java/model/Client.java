package model;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String personalId;
    private String firstName;
    private String lastName;
    private boolean archive;

    @OneToMany(mappedBy = "client")
    @Fetch(FetchMode.SUBSELECT)
    @BatchSize(size = 15)
    private List<Rent> rents;

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
