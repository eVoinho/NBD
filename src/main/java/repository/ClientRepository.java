package repository;

import com.datastax.oss.driver.api.core.cql.ResultSet;
import model.Client;
import java.util.List;
import java.util.UUID;



public class ClientRepository extends Repository{


    public ClientRepository(){ super();}



    public void addClient(Client client){
       session.execute("INSERT INTO client (ID, firstName, lastName) VALUES (?,?,?)", client.getClientId(),
               client.getFirstName(), client.getLastName());
    }

    public void removeClient(UUID personalId) {
       session.execute("DELETE FROM client WHERE ID = ?", personalId);
    }

    public void update(Client client) {
        session.execute("UPDATE client SET firstName = ?, lastName = ? WHERE ID = ?", client.getFirstName(),
                client.getLastName(), client.getClientId());
    }

    public ResultSet getClient(UUID personalId) {
        return session.execute("SELECT * FROM client WHERE ID = ?", personalId);
    }

    public ResultSet getAllClients() {

        ResultSet resultSet = session.execute("SELECT * FROM client");

        for(var row : resultSet) {
            System.out.println(row.getUuid("ID") + " " + row.getString("firstName") + " " +
                    row.getString("lastName"));
        }
        return resultSet;
    }

    public Client getClientById(UUID personalId) {
        ResultSet resultSet = getClient(personalId);
        Client client = new Client();
        for (var row : resultSet) {
            client.setClientId(row.getUuid("ID"));
            client.setFirstName(row.getString("firstName"));
            client.setLastName(row.getString("lastName"));
        }
        return client;
    }

    public List<Client> getAllClientsAsList() {
        ResultSet resultSet = getAllClients();
        List<Client> clients = List.of();
        for (var row : resultSet) {
            Client client = new Client();
            client.setClientId(row.getUuid("ID"));
            client.setFirstName(row.getString("firstName"));
            client.setLastName(row.getString("lastName"));
            clients.add(client);
        }
        return clients;
    }
    @Override
    public void close() throws Exception {
        session.close();
    }

}
