package main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class Panel extends JFrame implements ActionListener {

    private JPanel mainPanel;
    private JButton changePath;
    private JTextField pathField;
    private JButton previousButton;
    private JButton nextButton;
    private JLabel fileName;
    private JPanel listPanel;
    private JScrollPane scrollPane = new JScrollPane();

    private File directory;
    private int dispFile = 0;
    private List<String> fileList = new ArrayList<>();
    private List<WeakReference<JList>> fileReferenceList = new ArrayList<>();


    public Panel() throws HeadlessException {
        super("File Reader");
        add(mainPanel);
        changePath.addActionListener(this);
        previousButton.addActionListener(this);
        nextButton.addActionListener(this);
        listPanel.add(scrollPane);
    }

    //Metoda updateFileName ustawiajaca odpowiednia nazwe pliku
    private void updateFileName(){
        fileName.setText(fileList.get(dispFile).replace((directory.getPath()+"\\"),""));
    }

    //Metoda showData odpowiedzialna za pobieranie i wyswietlanie zawartosci pliku
    private void showData(String file) throws IOException {

        if(fileReferenceList.get(dispFile).get() == null)
        {
            DefaultListModel<String> listModel = new DefaultListModel<>();
            BufferedReader br = null;
            br = new BufferedReader(new FileReader(file));
            String strCurrentLine;

            while((strCurrentLine = br.readLine()) != null){
                listModel.addElement(strCurrentLine);
            }
            fileReferenceList.set(dispFile,new WeakReference<>(new JList(listModel)));
        }
        scrollPane.setViewportView(fileReferenceList.get(dispFile).get());
    }

    //Metoda actionPerformed odpowiedzialna za obsluge akcji wykonanych przez uzytkownika
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        //Obsluga funkcjonalnosci zwiazanych z wyborem sciezki do katalogu
        if (source.equals(changePath)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setDialogTitle("Choose Folder with text files");
            int ret = chooser.showOpenDialog(new JDialog());

            if (ret == JFileChooser.APPROVE_OPTION) {
                directory = chooser.getSelectedFile();
                pathField.setText(directory.getPath());

                try {
                    Files.walk(Paths.get(directory.toString()))
                            .filter(Files::isRegularFile)
                            .forEach(file -> fileList.add(file.toString()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                for (String filePath: fileList) fileReferenceList.add(new WeakReference<>(null));

                try {
                    showData(fileList.get(dispFile));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                updateFileName();
            }
        }

        //Obsluga przycisku ,,Previous" pokazujacego poprzednio wyswietlany plik
        if (source.equals(previousButton)) {
            dispFile--;
            if(dispFile < 0) dispFile = fileList.size() - 1;
            try {
                showData(fileList.get(dispFile));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            updateFileName();
        }

        //Obsluga przycisku ,,Next" pokazujacego nastepny plik w katalogu
        if (source.equals(nextButton)) {
            dispFile++;
            if(dispFile > fileList.size() - 1) dispFile = 0;
            try {
                showData(fileList.get(dispFile));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            updateFileName();
        }
    }

    public static void main(String[] args) {

        Panel panel = new Panel();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setSize(675, 500);
        panel.setResizable(false);
        panel.setVisible(true);
    }
}
