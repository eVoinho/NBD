package services;

import model.Book;
import org.bson.types.ObjectId;
import repository.BookRepository;

public class BookService extends ServiceDec<Book> {
    private final BookRepository bookRepository = new BookRepository();

    public void add(Book book) {
        bookRepository.addBook(book);
    }

    //W BookRepository w funkcji "removeBook" chcą ObjectId zamiast Integer, nie wiem czy
    //zostawiamy Inta czy zmieniamy na Objecta ¯\_(ツ)_/¯
    @Override
    public void delete(Object id) {
        bookRepository.removeBook((ObjectId) id);
    }

    @Override
    public Book get(ObjectId id) {
        return bookRepository.find(id);
    }

    @Override
    public void update(Book book) {
        bookRepository.update(book);
    }
}