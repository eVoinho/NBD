package repository;

import model.Client;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ClientRepository implements AutoCloseable{

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public ClientRepository(){
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public List<Client> getClients(){
        return entityManager.createQuery("SELECT c FROM Client C", Client.class).getResultList();
    }

    public void addClient(Client client){
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();
    }

    public void removeClient(Client client){
        entityManager.getTransaction().begin();
        entityManager.remove(client);
        entityManager.getTransaction().commit();
    }

    public String Report(){
        entityManager.getTransaction().begin();
        List<Client> clients = entityManager.createQuery("SELECT c FROM Client c").getResultList();
        entityManager.getTransaction().commit();
        StringBuilder stringBuilder = new StringBuilder();
        for (Client client : clients){
            stringBuilder.append(client.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void close() {
        entityManagerFactory.close();
        entityManager.close();
    }
}
