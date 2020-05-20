package sample;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class Receiver {
    private List<MyListener> ml = new ArrayList<MyListener>();
    private Thread t = null;
    private int port = 0;
    private ServerSocket s = null;
    private boolean end = false;

    public ServerSocket create(int[] ports) throws IOException {
        for (int port : ports) {
            try {
                return new ServerSocket(port);
            } catch (IOException ex) {
                continue; // try next port
            }
        }

        // if the program gets here, no port in the range was found
        throw new IOException("no free port found");
    }

    public void stop() {
        t.interrupt();
        try {
            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void start() {
        end = false;
        //t = new Thread(new Runnable() {
        Task<Void> task = new Task<Void>(){
            @Override
            public Void call() {
                try {
                    //s = new ServerSocket(port);
                    s = create(new int[] {9005,9006,9007,9008});
                    System.out.println("Odbierasz na porcie: " + s.getLocalPort());
                    while (true) {
                        Socket sc = s.accept();
                        InputStream is = sc.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        BufferedReader br = new BufferedReader(isr);
                        String theLine = br.readLine();
                        ml.forEach((item) -> item.messageReceived(theLine));
                        sc.close();
                    }
                } catch(SocketException e){
                    // TODO - podczas przerywania wątku metoda accept zgłosi wyjątek
                    // z wiadomością: socket closed
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Brak dostepnych portow!");
                    e.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task).start();
        //t.start();
    }

    public void addMyListener(MyListener m) {
        ml.add(m);
    }

    public void removeMyListener(MyListener m) {
        ml.remove(m);
    }

    Receiver(int port){
        this.port = port;
    }

    Receiver() throws IOException {
        initReceiver(new Stage());
    }

    public void initReceiver(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ChatMenu.fxml"));
        primaryStage.setTitle("ChatBox");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
