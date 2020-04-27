package Controllers;

import Classes.CBillboard;
import Classes.CClient;
import Classes.CManager;
import Interfaces.IBillboard;
import Interfaces.IClient;
import Interfaces.IManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Order;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ResourceBundle;

public class ClientController implements Initializable {

    public ListView<Integer> ordersList = new ListView<>();
    private IClient actualClient;

    @FXML
    public TextField advertTime;

    @FXML
    public TextField advertText;

    private ObservableList<Integer> ordersCont;

    public ClientController() throws IOException {
    }

    public void addAdvertButtonOnClick(ActionEvent actionEvent) throws RemoteException {
        boolean addedOrder;

        addedOrder = actualClient.getInterfaceManager().placeOrder(new Order(advertText.getText(), Duration.parse("PT" + advertTime.getText() + "M") ,actualClient));
        if(addedOrder){
            System.out.println("Zamowienie zostalo dodane");
        }
        refreshList();

    }

    public void deleteAdvertButtonOnClick(ActionEvent actionEvent) throws RemoteException {
        actualClient.getInterfaceManager().withdrawOrder(ordersList.getSelectionModel().getSelectedItem());
        refreshList();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ordersList.setCellFactory(param -> new ListCell<Integer>(){
            @Override
            protected void updateItem(Integer item, boolean empty) {         //METODA updateItem aby wyswietlana lista nie byla pusta
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText("Reklama id: " + item.toString());
                }
            }
        });
    }

    public IClient getActualClient() {
        return actualClient;
    }

    public void setActualClient(IClient actualClient) {
        this.actualClient = actualClient;
    }

    public void refreshList() throws RemoteException {
        ordersCont = FXCollections.observableArrayList(getActualClient().getOrders());
        ordersList.setItems(FXCollections.observableArrayList(getActualClient().getOrders()));
    }
}
