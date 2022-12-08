package dataPack.repoTest;


import static org.junit.jupiter.api.Assertions.*;

import com.mongodb.MongoWriteException;
import dataPack.data;
import model.Client;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;

import java.util.ArrayList;

class ClientRepositoryTest {

    private ClientRepository clientRepository = new ClientRepository();

    @BeforeEach
    void droprepo() {
        clientRepository.drop();
    }
    @Test
    void addClientTest() {

        clientRepository.addClient(data.client);
        clientRepository.addClient(data.client2);
        ArrayList<Client> ls = clientRepository.findAll();
        assertEquals(2, ls.size());
    }

    @Test
    void addExistingClientTest() {
        clientRepository.drop();
        clientRepository.addClient(data.client);
        clientRepository.addClient(data.client2);
        assertThrows(MongoWriteException.class, () -> clientRepository.addClient(data.client));
        ArrayList<Client> ls = clientRepository.findAll();
        assertEquals(2, ls.size());
    }

    @Test
    void dropTest() {
        clientRepository.drop();
        ArrayList<Client> ls = clientRepository.findAll();
        assertEquals(0, ls.size());
    }
    @Test
    void removeClientTest() {
        clientRepository.drop();
        ObjectId id = data.client.getPersonalId();

        clientRepository.addClient(data.client);
        clientRepository.addClient(data.client2);

        Client removed = clientRepository.removeClient(id);
        assertEquals(removed, data.client);

        ArrayList<Client> ls = clientRepository.findAll();
        assertEquals(1, ls.size());
    }

    @Test
    void findAllTest() {
        clientRepository.drop();

        clientRepository.addClient(data.client);
        clientRepository.addClient(data.client2);

        ArrayList<Client> ls = clientRepository.findAll();
        assertEquals(data.client, ls.get(0));
        assertEquals(data.client2, ls.get(1));
    }


}