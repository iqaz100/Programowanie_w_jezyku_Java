package Controllers;

import Classes.CManager;
import Interfaces.IBillboard;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class ManagerController implements Initializable {

    @FXML
    public ListView<IBillboard> billboardListView;

    private CManager actualManager;


    public CManager getActualManager() {
        return actualManager;
    }

    public void setActualManager(CManager actualManager) {
        this.actualManager = actualManager;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void refreshButtonOnClick(ActionEvent actionEvent) {
        billboardListView.setCellFactory(param -> new ListCell<IBillboard>() {
            @Override
            protected void updateItem(IBillboard item, boolean empty) {         //METODA updateItem aby wyswietlana lista nie byla pusta
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    try {
                        setText(item.getName());
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        billboardListView.setItems(FXCollections.observableArrayList(getActualManager().getBillboardList()));
    }
}
