package repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import model.Book;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.ClassModel;
import org.bson.codecs.pojo.Conventions;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.List;

public class Repository {
    private static ConnectionString connectionString = new ConnectionString("mongodb://mikolaj236523:pB8Pec3tm2hQMCe8wYzUnoXEq24Tems8tTEPmyQywIURoq9wPdsBBKsy1gzWYHkm3qI2p9aNqleWACDbezdWlw==@mikolaj236523.mongo.cosmos.azure.com:10255/?ssl=true&retrywrites=false&replicaSet=globaldb&maxIdleTimeMS=120000&appName=@mikolaj236523@");


    //private CodecRegistry codecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
    //        .automatic(true)
    //        .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
    //        .build());
    ClassModel<Book> bookMongoClassModel =
            ClassModel.builder(Book.class).enableDiscriminator(true).build();

    private CodecRegistry codecRegistry = CodecRegistries.fromProviders(PojoCodecProvider.builder()
            .register(bookMongoClassModel)
            .automatic(true)
            .conventions(List.of(Conventions.ANNOTATION_CONVENTION))
            .build());
    private com.mongodb.client.MongoClient MongoClient;
    private MongoDatabase LibraryDB;

    protected void initConnection() {
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .codecRegistry(CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),codecRegistry))
                .build();
        MongoClient = MongoClients.create(settings);
        LibraryDB = MongoClient.getDatabase("NBD_Azure");
    }

   // public ConnectionString getConnectionString() {
   //     return connectionString;
   // }

    //public CodecRegistry getCodecRegistry() {
    //    return codecRegistry;
    //}

    public com.mongodb.client.MongoClient getMongoClient() {
        return MongoClient;
    }

    public MongoDatabase getLibraryDB() {
        return LibraryDB;
    }
}
