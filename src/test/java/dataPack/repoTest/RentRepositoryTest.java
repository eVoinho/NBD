package dataPack.repoTest;

import static dataPack.data.*;
<<<<<<< Updated upstream
=======
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

>>>>>>> Stashed changes
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import repository.RentRepository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.RollbackException;

import model.Rent;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import javax.persistence.RollbackException;

class RentRepositoryTest {

    private static EntityManagerFactory entityManagerFactory =
            Persistence.createEntityManagerFactory("default");

    @AfterAll
    static void close() {
        entityManagerFactory.close();
    }

    @Test
    void addRent() throws Exception {
        try (RentRepository rentRepository = new RentRepository()) {
            client1.addRent(rent1);
            rentRepository.addActiveRent(rent1);
            assertThat(rentRepository.getRents()).contains(rent1);
        }
    }

    @Test
    void optimisticLockExceptionTest() {

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        entityManager1.getTransaction().begin();
        entityManager2.getTransaction().begin();

        entityManager1.persist(rent1);
        entityManager1.getTransaction().commit();

        Rent rentTest1 = entityManager1.find(Rent.class, rent1.getId());
        Rent rentTest2 = entityManager2.find(Rent.class, rent1.getId());

        entityManager1.getTransaction().begin();
        rentTest1.setTotalPenalty(50.0);
        entityManager1.getTransaction().commit();

        rentTest2.setTotalPenalty(60.0);

        assertThatThrownBy(() -> entityManager2.getTransaction().commit()).isInstanceOf(
                RollbackException.class);

        entityManager1.close();
        entityManager2.close();
    }
    @Test
    void removeRent() throws Exception {
        try (RentRepository rentRepository = new RentRepository()) {
            client1.addRent(rent1);
            rentRepository.addActiveRent(rent1);
            assertThat(rentRepository.getRents()).contains(rent1);
            rentRepository.removeRent(rent1);
            assertThat(rentRepository.getRents()).doesNotContain(rent1);
        }
    }

    @Test
    void optimisticLockExceptionTest() {

        EntityManager entityManager1 = entityManagerFactory.createEntityManager();
        EntityManager entityManager2 = entityManagerFactory.createEntityManager();

        entityManager1.getTransaction().begin();
        entityManager2.getTransaction().begin();

        entityManager1.persist(rent1);
        entityManager1.getTransaction().commit();

        Rent rentTest1 = entityManager1.find(Rent.class, rent1.getId());
        Rent rentTest2 = entityManager2.find(Rent.class, rent1.getId());

        entityManager1.getTransaction().begin();
        rentTest1.setTotalPenalty(50.0);
        entityManager1.getTransaction().commit();

        rentTest2.setTotalPenalty(60.0);

        assertThatThrownBy(() -> entityManager2.getTransaction().commit()).isInstanceOf(
                RollbackException.class);

        entityManager1.close();
        entityManager2.close();
    }

}
