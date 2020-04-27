package Classes;

import Controllers.BillboardController;
import Controllers.ClientController;
import Interfaces.IBillboard;
import Interfaces.IClient;
import Interfaces.IManager;
import javafx.application.Application;
import javafx.event.EventHandler;
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
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class CBillboard implements IBillboard {

    private int billboardID;
    private int registerHost;
    public List<Advertisement> advList = new ArrayList<>();
    private int[] capacity = {5,5};
    private IManager interfaceManager;
    private IBillboard interfaceBillboard;
    public int billboardPort;
    private BillboardController controller;
    private String name;
    private int tempI = 0;
    private Duration displayInterval;
    private boolean isRunning = false;

    public CBillboard(int billboardID, int registerHost, CManager manager) throws RemoteException {
        this.billboardID=billboardID;
        this.registerHost=registerHost;
    }

    public CBillboard(int billboardPort) throws IOException, NotBoundException {
        this.billboardPort = billboardPort;
        initBillboard(new Stage());
        isRunning = true;
        name = "Billboard: " + billboardPort + " ON";
        setDisplayInterval(Duration.parse("PT15M"));
}

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        if(getCapacity()[1]>0){
            advList.add(new Advertisement(advertText,displayPeriod,orderId));
            capacity[1]-=1;
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        for(Advertisement adv : advList){
            if(adv.orderID==orderId){
                advList.remove(adv);
                capacity[1]+=1;
                System.out.println("Reklama zostala usunieta w " + this.getName());
            }
        }
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return capacity;
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        this.displayInterval = displayInterval;
    }

    @Override
    public boolean start() throws RemoteException {
        System.out.println("Billboard " + billboardID + " wystartował");
        isRunning = true;
        name = "Billboard: " + billboardPort + " ON";
        return false;
    }

    @Override
    public boolean stop() throws RemoteException {

        System.out.println("Billboard " + billboardPort + " został wyłączony");
        controller.stopThread();
        isRunning = false;
        name = "Billboard: " + billboardPort + " OFF";
        return false;
    }


    public void initBillboard(Stage primaryStage) throws IOException, NotBoundException {
        interfaceBillboard = (IBillboard) UnicastRemoteObject.exportObject(this, billboardPort);
        Registry reg = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
        reg.rebind("Billboard "+ billboardPort, interfaceBillboard);
        this.setInterfaceManager((IManager) reg.lookup("Manager"));
        this.getInterfaceManager().bindBillboard(interfaceBillboard);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../sample/Billboard.fxml"));
        Parent root = loader.load();
        BillboardController controller = loader.getController();
        controller.setActualBillboard(this);
        this.setController(controller);
        primaryStage.setTitle("Billboard " + billboardPort);
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){
            public void handle(WindowEvent we){
                try {
                    stop();
                    interfaceManager.unbindBillboard(billboardPort);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        primaryStage.show();
    }

    public int getBillboardID() {
        return billboardID;
    }

    public void setBillboardID(int billboardID) {
        this.billboardID = billboardID;
    }

    public void setInterfaceManager(IManager interfaceManager) {
        this.interfaceManager = interfaceManager;
    }

    public IManager getInterfaceManager() {
        return interfaceManager;
    }

    public BillboardController getController() {
        return controller;
    }


    public void setController(BillboardController controller) {
        this.controller = controller;
    }

    @Override
    public String toString(){
        return "Billboard " + billboardPort;
    }

    @Override
    public String getName(){
        return name;
    }

    public void changeText(){
        tempI++;
        getController().labelAdvertisement.setText(Integer.toString(tempI));
    }

    public Duration getDisplayInterval() {
        return displayInterval;
    }

    @Override
    public List<Advertisement> getAdvertisements() throws RemoteException {
        return advList;
    }

    @Override
    public int getId() throws RemoteException {
        return billboardPort;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
