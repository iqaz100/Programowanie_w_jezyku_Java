package JavaBean.SwingClasses;

import JavaBean.ClockBean;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class ClockPanel extends JPanel implements Serializable {

    private DefaultComboBoxModel<Integer> comboHours = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<Integer> comboMinutes = new DefaultComboBoxModel<>();
    private DefaultComboBoxModel<Integer> comboSeconds = new DefaultComboBoxModel<>();

    private JComboBox<Integer> hours = new JComboBox<>(comboHours);
    private JComboBox<Integer> minutes = new JComboBox<>(comboMinutes);
    private JComboBox<Integer> seconds = new JComboBox<>(comboSeconds);

    private JLabel title = new JLabel("Twoja nazwa budzika", SwingConstants.CENTER);

    private JLabel h = new JLabel("Godzina:", SwingConstants.CENTER);
    private JLabel min = new JLabel("Minuta:", SwingConstants.CENTER);
    private JLabel sec = new JLabel("Sekunda:", SwingConstants.CENTER);
    private JLabel descriptionLabel = new JLabel("Opis alarmu", SwingConstants.CENTER);
    private JTextField description = new JTextField("", SwingConstants.CENTER);


    public ClockPanel(ClockBean panel) {
        super(new GridLayout(0,1,5,5));

        for(int i = 0; i <= 23; i++) comboHours.addElement(i);
        for(int i = 0; i <= 59; i++) {comboMinutes.addElement(i);  comboSeconds.addElement(i);}

        add(title);
        add(h);
        add(hours);
        add(min);
        add(minutes);
        add(sec);
        add(seconds);
        add(descriptionLabel);
        add(description);
        description.setText(panel.getDefaultMessage());
    }

    public void setFonts(Font font){
        title.setFont(font);
        h.setFont(font);
        min.setFont(font);
        sec.setFont(font);
        hours.setFont(font);
        minutes.setFont(font);
        seconds.setFont(font);
    }

    public void setBackg(Color color){
        setBackground(color.darker());
    }

    public int getSelectedHour(){
        return (int) hours.getSelectedItem();
    }

    public int getSelectedMinute(){
        return (int) minutes.getSelectedItem();
    }

    public int getSelectedSecond(){
        return (int) seconds.getSelectedItem();
    }

    public String getDescription(){return (String) description.getText();}

    public void setTitle(String title) {
        this.title.setText(title);
    }

    public void setDescriptionLabel(String descriptionMessage){
        description.setText(descriptionMessage);
    }
}
