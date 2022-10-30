package dataPack.repoTest;

import static dataPack.data.client1;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

class ClientRepositoryTest {
    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    @AfterAll
    static void close() {
        entityManagerFactory.close();
    }

    @Test
    void addClientTest(){
        try (ClientRepository clientRepository = new ClientRepository()) {
            System.out.println(clientRepository.getClients().size());
            clientRepository.addClient(client1);
            assertThat(clientRepository.findClientById(client1.getPersonalId())).isEqualTo(client1);
        }
    }

    @Test
    void removeClientTest(){
        try (ClientRepository clientRepository = new ClientRepository()) {
            clientRepository.addClient(client1);
            assertThat(clientRepository.getClients()).contains(client1);
            clientRepository.removeClient(client1);
            assertThat(clientRepository.getClients()).doesNotContain(client1);
        }
    }






}
