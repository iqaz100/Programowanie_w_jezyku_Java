package Classes;

import Controllers.ManagerController;
import Interfaces.IBillboard;
import Interfaces.IManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Order;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class CManager extends Application implements IManager {

    private List<IBillboard> billboardList = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private int billBoardID;
    private int orderID = 0;
    private boolean isFreeBillboard = false;
    private ManagerController controller;
    private Registry reg;

    public List<IBillboard> getBillboardList(){
        return billboardList;
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        billboardList.add(billboard);
        billBoardID++;
        return billBoardID;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        for (IBillboard billboard : billboardList){
            if(billboard.getId() == billboardId){
                billboardList.remove(billboard);
                System.out.println("Manager: Billboard o porcie: " + billboardId + " zostal odczepiony");
            }
        }
        return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        isFreeBillboard = false;
        for(IBillboard billboard: billboardList){
            if(billboard.getCapacity()[1] > 0){
                isFreeBillboard = true;
                billboard.addAdvertisement(order.advertText,order.displayPeriod,orderID);
            }
        }
        if(isFreeBillboard){
            orders.add(order);
            order.client.setOrderId(orderID);
            orderID++;
            return true;
        }
        else {
            System.out.println("Nie ma wolnych billboardow!");
            return false;
        }
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        for(IBillboard billboard: billboardList){
            billboard.removeAdvertisement(orderId);
        }
        return false;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new CManager();
    }

    public CManager() throws IOException {
        initManager(this,new Stage());
    }

    private void initManager(CManager manager, Stage primaryStage) throws IOException {
        IManager interfaceManager = (IManager) UnicastRemoteObject.exportObject(this, Registry.REGISTRY_PORT);
        this.reg = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        this.reg.rebind("Manager", interfaceManager);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Manager.fxml"));
        Parent root = loader.load();
        ManagerController controller = loader.getController();
        controller.setActualManager(manager);
        manager.setController(controller);
        primaryStage.setTitle("Manager");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public ManagerController getController() {
        return controller;
    }

    public void setController(ManagerController controller) {
        this.controller = controller;
    }
}
