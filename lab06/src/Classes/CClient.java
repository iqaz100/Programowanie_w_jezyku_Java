package Classes;

import Controllers.BillboardController;
import Controllers.ClientController;
import Interfaces.IBillboard;
import Interfaces.IClient;
import Interfaces.IManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class CClient extends Application implements IClient {

    public List<Integer> orders = new ArrayList<>();
    public int clientPort;
    private ClientController controller;
    private IClient interfaceClient;
    private IManager interfaceManager;

    @Override
    public void setOrderId(int orderId) throws RemoteException {
        orders.add(orderId);
    }

    @Override
    public List<Integer> getOrders() throws RemoteException {
        return orders;
    }

    public CClient(int port) throws IOException, NotBoundException {
        this.clientPort = port;
        initClient(this,new Stage());
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new CClient(8535);
    }

    public void initClient(CClient client, Stage primaryStage) throws IOException, NotBoundException {

        interfaceClient = (IClient) UnicastRemoteObject.exportObject(this, clientPort);
        Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        reg.rebind("Client "+ clientPort, interfaceClient);
        client.setInterfaceManager((IManager) reg.lookup("Manager"));

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Client.fxml"));
        Parent root = loader.load();
        ClientController controller = loader.getController();
        controller.setActualClient(client);
        client.setController(controller);
        primaryStage.setTitle("Client " + clientPort);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public ClientController getController() {
        return controller;
    }

    public void setController(ClientController controller) {
        this.controller = controller;
    }

    public IManager getInterfaceManager() {
        return interfaceManager;
    }

    public void setInterfaceManager(IManager interfaceManager) {
        this.interfaceManager = interfaceManager;
    }
}
