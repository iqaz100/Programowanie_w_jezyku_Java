package sample;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Main extends JFrame implements ActionListener {

    private JPanel panel = new JPanel();
    private JButton chooseFile = new JButton("Wybierz obraz");
    private JButton chooseScript = new JButton("Wybierz skrypt");
    private JButton submit = new JButton("Zatwierdz");
    public BufferedImage loadedImage;
    public BufferedImage editedImage;
    private ImageIcon imageIcon = new ImageIcon();
    private ImageIcon newImageIcon = new ImageIcon();
    private JLabel oldLabel = new JLabel();
    private JLabel newLabel = new JLabel();
    ScriptEngineManager sem = new ScriptEngineManager();
    ScriptEngine engine = sem.getEngineByName("nashorn");

    File workingDirectory = new File(System.getProperty("user.dir"));
    File image;
    File script;

    public Main(){
        super("Image Editor");
        add(panel);
        setSize(500,300);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel.add(chooseFile);
        panel.add(chooseScript);
        panel.add(submit);
        panel.add(oldLabel);
        panel.add(newLabel);

        chooseFile.addActionListener(this);
        chooseScript.addActionListener(this);
        submit.addActionListener(this);
    }

    public static void main(String[] args)  {
        new Main();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source  = e.getSource();

        if(source.equals(chooseFile)){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(workingDirectory);
            int c = fileChooser.showOpenDialog(new JDialog());

            if(c == JFileChooser.APPROVE_OPTION){
                image = fileChooser.getSelectedFile();
                try {
                    loadedImage = ImageIO.read(new File(image.getPath()));
                    editedImage = ImageIO.read(new File(image.getPath()));
                    imageIcon.setImage(loadedImage);
                    newImageIcon.setImage(editedImage);
                    oldLabel.setIcon(imageIcon);
                    newLabel.setIcon(newImageIcon);
                    repaint();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }

        if(source.equals(chooseScript)){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setCurrentDirectory(workingDirectory);
            int c = fileChooser.showOpenDialog(new JDialog());

            if(c == JFileChooser.APPROVE_OPTION){
                script = fileChooser.getSelectedFile();
                try {
                    engine.eval(new FileReader(script));
                } catch (ScriptException scriptException) {
                    scriptException.printStackTrace();
                } catch (FileNotFoundException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
        }

        if(source.equals(submit)){
            Invocable invocable = (Invocable) engine;

            try {
                invocable.invokeFunction("jsfunc", editedImage);
            } catch (ScriptException scriptException) {
                scriptException.printStackTrace();
            } catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            }
            repaint();
        }
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
        g.drawImage(loadedImage,200,200,200,200,0,0,loadedImage.getWidth(),loadedImage.getHeight(),null);
    }
}
