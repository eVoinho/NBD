package dataPack.repoTest;

import static dataPack.data.client1;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import model.Client;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import repository.ClientRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;


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
    void OptimisticLockExceptionTest() {

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        entityManager1.getTransaction().begin();
        entityManager2.getTransaction().begin();

        entityManager1.persist(client1);
        entityManager1.getTransaction().commit();

        Client clientTest1 = entityManager1.find(Client.class, client1.getPersonalId());
        Client clientTest2 = entityManager2.find(Client.class, client1.getPersonalId());

        entityManager1.getTransaction().begin();
        clientTest1.setFirstName("Marcin");
        entityManager1.getTransaction().commit();

        clientTest2.setFirstName("Bogdan");

        assertThatThrownBy(() -> entityManager2.getTransaction().commit()).isInstanceOf(RollbackException.class);

        entityManager1.close();
        entityManager2.close();
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
