package repository;

import javax.persistence.Persistence;

import com.mongodb.MongoCommandException;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import model.Client;
import model.Rent;
import org.bson.Document;

import java.time.LocalDateTime;
import java.util.List;

public class RentRepository extends Repository{


    public RentRepository() {
        initConnection();
        try {
            getLibraryDB().createCollection("rents", new CreateCollectionOptions().validationOptions( new ValidationOptions().validator(
                    Document.parse(
                            """
    {
       $jsonSchema: {
          bsonType: "object",
          required: [ "show" ],
          properties: {
             show: {
                bsonType: "$oid",
                description: "show objectId"
             },
             client: {
                bsonType: "$iod",
                description: "client objectId"
             },
             seatNumber: {
                bsonType: "int",
                minimum: 0,
                description: "must be a positive integer"
             }
             price: {
                bsonType: "float",
                minimum: 0,
                description: "must be a positive float"
             }
          }
       }
    }
                    """
                    )
            )));
        } catch(MongoCommandException ignored) {
        }

        ticketCollection = getCinemaDB().getCollection("tickets", TicketMdb.class);
        showCollection = getCinemaDB().getCollection("shows", ShowMdb.class);
    }

    public void addActiveRent(Rent rent){
        Client client = rent.getClient();
        if (client.getClientType().getMaxBooks() > client.getRents().size()){
            entityManager.getTransaction().begin();
            entityManager.persist(rent);
            entityManager.getTransaction().commit();
        }

    }

    public void addArchiveRent(Rent rent){
        entityManager.getTransaction().begin();
        entityManager.persist(rent);
        entityManager.getTransaction().commit();
    }

    public List<Rent> getRents(){
        return entityManager.createQuery("FROM Rent", Rent.class).getResultList();
    }

    public void removeRent(Rent rent) {

        Client client = rent.getClient();
        rent.setEnd(LocalDateTime.now());

        if (rent.getEnd().isAfter(rent.getBegin().plusDays(client.getClientType().getMaxBooks()))) {

            int daysAfterEndTime = rent.getEnd().getDayOfYear() - rent.getBegin().plusDays(client.getClientType().getMaxBooks()).getDayOfYear();
            rent.setTotalPenalty(client.getClientType().getPenalty() * daysAfterEndTime);
        }

        entityManager.getTransaction().begin();
        entityManager.remove(rent);
        entityManager.getTransaction().commit();
    }

}
