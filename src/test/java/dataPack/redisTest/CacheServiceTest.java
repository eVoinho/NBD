package dataPack.redisTest;

import org.junit.jupiter.api.Test;
import model.Book;
import services.RedisCacheService;

import static dataPack.data.book2;
import static dataPack.data.book3;

public class CacheServiceTest {
    @Test
    void secondReadFromRedisCacheTest() {
        RedisCacheService bookService = new RedisCacheService();

        Book book = bookService.get(book3.getId());
        if (book == null) {
            System.out.println("Book neither in redis cache nor mongodb");
        }
        bookService.add(book3);
        System.out.println("Book 'read' from redis cache");
        bookService.get(book3.getId());
    }

    @Test
    void readFromMongodbWhenLostConnectionToRedis() {
        RedisCacheService bookService = new RedisCacheService();

        bookService.add(book2);
        System.out.println("Read from mongodb when connection is lost");
        bookService.get(book2.getId());
    }
}
