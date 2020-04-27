package JavaBean.SwingClasses;

import JavaBean.ClockBean;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm extends TimerTask
{
    private ClockBean panel;
    private Timer timer;
    private Counter counter;
    private String descriptionAlarm = "";

    public Alarm(ClockBean panel, Timer timer, Counter counter, String description) {
        this.panel = panel;
        this.timer = timer;
        this.counter = counter;
        descriptionAlarm=description;
    }

    public void endAlarm(){
        timer.cancel();
    }

    public void run()
    {
        Toolkit.getDefaultToolkit().beep();
        String[] options = {"Drzemka", "Wyłącz alaram"};
        int x = JOptionPane.showOptionDialog(panel, descriptionAlarm, "Budzik",  JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if(x==1) { endAlarm(); counter.setClosed(true); }
        else {
            counter.setEndTimeMin(counter.getEndTimeMin()+1);
            timer.schedule(new Alarm(panel, timer, counter, descriptionAlarm), counter.getEndTime());
        }
    }
}