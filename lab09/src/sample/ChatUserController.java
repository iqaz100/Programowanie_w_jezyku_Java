package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import net.pwr.mysecurityclass.MySecurityClass;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChatUserController implements Initializable,MyListener {
    public TextField messageTextField;
    public TextArea chatBox;
    public TextField portTextField;
    public TextField nicknameTextField;
    private String hostName = null;
    private Receiver r = null;
    private Sender s;
    private int port = 9005;
    private boolean firstMessageSent = false;
    MySecurityClass msc;
    PublicKey pk;
    List<String> chatBoxList = new ArrayList<>();

    public void sendMessageOnClick(ActionEvent actionEvent) throws Exception {
        if(!firstMessageSent){
            s = new Sender(nicknameTextField.getText());
            firstMessageSent = true;
        }

        s.send(messageTextField.getText(),hostName,Integer.parseInt(portTextField.getText()));
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        r = new Receiver(port);
        r.addMyListener(ChatUserController.this);
        r.start();
    }

    @Override
    public void messageReceived(String theLine) {
        Platform.runLater(() ->{
            chatBoxList.add(theLine);
            chatBox.appendText(theLine + "\n");
        });
    }

    public void selectKeyOnClick(ActionEvent actionEvent) throws Exception {

        FileChooser fc = new FileChooser();
        fc.setInitialDirectory(new File(System.getProperty("user.dir")));
        File selectedFile = fc.showOpenDialog(null);
        msc = new MySecurityClass();

        if(selectedFile!=null){
            pk = msc.getPublic("KeyPair/" + selectedFile.getName());
            System.out.println("Wczytany klucz publiczny: " + pk);
        }
    }

    public void decodeMsgOnClick(ActionEvent actionEvent) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
        chatBox.clear();
        for(String chatMsg : chatBoxList){
            String[] splitMsg = chatMsg.split(" ");
            String decryptedText = msc.decryptText(splitMsg[1],pk);
            chatBox.appendText(splitMsg[0]+decryptedText+"\n");
            //System.out.println(msc.decryptText(chatMsg,pk));
        }
    }
}
