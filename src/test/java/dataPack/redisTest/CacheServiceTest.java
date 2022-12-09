package dataPack.redisTest;

import org.junit.jupiter.api.Test;
import model.Book;
import services.RedisCacheService;
import org.junit.jupiter.api.Assertions.*;

import static dataPack.data.book2;
import static dataPack.data.book3;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CacheServiceTest {
    @Test
    void secondReadFromRedisCacheTest() {
        RedisCacheService bookService = new RedisCacheService();
        bookService.ping();
        Book book = bookService.get(book3.getId());
        if (book == null) {
            System.out.println("Book neither in redis cache nor mongodb");
        }
        bookService.add(book3);
        System.out.println("Book 'read' from redis cache");
        System.out.println(bookService.get((book3.getId())));
        System.out.println((book3.getId()));
        assertNotNull(bookService.get(book3.getId()));
    }

//    @Test
//    void readFromMongodbWhenLostConnectionToRedis() {
//        RedisCacheService bookService = new RedisCacheService();
//
//        bookService.add(book2);
//        System.out.println("Read from mongodb when connection is lost");
//        bookService.get(book2.getId());
//    }
}
