package dataPack.repoTest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import repository.BookRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static dataPack.data.book1;

public class BookRepositoryTest {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");


    @AfterAll
    static void close() {
        entityManagerFactory.close();
    }

    @Test
    void addBookTest() {
        try (BookRepository bookRepository = new BookRepository()) {
            bookRepository.addBook(book1);
            assertThat(bookRepository.getBooks()).contains(book1);
        }
    }

    @Test
    void removeBookTest() {
        try (BookRepository bookRepository = new BookRepository()) {
            bookRepository.addBook(book1);
            assertThat(bookRepository.getBooks()).contains(book1);
            bookRepository.removeBook(book1);
            assertThat(bookRepository.getBooks()).doesNotContain(book1);
        }
    }
}
