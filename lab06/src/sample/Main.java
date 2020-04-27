package sample;

import Classes.CBillboard;
import Classes.CClient;
import Classes.CManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.rmi.RemoteException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        new CManager();

        new CBillboard(9004);
        new CBillboard(9002);
        new CBillboard(9003);

        new CClient(9053);
        new CClient(9054);

    }


    public static void main(String[] args) throws IOException {

        launch(args);
    }
}
