package repository;

import javax.persistence.Persistence;

import com.mongodb.MongoCommandException;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Updates;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.result.UpdateResult;
import model.Book;
import model.Client;
import model.Rent;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class RentRepository extends Repository{


    public RentRepository() {
        initConnection();
        List<String> collections = getLibraryDB().listCollectionNames().into(new ArrayList<>());
        try {
            if(!collections.contains("rents")){
            getLibraryDB().createCollection("rents", new CreateCollectionOptions().validationOptions( new ValidationOptions().validator(
                    Document.parse(
                            """
                        {
                           $jsonSchema: {
                             bsonType: "object",
                             required: [ "client", "books" ],
                             properties: {
                               begintime: {
                                 bsonType: "date",
                                 description: "must be a date"
                               },
                               client: {
                                 bsonType: "object",
                                 description: "must be a object"
                               },
                               books: {
                                 bsonType: "array",
                                 description: "must be a array"
                               }
                             }
                        }
                    """
                    )
            )));}
        } catch(MongoCommandException ignored) {
        }

        rentMongoCollection = getLibraryDB().getCollection("rents", Rent.class);
        bookMongoCollection = getLibraryDB().getCollection("books", Book.class);
    }

    private final MongoCollection<Rent> rentMongoCollection;
    private final MongoCollection<Book> bookMongoCollection;

    public boolean addRent(Rent rent) {
        try (ClientSession session = getMongoClient().startSession()) {
            session.startTransaction();
            transactionBody(rent);
            session.commitTransaction();
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }

    private void transactionBody(Rent rent) {
        Book book = getShowFromDatabase(rent);
        if (book == null)
        {
            throw new RuntimeException();
        }
        updateDataBase(rent, book);
    }


    private Book getShowFromDatabase(Rent rent) {
        Bson filter = eq("_id", rent.getBook());
        ArrayList<Book> ls = bookMongoCollection.find(filter, Book.class).into(new ArrayList<> ());
        Book sh;
        if(!ls.isEmpty())
        {
            sh = ls.get(0);
            return sh;
        }
        return null;
    }

    private void updateDataBase(Rent rent, Book book) {
        Bson fil = eq("_id", book.getId());
        Bson update = Updates.inc("quantity", -1);
        bookMongoCollection.findOneAndUpdate(fil, update);
        rentMongoCollection.insertOne(rent);
    }

    private boolean isExisting(Rent rent) {
        ArrayList<Rent> ls = find(rent.getId());
        return !ls.isEmpty();
    }

    public Rent removeRent(ObjectId id) {

        Rent rent;
        Bson rentFilter = eq("_id", id);
        ClientSession session = getMongoClient().startSession();

        session.startTransaction();
        rent = rentMongoCollection.findOneAndDelete(rentFilter);
        Bson update = Updates.inc("quantity", 1);
        assert rent != null;
        Bson showFilter = eq("_id", rent.getBook());
        bookMongoCollection.updateOne(showFilter, update);
        session.commitTransaction();

        return rent;
    }

    public void drop()
    {
        rentMongoCollection.drop();
    }

    public ArrayList<Rent> findAll() {
        return rentMongoCollection.find().into(new ArrayList<> ());
    }

    public ArrayList<Rent> find(Integer id) {
        Bson filter = eq("_id", id);

        return rentMongoCollection.find(filter).into(new ArrayList<> ());
    }

    public Rent updateOne(ObjectId id, Bson updateOperation) {
        Bson filter = eq("_id", id);
        return rentMongoCollection.findOneAndUpdate(filter, updateOperation);
    }

    public UpdateResult updateMany(Bson filter, Bson updateOperation) {
        return rentMongoCollection.updateMany(filter, updateOperation);
    }

}
