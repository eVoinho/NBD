package dataPack.repoTest;

import model.Rent;
import static dataPack.data.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import repository.RentRepository;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
    void removeRent() throws Exception {
        try (RentRepository rentRepository = new RentRepository()) {
            client1.addRent(rent1);
            rentRepository.addActiveRent(rent1);
            assertThat(rentRepository.getRents()).contains(rent1);
            rentRepository.removeRent(rent1);
            assertThat(rentRepository.getRents()).doesNotContain(rent1);
        }
    }
}
