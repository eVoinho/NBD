package services;

import com.google.gson.Gson;
import exception.RedisConnectNotFoundException;
import java.io.FileInputStream;
import java.util.Properties;
import model.Book;
import org.bson.types.ObjectId;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisCacheService extends ServiceDec<Book> {
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //Pozmieniałem pod nasze booki, komentarzy i wartości nie zmieniałem. Nie dodawałem tych ich redisConfigFile i redis (properties) @@@@@@@@@@@@@@@@@@@@@@@@@@@@
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    private final BookService bookService = new BookService();
    private JedisPool jedisPool;
    private final Gson gson = new Gson();

    public RedisCacheService() {
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/main/java/service/redis.properties"));
            jedisPool = new JedisPool(properties.getProperty("redis.host"), Integer.parseInt(properties.getProperty("redis.port")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Jedis jedis;

    @Override
    public void add(Book book) {

        System.out
                .println("Adding new book do db and Redis");

        try {
            jedis = jedisPool.getResource();
            String key = String.valueOf(book.getId());
            String value = gson.toJson(book);
            jedis.set(key, value);
            jedis.expire(key, 60);
            System.out.println( book.getTitle() + " added to redis");
            bookService.add(book);
        } catch (Exception e) {
            releaseJedis(jedis);
            System.out.println("Connection to redis lost, add only to db");
            bookService.add(book);
        } finally {
            releaseJedis(jedis);
        }
    }

    private void releaseJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    @Override
    public Book get(ObjectId id) {
        try {
            jedis = jedisPool.getResource();
            String clientJson = jedis.get(String.valueOf(id));

            if (clientJson != null) {
                return gson.fromJson(clientJson, Book.class);
            } else {
                return bookService.get(id);
            }

        } catch (Exception e) {
            releaseJedis(jedis);
            System.out.println("Read from db because connection to Redis was lost");
            return bookService.get(id);
        } finally {
            releaseJedis(jedis);
        }
    }

    @Override
    public void delete(Object id) {


        try {
            jedis = jedisPool.getResource();
            jedis.del(String.valueOf(id));
            System.out.println(id +" deleted from redis");
            bookService.delete(id);
        } catch (Exception e) {
            releaseJedis(jedis);
            throw new RedisConnectNotFoundException("Cannot connect with Redis");
        } finally {
            releaseJedis(jedis);
        }
    }

    @Override
    public void update(Book book) {
        try {
            jedis = jedisPool.getResource();
            String key = String.valueOf(book.getId());
            String value = gson.toJson(book);
            jedis.set(key, value);
            jedis.expire(key, 60);
            System.out.println(book.getTitle() + " updated in redis");
            bookService.update(book);
        } catch (Exception e) {
            releaseJedis(jedis);
            System.out.println("Changes added only to db");
            bookService.update(book);
        } finally {
            releaseJedis(jedis);
        }
    }
}
