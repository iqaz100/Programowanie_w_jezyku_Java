package Controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.*;

import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import sample.Asteroid;
import sample.HyperlinkCell;
//import org.json.simple.JSONObject;



public class MainWindowController implements Initializable {

    public DatePicker startDateText;
    public DatePicker endDateText;
    public TableColumn<Asteroid, String> column1Name = new TableColumn<>();
    public TableColumn<Asteroid,String> column2Name = new TableColumn<>();
    public TableColumn<Asteroid, Hyperlink> column3Name = new TableColumn<>();
    public TableView<Asteroid> tableView = new TableView<Asteroid>();
    public Label asteroidsCountLabel;
    public Button searchButton;
    public Label timeIntervalLabel;

    private Locale plLocale = new Locale("pl", "PL");
    private Locale usLocale = new Locale("en", "US");

    private ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", plLocale);

    ObservableList<Asteroid> namesList = FXCollections.observableArrayList();

    String startData = "2015-09-07";
    String endData = "2015-09-08";
    int asteroidsNumber=0;



    @FXML
    RadioButton radioButtonPL;

    @FXML
    RadioButton radioButtonENG;

    public void selectPL(ActionEvent actionEvent) {
        radioButtonENG.setSelected(false);
        messages = ResourceBundle.getBundle("MessagesBundle", plLocale);
        changeLanguage();
    }

    public void selectENG(ActionEvent actionEvent) {
        radioButtonPL.setSelected(false);
        messages = ResourceBundle.getBundle("MessagesBundle", usLocale);
        changeLanguage();
    }

    String keyLabel(){  //METODA keyLabel sprawdzająca liczbę asteroid i dopasowująca odmianę słowa
        String tempKey = "";
        if(asteroidsNumber==1)
            tempKey="oneAsteroid";
        else if(asteroidsNumber%10>4 || (asteroidsNumber>11 && asteroidsNumber <15))
            tempKey="manyAsteroids";
        else if(asteroidsNumber%10==2 || asteroidsNumber%10==3 || asteroidsNumber%10==4)
            tempKey="twoToFourAsteroids";
        else
            tempKey="manyAsteroids";
        return tempKey;
    }

    void changeLanguage() {
        startDateText.setPromptText(messages.getString("startDateText"));
        endDateText.setPromptText(messages.getString("endDateText"));
        column1Name.setText(messages.getString("column1Name"));
        column3Name.setText(messages.getString("column3Name"));
        searchButton.setText(messages.getString("searchButton"));
        asteroidsCountLabel.setText(messages.getString("found") + " " + asteroidsNumber + " " + (messages.getString(keyLabel())));
        timeIntervalLabel.setText(messages.getString("timeIntervalLabel"));
       // loginButton.setText(messages.getString("login"));
       // adminCheckBox.setText(messages.getString("admin"));
    }

    public void loadAsteroids() throws IOException {
        namesList.clear();
        String query = "lon=100.75&lat=1.5&begin=2014-02-01&api_key=DEMO_KEY";
        String query2 = "start_date=" + startData + "&end_date="+ endData + "&api_key=QYt1QdNC9g8TWNKNftJAUxogX9OEDC4ERGxX7N5O";


        URLConnection connection = new URL("https://api.nasa.gov/neo/rest/v1/feed?" + query2).openConnection();
        connection.connect();

        BufferedReader r  = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));

        //JSONParser parse = new JSONParser();
        //JSONObject jobj = (JSONObject)parse.parse(r);
        //JSONArray jsonarr = (JSONArray) jobj.get("links");

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            sb.append(line);
        }

        String s = ("https://api.nasa.gov/neo/rest/v1/feed?" + query2);

        URL url = new URL(s);

        Scanner scan = new Scanner(url.openStream());
        String str = new String();
        while (scan.hasNext())
            str += scan.nextLine();
        scan.close();

        line = sb.toString();
        System.out.println(line + "\n");
        System.out.println(str);
        System.out.println("test");

        JSONObject jsonObject = new JSONObject(str);
        // String test = jsonObject.getString("links");
        //System.out.println(test);

        JSONObject res = jsonObject.getJSONObject("near_earth_objects");
        System.out.println(res);
        asteroidsNumber = jsonObject.getInt("element_count");
        System.out.println(asteroidsNumber);
        String res3 = jsonObject.getJSONObject("links").getString("next");
        System.out.println(res3);

        List<String> keyList = new ArrayList<String>();
        Iterator iterator = res.keys();
        while (iterator.hasNext()) { //pobieram wszystkie dni tygodnia
            String key = iterator.next().toString();
            keyList.add(key);
        }

        System.out.println(keyList);

        String[] arr = (String[]) keyList.toArray(new String[keyList.size()]);

        for(int i=0 ; i< arr.length ;i++){                      //iterowanie po dniach
            JSONArray namePlanet = res.getJSONArray(arr[i]);
            for(int j=0; j< namePlanet.length() ; j++){         //iterowanie po asteroidach
                String planetName = namePlanet.getJSONObject(j).getString("name");
                String planetId = namePlanet.getJSONObject(j).getString("id");
                String planetProperties = namePlanet.getJSONObject(j).getString("nasa_jpl_url");
                namesList.add(new Asteroid(planetName,planetId,planetProperties));
            }
        }

        System.out.println("Lista" + namesList);
        System.out.println(arr[0]);
        asteroidsCountLabel.setText(messages.getString("found") + " " + asteroidsNumber + " " + (messages.getString(keyLabel())));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //tableView.getColumns().addAll(column1Name);
        column1Name.setCellValueFactory(new PropertyValueFactory<Asteroid,String>("name"));
        column2Name.setCellValueFactory(new PropertyValueFactory<Asteroid,String>("id"));
        column3Name.setCellValueFactory(new PropertyValueFactory<>("hyperlink"));
        column3Name.setCellFactory(new HyperlinkCell());


        tableView.setItems(namesList);
    }

    public void changeStartData(ActionEvent actionEvent) throws IOException {
        startData = startDateText.getValue().toString();
    }

    public void changeEndData(ActionEvent actionEvent) throws IOException {
        endData = endDateText.getValue().toString();
    }

    public void searchAsteroids(ActionEvent actionEvent) throws IOException {
        loadAsteroids();
    }
}
