package sample;

import javax.xml.bind.Marshaller;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


public interface Processor {
    boolean submitTask(String task) throws UnsupportedEncodingException, NoSuchAlgorithmException, InterruptedException;

    String getInfo();

    String getResult();
}
