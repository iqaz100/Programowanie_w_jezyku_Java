package JavaBean;

import JavaBean.SwingClasses.ClockPanel;
import JavaBean.SwingClasses.Counter;
import JavaBean.SwingClasses.MyTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.beans.*;
import java.io.Serializable;
import java.util.Date;


public class ClockBean extends JPanel implements ActionListener, Serializable, PropertyChangeListener {

    private MyTimer myTimer;
    public JButton startButton = new JButton("Start");
    private Font myFont = new Font("Arial", Font.PLAIN, 14);
    private Color myBackground = Color.decode("0x0517EC");
    private ClockPanel clockPanel;
    private String title = "TIMER";
    private String defaultMessage = "WSTAWAJ";
    private int titlePosition;
    private int maxLengthMessage = 10;

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetoes = new VetoableChangeSupport(this);

    public ClockBean() throws PropertyVetoException {
        super(new BorderLayout(10,10));
        startButton.addActionListener(this);

        setMaxLengthMessage(10);

        clockPanel = new ClockPanel(this);
        myTimer = new MyTimer(this);

        setMyBackground("0x9198EB");
        setTitle("Twoja nazwa budzika");
        setMyFonts(myFont.getFontName());
        setDefaultMessage("Wstawaj");

        //setTitlePosition(LEFT);

        addPropertyChangeListener(this);

        add(clockPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.PAGE_END);
        add(myTimer, BorderLayout.PAGE_START);
    }

    public String getMyBackground() {
        String r, g, b;
        if (myBackground.getRed()<16) r = Integer.toHexString(myBackground.getRed()) + "0"; else r = Integer.toHexString(myBackground.getRed());
        if (myBackground.getGreen()<16) g = Integer.toHexString(myBackground.getGreen()) + "0"; else g = Integer.toHexString(myBackground.getGreen());
        if (myBackground.getBlue()<16) b = Integer.toHexString(myBackground.getBlue()) + "0"; else b = Integer.toHexString(myBackground.getBlue());
        return ("0x" + r + g + b);
    }

    public ClockPanel getClockPanel() {
        return clockPanel;
    }

    public void setMyBackground(String color) throws PropertyVetoException {
        String oldColor = this.myBackground.toString();
        vetoes.fireVetoableChange("myBackground", oldColor, color);
        myBackground = Color.decode(color);
        setBackground(myBackground);
        clockPanel.setBackg(myBackground);
    }

    public void setTitle(String str) {
        String oldTitle = getTitle();
        this.title = str;
        this.clockPanel.setTitle(str);
        changes.firePropertyChange("title", oldTitle, str);
    }

    public String getTitle() {
        return title;
    }


    public void setMyFonts(String strFont){
        Font font = new Font(strFont, Font.PLAIN, 14);
        this.myFont = font;
        startButton.setFont(font);
        clockPanel.setFonts(font.deriveFont(14f));
        myTimer.setFont(font.deriveFont(24f));
    }

    public String getMyFonts() {
        return myFont.getFontName();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(startButton)) {
            {
                    Date time = new Date();
                    time.setHours(clockPanel.getSelectedHour());
                    time.setMinutes(clockPanel.getSelectedMinute());
                    time.setSeconds(clockPanel.getSelectedSecond());

                    if(!new Date().before(time)) time.setDate(new Date().getDate()+1);
                    Counter cnt = new Counter(this, time, clockPanel.getDescription());
                    cnt.setFonts(myFont);
                    clockPanel.add(cnt);
            }
        }
    }

    public static void main(String[] args) throws PropertyVetoException {
        JFrame window = new JFrame();
        ClockBean clock = new ClockBean();
        window.setTitle("Budzik");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(300, 400);
        window.setContentPane(clock);
        window.setVisible(true);
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }

    public void setDefaultMessage(String defaultMessage) {
        String inputString = defaultMessage;
        String tempString = inputString.substring(0, Math.min(maxLengthMessage, inputString.length()));
        this.clockPanel.setDescriptionLabel(tempString);
        this.defaultMessage = tempString;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

    public int getMaxLengthMessage() {
        return maxLengthMessage;
    }

    public void setMaxLengthMessage(int maxLengthMessage) {
        this.maxLengthMessage = maxLengthMessage;
    }
}
