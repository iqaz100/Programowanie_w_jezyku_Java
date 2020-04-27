package JavaBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyEditorSupport;

public class ColorPanelEditorPanel extends JPanel implements PropertyChangeListener {

    private JPanel panel;
    private PropertyEditorSupport editor;
    private JLabel label;
    private JCheckBox box1;
    private JCheckBox box2;
    private JCheckBox box3;

    public ColorPanelEditorPanel(PropertyEditorSupport ed){
        editor = ed;
        editor.addPropertyChangeListener(this);

        panel = new JPanel();
        add(panel);

        label = new JLabel("WYBIERZ KOLOR TLA");
        box1 = new JCheckBox("CZERWONY");
        box2 = new JCheckBox("ZIELONY");
        box3 = new JCheckBox("NIEBIESKI");

        panel.add(label);

        panel.add(box1);
        panel.add(box2);
        panel.add(box3);



        ActionListener bl = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(box1.isSelected()){
                    box2.setSelected(false);
                    box3.setSelected(false);
                    editor.setValue(new String("0xff0000"));
                }
                if(box2.isSelected()){
                    box1.setSelected(false);
                    box3.setSelected(false);
                    editor.setValue(new String("0x00ff00"));
                }
                if(box3.isSelected()){
                    box2.setSelected(false);
                    box1.setSelected(false);
                    editor.setValue(new String("0x0000ff"));
                }
                editor.firePropertyChange();
            }
        };
        box1.addActionListener(bl);
        box2.addActionListener(bl);
        box3.addActionListener(bl);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }
}
