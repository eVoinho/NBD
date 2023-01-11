package repository;


import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.mongodb.client.result.UpdateResult;
import model.Book;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import java.util.ArrayList;
import java.util.UUID;

public class BookRepository extends Repository{

    public BookRepository(){
        super();
    }


    public void addBook(Book book){
        session.execute("INSERT INTO book (bookId, title, genre, author, pageNumber) VALUES (?, ?, ? ,?, ?)",
                book.getBookId(), book.getTitle(), book.getGenre(), book.getAuthor(), book.getPageNumber());
    }

    public void removeBook(UUID bookId){
        session.execute("DELETE FROM book WHERE ID = ?", bookId);
    }

    public void update(Book book) {
        session.execute("UPDATE book SET title = ?, author = ?, genre = ?, pageNumber = ?",
                book.getTitle(), book.getAuthor(), book.getGenre(), book.getPageNumber());
    }

    public ResultSet getBook(UUID bookId) {
        return session.execute("SELECT * FROM book WHERE bookId = ?", bookId);
    }

    public ResultSet findAll() {
        ResultSet resultSet = session.execute("SELECT * FROM book");

        for(var row : resultSet) {
            System.out.println(row.getUuid("bookId") + " " + row.getString("title") + " " +
                    row.getString("author") + " " + row.getString("genre") + " " +
                    row.getInt("pageNumber"));
        }

        return resultSet;
    }

    @Override
    public void close() throws Exception {
        session.close();
    }
}
