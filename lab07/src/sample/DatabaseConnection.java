package sample;

import Tables.Charge;
import Tables.Client;
import Tables.Installation;
import Tables.Payment;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseConnection {

    private Connection connection = null;
    private Statement statement = null;
    private String url = "jdbc:sqlite:./database.db";

    public DatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection(url);
    }

    public void testowa(){
        System.out.println("sds");
    }

    public void addClient(String firstName, String lastName) throws SQLException {
        String sql = "INSERT INTO Client(firstName,lastName) VALUES(?,?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.executeUpdate();
    }

    public void deleteClient(int id) throws SQLException {
        String sql = "DELETE FROM Client WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        statement.executeUpdate();
    }

    public void addInstallation(String address, int idClient, int routerNumber, int serviceType) throws SQLException {
        String sql = "INSERT INTO Installation(address,idClient,routerNumber,serviceType) VALUES(?,?,?,?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, address);
        ps.setInt(2, idClient);
        ps.setInt(3, routerNumber);
        ps.setInt(4, serviceType);

        ps.executeUpdate();
    }

    public void deleteInstallation(int id) throws SQLException {
        String sql = "DELETE FROM Installation WHERE id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1,id);
        statement.executeUpdate();
    }

    public void addPayment(int idInstallation, int prize, String term) throws SQLException {
        String sql = "INSERT INTO Payment(idInstallation,prize,term) VALUES(?,?,?)";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setInt(1, idInstallation);
        ps.setInt(2, prize);
        ps.setString(3, term);

        ps.executeUpdate();
    }

    public void addCharge(String term) throws SQLException {
        String sql = "insert into Charge (term,prize,idInstallation) select ?, p.prize, i.id from Installation i join prize p on p.serviceid = i.servicetype";

        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, term);
       // ps.setInt(2, idInstallation);

        ps.executeUpdate();
    }

    public ArrayList<Charge> selectCharges() throws SQLException {
        String sql = "SELECT id,term,prize,idInstallation FROM Charge";
        ArrayList<Charge> chargesList = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            chargesList.add(new Charge(rs.getInt("id"),rs.getInt("idInstallation"),
                    rs.getInt("prize"),rs.getString("term")));
        }
        return chargesList;
    }

    public ArrayList<Client> selectClients() throws SQLException {
        String sql = "SELECT id,firstName,lastName FROM Client";
        ArrayList<Client> clientList = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            clientList.add(new Client(rs.getInt("id"),rs.getString("firstName"),rs.getString("lastName")));
        }

        return clientList;
    }

    public void disconnect() throws SQLException {
        if (connection == null)
            return;
        connection.close();
        connection = null;
    }

    public ArrayList<Installation> selectInstallations() throws SQLException {
        String sql = "SELECT id,address,idClient,routerNumber,serviceType FROM Installation";
        ArrayList<Installation> installationList = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            installationList.add(new Installation(rs.getInt("id"),rs.getString("address"),
                    rs.getInt("idClient"),rs.getInt("routerNumber"),rs.getInt("serviceType")));
        }

        return installationList;
    }

    public ArrayList<Payment> selectPayments() throws SQLException {
        String sql = "SELECT id,idInstallation,prize,term FROM Payment";
        ArrayList<Payment> paymentsList = new ArrayList<>();

        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            paymentsList.add(new Payment(rs.getInt("id"),rs.getInt("idInstallation"),
                    rs.getInt("prize"),rs.getString("term")));
        }

        return paymentsList;
    }
}
