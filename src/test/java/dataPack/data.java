package dataPack;

import model.Client;
import model.ClientType;

public class data {

    public static Client client = Client.builder()
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
}

