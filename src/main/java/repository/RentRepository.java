package repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import model.Client;
import model.Rent;
import java.time.LocalDateTime;
import java.util.List;

public class RentRepository implements AutoCloseable{

    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;

    public RentRepository() {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
    }

    public void addActiveRent(Rent rent){
        Client client = rent.getClient();
        if (client.getClientType().getMaxItems() > client.getRents().size()){
            entityManager.getTransaction().begin();
            entityManager.persist(rent);
            entityManager.getTransaction().commit();
        }
        //Exception jakiś mają????????
    }

    public void addArchiveRent(Rent rent){
        entityManager.getTransaction().begin();
        entityManager.persist(rent);
        entityManager.getTransaction().commit();
    }

    public List<Rent> getRents(){
        return entityManager.createQuery("from Rent", Rent.class).getResultList();
    }

    public void removeRent(Rent rent) {

        Client client = rent.getClient();
        rent.setEnd(LocalDateTime.now());

        if (rent.getEnd().isAfter(rent.getBegin().plusDays(client.getClientType().getMaxDays()))) {

            int daysAfterEndTime = rent.getEnd().getDayOfYear() - rent.getBegin().plusDays(client.getClientType().getMaxDays()).getDayOfYear();
            rent.setTotalPenalty(client.getClientType().getPenalty() * daysAfterEndTime);
        }

        entityManager.getTransaction().begin();
        entityManager.remove(rent);
        entityManager.getTransaction().commit();
    }

    public String Report() {

        entityManager.getTransaction().begin();
        List<Rent> rents = entityManager.createQuery("SELECT r FROM Rent r").getResultList();
        entityManager.getTransaction().commit();

        StringBuilder stringBuilder = new StringBuilder();
        for (Rent rent : rents) {
            stringBuilder.append(rent.toString());
        }
        return stringBuilder.toString();
    }

    @Override
    public void close() throws Exception {

    }
}
