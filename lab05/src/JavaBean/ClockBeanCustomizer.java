package JavaBean;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Customizer;
import java.beans.PropertyVetoException;
import java.io.Serializable;

public class ClockBeanCustomizer extends JPanel implements Customizer, Serializable, ActionListener {

    private ClockBean bean;

    private JLabel title = new JLabel("Nazwa:", SwingConstants.CENTER);
    private JLabel color = new JLabel("Kolor:", SwingConstants.CENTER);
    private JLabel font = new JLabel("Czcionka:", SwingConstants.CENTER);

    private JTextField titleField = new JTextField();
    private JTextField fontField = new JTextField();

    private JButton colorChooser = new JButton("Wybierz kolor");

    public ClockBeanCustomizer(){

        setPreferredSize(new Dimension(300, 100));
        JPanel panel = new JPanel(new GridLayout(2,3,15,15));

        titleField.setHorizontalAlignment(SwingConstants.CENTER);
        fontField.setHorizontalAlignment(SwingConstants.CENTER);

        colorChooser.addActionListener(this);

        titleField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent evt) {
                setTitle(titleField.getText());
            }

            public void insertUpdate(DocumentEvent evt) {
                setTitle(titleField.getText());
            }

            public void removeUpdate(DocumentEvent evt) {
                setTitle(titleField.getText());
            }
        });

        fontField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent evt) {
                setMyFonts(fontField.getText());
            }

            public void insertUpdate(DocumentEvent evt) {
                setMyFonts(fontField.getText());
            }

            public void removeUpdate(DocumentEvent evt) {
                setMyFonts(fontField.getText());
            }
        });

        this.add(panel);
        panel.add(title);
        panel.add(color);
        panel.add(font);
        panel.add(titleField);
        panel.add(colorChooser);
        panel.add(fontField);
        panel.setVisible(true);
    }

    public void setTitle(String newTitle) {
        if(bean == null)
            return;
        String oldValue = bean.getTitle();
        bean.setTitle(newTitle);
        firePropertyChange("title", oldValue, newTitle);
    }

    public void setMyFonts(String newValue) {
        if (bean == null)
            return;
        String oldValue = bean.getMyFonts();
        bean.setMyFonts(newValue);
    }

    @Override
    public void setObject(Object obj) {
        bean = (ClockBean) obj;
        titleField.setText(this.bean.getTitle());
        fontField.setText(this.bean.getMyFonts());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(colorChooser)) {
            if (bean == null)
                return;

            Color c = JColorChooser.showDialog(null, "Wybierz kolor", bean.getBackground());
            if (c != null) {
                String oldValue = bean.getMyBackground();
                String newValue = Integer.toString(c.getRGB());
                try {
                    fireVetoableChange("myBackground", oldValue, newValue);
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
                try {
                    bean.setMyBackground(newValue);
                } catch (PropertyVetoException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}