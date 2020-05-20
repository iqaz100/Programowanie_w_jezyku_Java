package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import net.pwr.mysecurityclass.MySecurityClass;
import org.apache.commons.codec.*;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;

public class Sender extends Application {

    public String userName;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    public MySecurityClass ms;

    public void send(String message, String host, int port) throws Exception {
        Socket s;
        PrivateKey privateKey = ms.getPrivate("KeyPair/privateKey"+userName);
        System.out.println("Zakodowano kluczem obcym użytkownika: " + userName);
        String encryptedMessage = ms.encryptText(message,privateKey);
        try {
            s = new Socket(host,port);
            OutputStream out = s.getOutputStream();
            //ObjectOutputStream os = new ObjectOutputStream(out);
            PrintWriter pw = new PrintWriter(out, false);
            pw.println(userName + ": "+ encryptedMessage);
           //os.writeObject(ms.getPublicKey());
            pw.flush();
            pw.close();
            s.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public Sender(String userName) throws IOException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.userName = userName;
        generateKeys();
    }

    public Sender() throws NoSuchPaddingException, NoSuchAlgorithmException, IOException {
        generateKeys();
    };

    @Override
    public void start(Stage primaryStage) throws Exception {
        new Sender();
    }

    public void initSender(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ChatUser.fxml"));
        primaryStage.setTitle("UserMenu");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                try {
                    stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void generateKeys() throws NoSuchAlgorithmException, NoSuchPaddingException, IOException {
        ms = new MySecurityClass();
        ms.generateKeys(1024,userName);
        ms.createKeys();
    }
}
