package dataPack;

import model.Book;
import model.Client;
import static model.Client.Type.*;
import model.Rent;

import java.time.LocalDateTime;
import java.util.Arrays;

public class data {

    public static Client client = new Client("Jan", "Lesniak", NORMAL);
    public static Client client2 = new Client("Jakub", "Nowak", STUDENT);
    public static Client client3 = new Client("Jon", "Kowal", PROFESSIONAL);
    public static Book book1 = new Book( "O tym jak zdac studia", "Komedia",
            401);
    public static Book book2 = new Book( "O systemach operacyjnych slow kilka", "Dramat",
            666);
    public static Book book3 = new Book( "Piesn asemblera i dosu", "Poezja",
            102);

}

