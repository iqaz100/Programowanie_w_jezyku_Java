package sample;

import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Asteroid {
    String name;
    String id;
    Hyperlink hyperlink;

    String properties;
    public Asteroid(String name,String id,String properties){
        this.name = name;
        this.id = id;
        this.hyperlink = new Hyperlink(properties);

        this.hyperlink.setOnAction(e -> {
            Desktop d = Desktop.getDesktop();
            try {
                d.browse(new URI(properties));
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (URISyntaxException ex) {
                ex.printStackTrace();
            }
        });

    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getProperties() {
        return properties;
    }

    public Hyperlink getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String properties) {
        this.hyperlink = new Hyperlink(properties);
    }
}
