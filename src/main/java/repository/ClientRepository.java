package repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import model.Client;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.client.MongoCollection;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

public class ClientRepository extends Repository{

    private final MongoCollection<Client> clientMongoCollection;
    public ClientRepository(){
        initConnection();
        List<String> collections = getLibraryDB().listCollectionNames().into(new ArrayList<>());
        try {
            if(!collections.contains("clients")){
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
                                              clientType: {
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
            }
        } catch(MongoCommandException ignored) {
        }

        clientMongoCollection = getLibraryDB().getCollection("clients", Client.class);
    }



    public boolean addClient(Client client){
        if(isExisting(client)) {
            return false;
        }
        clientMongoCollection.insertOne(client);
        return true;
    }

    public Client removeClient(ObjectId personalId) {
        Bson filter = eq("_id", personalId);
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


    public Client find(ObjectId personalId) {
        Bson filter = eq("personalId", personalId);
        return clientMongoCollection.find(filter).first();
    }

}
