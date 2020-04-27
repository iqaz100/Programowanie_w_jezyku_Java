package JavaBean.SwingClasses;

import JavaBean.ClockBean;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Timer;

import static java.lang.Thread.sleep;

public class Counter extends JPanel implements Runnable, ActionListener {

    private Timer timer;
    private Alarm task;
    private Date endTime;
    private Date count = new Date();
    private boolean running = true;
    private boolean closed = false;
    private ClockBean panel;
    private JLabel text = new JLabel("00:00:00");
    private JButton cancel = new JButton("Wyłącz");
    private JLabel descriptionLabel = new JLabel(" ");

    public Counter(ClockBean panel, Date time, String description) {
        super(new GridLayout(1,2,5,5));
        this.endTime = time;
        this.panel = panel;
        add(text);
        add(cancel);
        add(descriptionLabel);
        cancel.addActionListener(this);
        Thread thread = new Thread(this);
        thread.start();
        timer = new Timer();
        task = new Alarm(panel, timer, this, description);
        timer.schedule(task, time);
        descriptionLabel.setText(description);
    }


    public void end(){
        running = false;
        task.endAlarm();
        panel.getClockPanel().remove(this);
        
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getEndTimeMin() {
        return endTime.getMinutes();
    }

    public void setEndTimeMin(int endTime) {
        this.endTime.setMinutes(endTime);
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setFonts(Font font)
    {
        text.setFont(font);
        cancel.setFont(font);
    }

    @Override
    public void run() {
        while (running) {
            try {
                sleep(1000);
                if(new Date().after(endTime) && closed) end();
                count.setHours(endTime.getHours()-new Date().getHours());
                count.setMinutes(endTime.getMinutes()-new Date().getMinutes());
                count.setSeconds(endTime.getSeconds()-new Date().getSeconds());
                String h, m, s, msg;
                if(count.getHours()>9) h=""+count.getHours(); else h="0"+count.getHours();
                if(count.getMinutes()>9) m=""+count.getMinutes(); else m="0"+count.getMinutes();
                if(count.getSeconds()>9) s=""+count.getSeconds(); else s="0"+count.getSeconds();
                msg = h + ":" + m + ":" + s;
                text.setText(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(cancel)) {
            end();
        }
    }
}
