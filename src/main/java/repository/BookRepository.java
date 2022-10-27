package repository;

import model.Book;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class BookRepository implements AutoCloseable{

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public BookRepository(){
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Book> getBooks(){
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    public void addBook(Book book){
        entityManager.getTransaction().begin();
        entityManager.persist(book);
        entityManager.getTransaction().commit();
    }

    public void removeBook(Book book){
        entityManager.getTransaction().begin();
        entityManager.remove(book);
        entityManager.getTransaction().commit();
    }

    //public void sizeBook(Book book){
    //    entityManager.getTransaction().begin();
    //    entityManager.remove(book);
    //    entityManager.getTransaction().commit();
    //}

    public String Report(){
        entityManager.getTransaction().begin();
        List<Book> books = entityManager.createQuery("SELECT b FROM Book b").getResultList();
        entityManager.getTransaction().commit();
        StringBuilder stringBuilder = new StringBuilder();
        for (Book book : books){
            stringBuilder.append(book.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void close() {
        entityManagerFactory.close();
        entityManager.close();
    }
}
