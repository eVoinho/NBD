package dataPack.repoTest;

import static org.assertj.core.api.Assertions.assertThat;

import model.Book;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import repository.BookRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import static dataPack.data.book1;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

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

    @Test
    void optimisticLockExceptionTest() {

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        entityManager1.getTransaction().begin();
        entityManager2.getTransaction().begin();

        entityManager1.persist(book1);
        entityManager1.getTransaction().commit();

        Book bookTest1 = entityManager1.find(Book.class, book1.getId());
        Book bookTest2 = entityManager2.find(Book.class, book1.getId());

        entityManager1.getTransaction().begin();
        bookTest1.setGenre("Przygodowa");
        entityManager1.getTransaction().commit();

        bookTest2.setGenre("Powieść");

        assertThatThrownBy(() -> entityManager2.getTransaction().commit()).isInstanceOf(
                RollbackException.class);

        entityManager1.close();
        entityManager2.close();
    }
}
