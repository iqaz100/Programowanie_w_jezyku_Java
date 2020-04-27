package sample;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class HyperlinkCell implements  Callback<TableColumn<Asteroid, Hyperlink>, TableCell<Asteroid, Hyperlink>> {

    @Override
    public TableCell<Asteroid, Hyperlink> call(TableColumn<Asteroid, Hyperlink> arg) {
        TableCell<Asteroid, Hyperlink> cell = new TableCell<Asteroid, Hyperlink>() {
            @Override
            protected void updateItem(Hyperlink item, boolean empty) {
                setGraphic(item);
            }
        };
        return cell;
    }


}
