package repository;

import static com.datastax.oss.driver.api.querybuilder.SchemaBuilder.createKeyspace;
import static services.CasConstIdentifiers.CLIENT_TABLE_ID;
import static services.CasConstIdentifiers.BOOK_TABLE_ID;

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.type.DataTypes;
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder;
import com.datastax.oss.driver.api.querybuilder.schema.CreateKeyspace;

import java.net.InetSocketAddress;

public abstract class Repository implements AutoCloseable {
   protected CqlSession session;

   public Repository() {
      //TODO: ZMIANY ARGUMENTÓW PO ZROBIENIU DOCKERA
      this.session = CqlSession.builder()
              .addContactPoint(new InetSocketAddress("127.0.0.1", 9042))
              .withLocalDatacenter("datacenter1")
              .withAuthCredentials("cassandra", "cassandra").build();

      //TODO: ZMIANY ARGUMENTÓW PO ZROBIENIU DOCKERA
      CreateKeyspace keyspace = createKeyspace(CqlIdentifier.fromCql("library"))
              .ifNotExists()
              .withSimpleStrategy(2)
              .withDurableWrites(true);
      SimpleStatement createKeyspace = keyspace.build();
      session.execute(createKeyspace);

      session.execute("USE library");

      SimpleStatement createClientTable = SchemaBuilder.createTable(CLIENT_TABLE_ID)
              .ifNotExists()
              .withPartitionKey(CqlIdentifier.fromCql("clientId"), DataTypes.UUID)
              .withColumn(CqlIdentifier.fromCql("firstName"), DataTypes.TEXT)
              .withColumn(CqlIdentifier.fromCql("lastName"), DataTypes.TEXT)
              .build();

      session.execute(createClientTable);

      SimpleStatement createBookTable = SchemaBuilder.createTable(CLIENT_TABLE_ID)
              .ifNotExists()
              .withPartitionKey(CqlIdentifier.fromCql("bookId"), DataTypes.UUID)
              .withColumn(CqlIdentifier.fromCql("title"), DataTypes.TEXT)
              .withColumn(CqlIdentifier.fromCql("genre"), DataTypes.TEXT)
              .withColumn(CqlIdentifier.fromCql("author"), DataTypes.TEXT)
              .withColumn(CqlIdentifier.fromCql("pageNumber"), DataTypes.INT)
              .build();

      session.execute(createBookTable);

      SimpleStatement createRentsByBook =
              SchemaBuilder.createTable("rentsByBook")
                      .ifNotExists()
                      .withPartitionKey(CqlIdentifier.fromCql("title"), DataTypes.TEXT)
                      .withClusteringColumn(CqlIdentifier.fromCql("rentId"), DataTypes.UUID)
                      .withColumn(CqlIdentifier.fromCql("clientId"), DataTypes.UUID)
                      .withColumn(CqlIdentifier.fromCql("bookId"), DataTypes.UUID)
                      .withColumn(CqlIdentifier.fromCql("begin"), DataTypes.DATE)
                      .withColumn(CqlIdentifier.fromCql("end"), DataTypes.DATE)
                      .build();

      session.execute(createRentsByBook);

      SimpleStatement createRentsByClient = SchemaBuilder.createTable("rentsByClient")
              .ifNotExists()
              .withPartitionKey(CqlIdentifier.fromCql("name"), DataTypes.TEXT)
              .withClusteringColumn(CqlIdentifier.fromCql("rentId"), DataTypes.UUID)
              .withColumn(CqlIdentifier.fromCql("clientId"), DataTypes.UUID)
              .withColumn(CqlIdentifier.fromCql("bookId"), DataTypes.UUID)
              .withColumn(CqlIdentifier.fromCql("begin"), DataTypes.DATE)
              .withColumn(CqlIdentifier.fromCql("end"), DataTypes.DATE)
              .build();

      session.execute(createRentsByClient);
   }



   }

