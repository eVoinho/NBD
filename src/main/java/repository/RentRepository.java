package repository;



import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import model.Rent;
import java.util.UUID;



public class RentRepository extends Repository{


    public RentRepository() {
        super();
    }



    public void addRent(Rent rent, String clientName, String bookTitle) {
        BatchStatement batch = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(SimpleStatement.newInstance("INSERT INTO rentsByClient (name, clientId, rentId, bookId, begin, end) VALUES (?, ?, ?, ?, ?)",
                        clientName, rent.getClientId(), rent.getRentId(), rent.getBookId(), rent.getBegin(), rent.getEnd()))
                .addStatement(SimpleStatement.newInstance("INSERT INTO rentsByBook (title, clientId, rentId, bookId, begin, end) VALUES (?, ?, ?, ?, ?)",
                        bookTitle, rent.getClientId(), rent.getRentId(), rent.getBookId(), rent.getBegin(), rent.getEnd()))
                .build();

        session.execute(batch);
    }

    public void removeRent(UUID rentId) {
        BatchStatement batch = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(SimpleStatement.newInstance(
                        "DELETE FROM rentsByClient WHERE rent_id = ?", rentId))
                .addStatement(SimpleStatement.newInstance(
                        "DELETE FROM rentsByBook WHERE rent_id = ?", rentId))
                .build();
    }

    public void update(Rent rent, String clientName, String bookTitle) {
        BatchStatement batch = BatchStatement.builder(BatchType.LOGGED)
                .addStatement(SimpleStatement.newInstance(
                        "UPDATE rentsByClient SET name = ?, begin = ?, end = ? WHERE rent_id = ?",
                        clientName, rent.getBegin(),
                        rent.getEnd(), rent.getRentId()))
                .addStatement(SimpleStatement.newInstance(
                        "UPDATE rentsByBook SET title = ?, begin = ?, end = ? WHERE rent_id = ?",
                        bookTitle, rent.getBegin(),
                        rent.getEnd(), rent.getRentId()))
                .build();

        session.execute(batch);
    }

    public ResultSet getRentsByClient(UUID personalID) {
        return session.execute("SELECT * FROM rentsByClient WHERE clientID = ? ALLOW FILTERING", personalID);
    }

    public ResultSet getRentsByBook(UUID bookId) {
        return session.execute("SELECT * FROM rentsByBook WHERE bookId = ? ALLOW FILTERING", bookId);
    }

    public ResultSet getAllRentsByClient() {
        return session.execute("SELECT * FROM rentsByClient");
    }

    public ResultSet getAllRentsByBook() {
        return session.execute("SELECT * FROM rentsByBook");
    }

    @Override
    public void close() throws Exception {
        session.close();
    }




}
