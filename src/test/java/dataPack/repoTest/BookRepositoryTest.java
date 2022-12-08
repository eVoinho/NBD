package dataPack.repoTest;
import com.mongodb.MongoWriteException;
import model.Client;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;

import static org.assertj.core.api.Assertions.assertThat;

import dataPack.data;
import model.Book;
import org.junit.jupiter.api.Test;
import repository.BookRepository;
import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import java.util.ArrayList;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookRepositoryTest {

    private BookRepository bookRepository = new BookRepository();

    @BeforeEach
    void droprepo() {
        bookRepository.drop();
    }

    @Test
    void addBookTest() {
        bookRepository.addBook(data.book1);
        bookRepository.addBook(data.book2);
        ArrayList<Book> ls = bookRepository.findAll();
        assertEquals(2, ls.size());
    }

    @Test
    void addExistingBookTest() {
        bookRepository.drop();
        bookRepository.addBook(data.book1);
        bookRepository.addBook(data.book2);
        assertThrows(MongoWriteException.class, () -> bookRepository.addBook(data.book1));
        ArrayList<Book> ls = bookRepository.findAll();
        assertEquals(2, ls.size());
    }

    @Test
    void dropTest() {
        bookRepository.drop();
        ArrayList<Book> ls = bookRepository.findAll();
        assertEquals(0, ls.size());
    }

    @Test
    void removeBookTest() {
        bookRepository.drop();
        ObjectId id = data.book1.getId();

        bookRepository.addBook(data.book1);
        bookRepository.addBook(data.book2);

        Book removed = bookRepository.removeBook(id);
        assertEquals(removed, data.book1);

        ArrayList<Book> ls = bookRepository.findAll();
        assertEquals(1, ls.size());
    }

    @Test
    void findAllTest() {
        bookRepository.drop();

        bookRepository.addBook(data.book1);
        bookRepository.addBook(data.book2);

        ArrayList<Book> ls = bookRepository.findAll();
        assertEquals(data.book1, ls.get(0));
        assertEquals(data.book2, ls.get(1));
    }
}
