package JavaBean.SwingClasses;

import JavaBean.ClockBean;
import javax.swing.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;

public class MyTimer extends JLabel implements Runnable {

    private String time;

    public MyTimer(ClockBean panel) {
        super(LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss")), SwingConstants.CENTER);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(1000);
                time = LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm:ss"));
                super.setText(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
