package repository;

import com.mongodb.MongoCommandException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.ValidationOptions;
import com.mongodb.client.result.UpdateResult;
import model.Book;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.or;

public class BookRepository extends Repository{

    public BookRepository(){
        initConnection();
        List<String> collections = getLibraryDB().listCollectionNames().into(new ArrayList<>());
        try {
            if(!collections.contains("books")){
            getLibraryDB().createCollection("books", new CreateCollectionOptions().validationOptions( new ValidationOptions().validator(
                    Document.parse(
                            """
                                    {
                                       $jsonSchema: {
                                          bsonType: "object",
                                          properties: {
                                             title: {
                                                bsonType: "string",
                                                description: "must be a string"
                                             },
                                             genre: {
                                                bsonType: "string",
                                                description: "must be a string"
                                             }
                                             pageNumber: {
                                                bsonType: "int",
                                                minimum: 1,
                                                description: "must be an integer"
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
        bookMongoCollection = getLibraryDB().getCollection("books", Book.class);
    }

    private final MongoCollection<Book> bookMongoCollection;

    public boolean addBook(Book book){
        if(isExisting(book)) {
            return false;
        }
        bookMongoCollection.insertOne(book);
        return true;
    }

    public Book removeBook(ObjectId id){
        Bson filter = eq("_id", id);
        return bookMongoCollection.findOneAndDelete(filter);
    }

    private boolean isExisting(Book book) {
        Bson filter;
        filter = eq("_id", book.getId());

        ArrayList<Book> ls = bookMongoCollection.find(filter).into(new ArrayList<>());
        return !ls.isEmpty();
    }
    public void drop()
    {
        bookMongoCollection.drop();
    }


    public ArrayList<Book> findAll() {
        return bookMongoCollection.find().into(new ArrayList<> ());
    }

    public Book find(ObjectId id) {
        Bson filter = eq("id", id);

        //return bookMongoCollection.find(filter, Book.class).into(new ArrayList<> ());
        return bookMongoCollection.find(filter).first();
    }

    public Book update(Book book) {
        Bson filter = eq("id", book.getId());
        UpdateResult updateResult = bookMongoCollection.replaceOne(filter, book);

        if (updateResult.getModifiedCount() == 1) {
            return book;
        }
        return null;
    }

    public Book updateOne(ObjectId id, Bson updateOperation) {
        Bson filter = eq("id", id);
        return bookMongoCollection.findOneAndUpdate(filter, updateOperation);
    }

    public UpdateResult updateMany(Bson filter, Bson updateOperation) {
        return bookMongoCollection.updateMany(filter, updateOperation);
    }
}
