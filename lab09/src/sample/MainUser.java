package sample;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainUser extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Sender sender = new Sender("Kuba");
        sender.initSender(new Stage());

        //dwa mainy - jeden dla Receiver, jeden dla Sender i dla Sender ustawione Pararel (Edit Configuration > Allow parrarel)
        //serwer nadaje porty userom
        //user przesyla wiadomosc do serwera zaszyfrowaną, on pokazuje ją i wysyla dalej wraz z kluczem do user2, a ten odszyfrowuje
    }

    public static void main(String[] args) throws IOException {
        launch(args);
    }
}
