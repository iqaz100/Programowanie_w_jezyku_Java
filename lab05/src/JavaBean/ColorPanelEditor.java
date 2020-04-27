package JavaBean;

import java.awt.*;
import java.beans.PropertyEditorSupport;

public class ColorPanelEditor extends PropertyEditorSupport {

    @Override
    public boolean isPaintable() {
        return true;
    }

    @Override
    public Component getCustomEditor() {
        return new ColorPanelEditorPanel(this);
    }

    @Override
    public boolean supportsCustomEditor() {
        return true;
    }
}
