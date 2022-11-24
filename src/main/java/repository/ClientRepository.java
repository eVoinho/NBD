package repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import model.Client;
import org.bson.Document;

import java.util.ArrayList;
import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class ClientRepository extends Repository{

    public ClientRepository(){
        initConnection();
        try {
            getLibraryDB().createCollection("clients", new CreateCollectionOptions().validationOptions( new ValidationOptions().validator(
                    Document.parse(
                            """
 {
    $jsonSchema: {
       bsonType: "object",
       required: [ "firstName", "lastName" ],
       properties: {
          firstName: {
             bsonType: "string",
             description: "must be a string"
          },
          lastName: {
             bsonType: "string",
             description: "must be a string"
          },
          clienType: {
             bsonType: "string",
             description: "must be a string"
          },
          rents: {
             bsonType: "array",
             description: "must be an array"
          }
       }
    }
 }
                 """
                    )
            )));
        } catch(MongoCommandException ignored) {
        }

        clientMongoCollection = getLibraryDB().getCollection("clients", Client.class);
    }

    private final MongoCollection<Client> clientMongoCollection;

    public boolean addClient(Client client){
        if(isExisting(client)) {
            return false;
        }
        clientMongoCollection.insertOne(client);
        return true;
    }

    public Client removeClient(ObjectId personalId) {
        Bson filter = eq("personalId", personalId);
        return clientMongoCollection.findOneAndDelete(filter);
    }
    private boolean isExisting(Client client) {
        Bson filter;
        filter = eq("personalId", client.getPersonalId());

        ArrayList<Client> ls = clientMongoCollection.find(filter).into(new ArrayList<>());
        return !ls.isEmpty();
        }

    public Client update(ObjectId personalId, String key, String value, Boolean many) {

        Bson filter = eq("personalId", personalId);
        Bson setUpdate = set(key, value);
        return clientMongoCollection.findOneAndUpdate(filter, setUpdate);
    }
    public void drop()
    {
        clientMongoCollection.drop();
    }

    public ArrayList<Client> findAll() {
        return clientMongoCollection.find().into(new ArrayList<> ());
    }

    public ArrayList<Client> find(ObjectId personalId) {
        Bson filter = eq("personalId", personalId);

        return clientMongoCollection.find(filter, Client.class).into(new ArrayList<> ());
    }

    }
