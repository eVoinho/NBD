package dataPack;

import model.Book;
import model.Client;
import model.ClientType;
import model.Rent;

import java.time.LocalDateTime;
import java.util.Arrays;

public class data {

    public static Client client1 = Client.builder()
            .firstName("Jan")
            .lastName("Kowalski")
            .clientType(ClientType.NORMAL)
            .build();

    public static Client client2 = Client.builder()
            .firstName("Kowal")
            .lastName("Janowski")
            .clientType(ClientType.PROFESSIONAL)
            .build();

    public static Client client3 = Client.builder()
            .firstName("Jan")
            .lastName("Buczek")
            .clientType(ClientType.STUDENT)
            .build();

    public static Book book1 = Book.builder()
            //.author.setFirstName()
            .title("Władca Pierścieni")
            .genre("Fantasy")
            .pageNumber(300)
            .build();
    public static Book book2 = Book.builder()
            //.author.setFirstName()
            .title("Krzyżacy")
            .genre("Powieść")
            .pageNumber(638)
            .build();
    public static Book book3 = Book.builder()
            //.author.setFirstName()
            .title("Pan Tadeusz")
            .genre("Poezja epicka")
            .pageNumber(420)
            .build();
    public static Rent rent1 = Rent.builder()
            .begin(LocalDateTime.now())
            .book(Arrays.asList((book1)))
            .build();
    public static Rent rent2 = Rent.builder()
            .begin(LocalDateTime.now())
            .book(Arrays.asList((book2)))
            .build();
    public static Rent rent3 = Rent.builder()
            .begin(LocalDateTime.now())
            .book(Arrays.asList((book3)))
            .build();
}
