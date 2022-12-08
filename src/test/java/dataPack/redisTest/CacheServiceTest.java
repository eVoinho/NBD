package dataPack.redisTest;

import org.junit.jupiter.api.Test;
import model.Book;

import static dataPack.data.book2;
import static dataPack.data.book3;

public class CacheServiceTest {
    @Test
    void second_read_from_redisCache_instead_of_database() {
        CacheService bookService = new CacheService();

        System.out.println("odczyt gdy przedmiot nie jest ani w redis cache ani w mongodb");
        Book book = bookService.get(book3.getId());
        if (book == null) {
            System.out.println("Nie ma w redis cache i w mongodb");
        }
        bookService.add(book3);
        System.out.println("Odczyt z redis cache zamiast bazy danych");
        bookService.get(book3.getId());
    }

    @Test
    void read_from_mongodb_when_lost_connection_to_redis() {
        CacheService bookService = new CacheService();

        book.add(book2);
        System.out.println("Odczyt z mongodb, gdy utracono polaczenie");
        book.get(book2.getId());
    }
}
