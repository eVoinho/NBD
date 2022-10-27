package manager;

import model.Rent;
import repository.RentRepository;

public class RentMenager {
    RentRepository archiveRents = new RentRepository();
    RentRepository activeRents = new RentRepository();

    public void rentBook(Rent rent){
        activeRents.addActiveRent(rent);
    }

    public void returnBook(Rent rent){
        activeRents.removeRent(rent);
        archiveRents.addArchiveRent(rent);
    }
}
