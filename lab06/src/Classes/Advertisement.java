package Classes;

import java.time.Duration;

public class Advertisement {
    public String text;
    public Duration displayTime;
    public int orderID;

    Advertisement(String text,Duration displayTime,int orderID){
        this.text = text;
        this.displayTime = displayTime;
        this.orderID = orderID;
    }

}
