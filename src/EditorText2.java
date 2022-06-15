import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class EditorText2 extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontSizeSpinner;
    JLabel fontLabel;

    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;


    EditorText2(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Redactor text 2");
        this.setSize(750,800); //marimea initiala a ferestrei redactorului
        this.setLayout(new FlowLayout());



        textArea = new JTextArea();
        textArea.setPreferredSize(new Dimension(700, 650)); // marimea zonei de redactare a textului
        textArea.setLineWrap(true); // perminte incadrarea in rand
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Times New Roman", Font.PLAIN,12));

        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 650));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        fontLabel = new JLabel("Mărimea fontului: ");

        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(40,25));
        fontSizeSpinner.setValue(12);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN, (Integer) fontSizeSpinner.getValue()));
            }
        });



        // ---Meniul--- \\

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);

        // ---Meniul--- \\
        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSizeSpinner);
        this.add(scrollPane);
        this.setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==openItem){

            JFileChooser fileChooser =  new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Fișiere text", "txt");
            fileChooser.setFileFilter(filter);

            int r = fileChooser.showOpenDialog(null);

            if (r == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()){
                        while (fileIn.hasNextLine()){
                            String line = fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fileIn.close();
                }
            }

        }
        if (e.getSource()==saveItem){
            JFileChooser fileChooser =  new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Fișiere text", "txt");
            fileChooser.setFileFilter(filter);

            int r = fileChooser.showSaveDialog(null);

            if (r == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                finally {
                    fileOut.close();
                }
            }
        }
        if (e.getSource()==exitItem){
            System.exit(0);
        }
    }
}
