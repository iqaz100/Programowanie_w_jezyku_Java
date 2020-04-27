package Controllers;

import Classes.Advertisement;
import Classes.CBillboard;
import Interfaces.IBillboard;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import static java.lang.Thread.currentThread;
import static java.lang.Thread.sleep;

public class BillboardController implements Initializable {
    public Label labelAdvertisement;
    public Button startButton;
    public Button stopButton;
    public Label labelTime;
    private boolean isRunningFlag = true;
    private boolean resetCounter = false;
    private IBillboard actualBillboard;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setDisable(true);
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws RemoteException, InterruptedException {
                for(int i=0;i<1000000;i++)
                {
                    if(((getActualBillboard().getCapacity()[0]) - (getActualBillboard().getCapacity()[1])) == 0){
                        Platform.runLater(() ->{
                            labelAdvertisement.setText("");
                        });
                    }
                    if(isRunningFlag==true) {
                        for (int j = 0; j < ((getActualBillboard().getCapacity()[0]) - (getActualBillboard().getCapacity()[1]));j++) {

                            int finalJ = j;
                            Platform.runLater(() -> {
                                try {
                                    labelAdvertisement.setText(getActualBillboard().getAdvertisements().get(finalJ).text);
                                } catch (RemoteException e) {
                                    e.printStackTrace();
                                }
                            });;
                            if(resetCounter==true){     //warunek sprawdzający czy reset jest aktywny, jeśli tak to idz dalej, jeśli nie to j-- czyli stój w miejscu i nie zmieniaj reklamy.
                                resetCounter = false;
                            }
                            else
                            {
                                j--;
                            }
                            //sleep(getActualBillboard().getDisplayInterval().toMillis() / 60);     //Czas uspienia wątku odpowiedzialnego za zmiane reklamy. Dzielony przez 60 - zamiana minut na sekundy.
                            sleep(50);
                            if(isRunningFlag==false){
                                break;
                            }
                        }
                    }
                    sleep(50);
                    if(i==1000000-1){
                        i=0;
                    }
                }
                return null;
            }

            };
        new Thread(task).start();
        Task<Void> task2 = new Task<Void>() {
            @Override
            public Void call() throws RemoteException, InterruptedException {
                long l = getActualBillboard().getDisplayInterval().toMinutes();
                for(int i= (int) l;i>=0;i--){
                    int finalI = i;
                    Platform.runLater(() ->{
                        labelTime.setText(Integer.toString(finalI));
                    });

                    sleep(1000);
                    if(i==0){
                        i = (int) l;
                        resetCounter=true;
                    }
                    if(isRunningFlag==false){
                        i++;
                    }
                }
                return null;
            }

        };
        new Thread(task2).start();
    }

    public void setActualBillboard(CBillboard actualBillboard) {
        this.actualBillboard = actualBillboard;
    }

    public IBillboard getActualBillboard() {
        return actualBillboard;
    }

    public void startBillboardOnClick(ActionEvent actionEvent) throws RemoteException {
        startButton.setDisable(true);
        stopButton.setDisable(false);
        isRunningFlag=true;
        actualBillboard.start();
    }

    public void stopBillboardOnClick(ActionEvent actionEvent) throws RemoteException {
        stopButton.setDisable(true);
        startButton.setDisable(false);
        isRunningFlag=false;
        actualBillboard.stop();
    }

    public void stopThread(){
        //tutaj powinna znalezc się kod konczacy watki
    }
}
