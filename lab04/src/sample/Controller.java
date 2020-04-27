package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Controller {

    @FXML
    public TextArea textToProcess;

    @FXML
    public Label resultLabel;

    @FXML
    public Label processingStatusInfo;
    public Label currentClassLabel;

    String className = "HashText";


    public void startProcessingButtonOnClick(ActionEvent actionEvent) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, ClassNotFoundException {
        String tempText = textToProcess.getText();
        CustomClassLoader loader = new CustomClassLoader();
        Class<?> c = loader.findClass("sample." + className);

        Object o = c.getDeclaredConstructor().newInstance();
        Method main = c.getMethod("submitTask", String.class);
        Object r = main.invoke(o,textToProcess.getText());

        Method getInfo = c.getMethod("getInfo");
        Object objInfo = getInfo.invoke(o);

        Method getResult = c.getMethod("getResult");
        Object objResult = getResult.invoke(o);

        currentClassLabel.setText(objInfo.toString());

        resultLabel.setText(objResult.toString());
        System.out.print(r);

    }

    public void refreshButtonOnClick(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        //fileChooser.setInitialDirectory();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CLASS files (.class)","*.class");
        fileChooser.getExtensionFilters().add (extFilter);
        File file = fileChooser.showOpenDialog(null);
        System.out.println(file.getName());
        String result2 = file.getName().substring(0,file.getName().indexOf("."));
        System.out.println(result2);
        className=result2;
    }

}
