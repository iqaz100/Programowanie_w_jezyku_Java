package sample;

import Tables.Charge;
import Tables.Installation;
import Tables.Payment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Tables.Client;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class MainPanelController implements Initializable {

    public ComboBox clientComboBox;
    public TextField addressInstallationTextField;
    public TextField routerNumberInstallationTextField;
    public TextField typeInstallationTextField;
    public TableView<Installation> tableViewInstallation;
    public TableColumn tableColumnInstallationAddress;
    public TableColumn tableColumnInstallationNumber;
    public TableColumn tableColumnInstallationType;
    public TableView<Payment> tableViewPayment;
    public TableColumn termPaymentTableColumn;
    public TableColumn prizePaymentTableColumn;
    public TableColumn idInstallPaymentTableColumn;
    public TableView<Charge> tableViewCharge;
    public TableColumn termChargeTableColumn;
    public TableColumn prizeChargeTableColumn;
    public TableColumn idInstallChargeTableColumn;
    public TextField prizePaymentTextField;
    public ComboBox idInstallationComboBox;
    private ObservableList<Client> clientList = null;
    private ObservableList<Installation> installationList = null;
    private ObservableList<Payment> paymentsList = null;
    private ObservableList<Charge> chargeList = null;
    private int datePlus = 0;

    public DatabaseConnection dbc;

    public TableColumn<Client,String> tableColumnClientName;

    public TableColumn<Client,String> tableColumnClientSurname;

    public TableColumn<Client,Integer> tableColumnClientID;

    public TableView<Client> tableViewClient;
    private Client client;

    public TextField firstNameTextField;
    public TextField secondNameTextField;

    public void addClientButtonOnClick(ActionEvent actionEvent) throws SQLException {
        dbc.addClient(firstNameTextField.getText(),secondNameTextField.getText());
        refreshList();
    }

    public void deleteButtonOnClick(ActionEvent actionEvent) throws SQLException {
        Client selectedClient = tableViewClient.getSelectionModel().getSelectedItem();
        dbc.deleteClient(selectedClient.getId());
        refreshList();
    }

    public void refreshList() throws SQLException {
        clientList = FXCollections.observableArrayList(dbc.selectClients());
        tableViewClient.setItems(clientList);
    }

    public void refreshInstallationList() throws SQLException {
        installationList = FXCollections.observableArrayList(dbc.selectInstallations());
        tableViewInstallation.setItems(installationList);
    }

    public void refreshComboBox() throws SQLException {
        clientList = FXCollections.observableArrayList(dbc.selectClients());
        clientComboBox.getItems().addAll(clientList);
        installationList = FXCollections.observableArrayList(dbc.selectInstallations());
        idInstallationComboBox.getItems().addAll(installationList);
    }

    public void refreshPaymentsList() throws SQLException {
        paymentsList = FXCollections.observableArrayList(dbc.selectPayments());
        tableViewPayment.setItems(paymentsList);
    }

    public void refreshChargesList() throws SQLException {
        chargeList = FXCollections.observableArrayList(dbc.selectCharges());
        tableViewCharge.setItems(chargeList);
    }

    public void writeChargesToFile() throws IOException, SQLException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("chargesList"));

        for(Charge charge : dbc.selectCharges()){
            writer.append(String.valueOf(charge));
        }
        writer.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            dbc = new DatabaseConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableColumnClientName.setCellValueFactory(new PropertyValueFactory<Client, String>("firstName"));
        tableColumnClientSurname.setCellValueFactory(new PropertyValueFactory<Client, String>("lastName"));
        tableColumnClientID.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));

        tableColumnInstallationAddress.setCellValueFactory(new PropertyValueFactory<Installation, String>("address"));
        tableColumnInstallationNumber.setCellValueFactory(new PropertyValueFactory<Installation, String>("routerNumber"));
        tableColumnInstallationType.setCellValueFactory(new PropertyValueFactory<Installation, Integer>("serviceType"));

        termPaymentTableColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("term"));
        idInstallPaymentTableColumn.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("idInstallation"));
        prizePaymentTableColumn.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("prize"));

        termChargeTableColumn.setCellValueFactory(new PropertyValueFactory<Payment, String>("term"));
        idInstallChargeTableColumn.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("idInstallation"));
        prizeChargeTableColumn.setCellValueFactory(new PropertyValueFactory<Payment, Integer>("prize"));

        try {
            refreshList();
            refreshInstallationList();
            refreshPaymentsList();
            refreshComboBox();
            refreshChargesList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addInstallationButtonOnClick(ActionEvent actionEvent) throws SQLException {
        dbc.addInstallation(addressInstallationTextField.getText(),Integer.parseInt(clientComboBox.getValue().toString()),Integer.parseInt(routerNumberInstallationTextField.getText()),Integer.parseInt(typeInstallationTextField.getText()));
        refreshInstallationList();
    }

    public void deleteInstallationButtonOnClick(ActionEvent actionEvent) throws SQLException {
        Installation selectedInstallation = tableViewInstallation.getSelectionModel().getSelectedItem();
        dbc.deleteInstallation(selectedInstallation.getId());
        refreshInstallationList();
    }

    public void addPayment(ActionEvent actionEvent) throws SQLException {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String actualDate = date.format(formatter).toString();
        dbc.addPayment(Integer.parseInt(idInstallationComboBox.getValue().toString()),Integer.parseInt(prizePaymentTextField.getText()), actualDate);
        refreshPaymentsList();
    }

    public void addCharges(ActionEvent actionEvent) throws SQLException, IOException {
        datePlus++;
        LocalDate date = LocalDate.now().plusMonths(datePlus);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String actualDate = date.format(formatter).toString();
        dbc.addCharge(actualDate);
        refreshChargesList();
        writeChargesToFile();
    }
}
