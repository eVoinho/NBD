package exception;

public class RedisConnectNotFoundException extends RuntimeException {
    public RedisConnectNotFoundException(String message) {
        super(message);
    }
}
