package editor;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;


public class TextEditor extends JFrame {
    
    public TextEditor() {
        JFileChooser jFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jFileChooser.setName("FileChooser");
        Findtext ft = new SimpleFind();
        Findtext ft1 = new RegexFind();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setVisible(true);
        setTitle("Text Editor");
        JTextField jTextField = new JTextField(40);
        jTextField.setName("SearchField");


        Dimension d = new Dimension(90, 50);
        jTextField.setMinimumSize(d);
        JTextArea jTextArea = new JTextArea();
        jTextArea.setName("TextArea");

        jTextArea.setBounds(40, 50, 260, 200);
        JScrollPane jscrollPane = new JScrollPane(jTextArea);
        jscrollPane.setName("ScrollPane");
        jscrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jscrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        getContentPane().add(jscrollPane);




        JButton jButton1 = new JButton(new ImageIcon("Text Editor/task/src/editor/open-file-icon-6653.png"));
        jButton1.setName("OpenButton");
        final String[] filename = {""};
        final File[] selectedFile = {null};

        jButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                int returnVal = jFileChooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    selectedFile[0] = jFileChooser.getSelectedFile();
                }
                assert selectedFile[0] != null;
                String fileName = selectedFile[0].getAbsolutePath();
                System.out.println(fileName);
                filename[0] = fileName;
                try {
                    jTextArea.setText(fileReader.readFile(fileName));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        JCheckBox jCheckBox = new JCheckBox("Use regex");
        jCheckBox.setName("UseRegExCheckbox");
        jCheckBox.setSelected(false);

        JButton jButton = new JButton(new ImageIcon("Text Editor/task/src/editor/save--save-icon-32310.png"));
        jButton.setName("SaveButton");


        jButton.addActionListener((e) -> {

            String myInfo = jTextArea.getText();
            try {
                fileWriter.writeFile(myInfo, filename[0]);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        });

        JButton jButton2 = new JButton(new ImageIcon("Text Editor/task/src/editor/find-icons-47632.png"));
        jButton2.setName("StartSearchButton");
        jButton2.addActionListener(actionEvent -> {
            if (jCheckBox.isSelected()){
                ft1.startFind(jTextArea.getText(), jTextField.getText());
                int index = ft1.find();
                if (index != -1) {
                    jTextArea.setCaretPosition(index + ft1.getPatternLength());
                    jTextArea.select(index, index + ft1.getPatternLength());
                    jTextArea.grabFocus();
                }
            } else {
                ft.startFind(jTextArea.getText(), jTextField.getText());
                int index = ft.find();
                if (index != -1) {
                    jTextArea.setCaretPosition(index + ft.getPatternLength());
                    jTextArea.select(index, index + ft.getPatternLength());
                    jTextArea.grabFocus();
                }
            }
        });
        JButton jButton3 = new JButton(new ImageIcon("Text Editor/task/src/editor/to-return-to--previous-page-icon-63791.png"));
        jButton3.setName("PreviousMatchButton");
        jButton3.addActionListener(actionEvent -> {
            if (jCheckBox.isSelected()){
                int index = ft1.findPrevious();
                if (index != -1) {
                    jTextArea.setCaretPosition(index + ft1.getPatternLength());
                    jTextArea.select(index, index + ft1.getPatternLength());
                    jTextArea.grabFocus();
                }
            } else {
                int index = ft.findPrevious();
                if (index != -1) {
                    jTextArea.setCaretPosition(index + ft.getPatternLength());
                    jTextArea.select(index, index + ft.getPatternLength());
                    jTextArea.grabFocus();
                }
            }
        });
        JButton jButton4 = new JButton(new ImageIcon("Text Editor/task/src/editor/next-page-arrow-icon-63792.png"));
        jButton4.setName("NextMatchButton");
        jButton4.addActionListener(actionEvent -> {
            if (jCheckBox.isSelected()){
                int index = ft1.findNext();
                if (index != -1) {
                    jTextArea.setCaretPosition(index + ft1.getPatternLength());
                    jTextArea.select(index, index + ft1.getPatternLength());
                    jTextArea.grabFocus();
                }
            } else {
                int index = ft.findNext();
                if (index != -1) {
                    jTextArea.setCaretPosition(index + ft.getPatternLength());
                    jTextArea.select(index, index + ft.getPatternLength());
                    jTextArea.grabFocus();
                }
            }
        });





        JMenuBar jMenuBar = new JMenuBar();
        JMenu MenuFile = new JMenu("File");
        MenuFile.setName("MenuFile");
        MenuFile.setMnemonic(KeyEvent.VK_F);
        JMenuItem MenuOpen = new JMenuItem("Open");
        MenuOpen.setName("MenuOpen");
        MenuOpen.addActionListener(actionEvent -> {

            int returnVal = jFileChooser.showOpenDialog(null);

            if (returnVal == JFileChooser.APPROVE_OPTION){
                selectedFile[0] = jFileChooser.getSelectedFile();
            }
            assert selectedFile[0] != null;
            String fileName = selectedFile[0].getAbsolutePath();
            filename[0] = fileName;
            System.out.println(fileName);
            try {
                jTextArea.setText(fileReader.readFile(fileName));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        JMenuItem MenuSave = new JMenuItem("Save");
        MenuSave.setName("MenuSave");
        MenuSave.addActionListener(actionEvent -> {
            String myInfo = jTextArea.getText();
            try {
                fileWriter.writeFile(myInfo, jTextField.getText());
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });

        JMenuItem MenuExit = new JMenuItem("Exit");
        MenuExit.setName("MenuExit");
        MenuExit.addActionListener(e -> dispose());

        MenuFile.add(MenuOpen);
        MenuFile.add(MenuSave);
        MenuFile.addSeparator();
        MenuFile.add(MenuExit);

        JMenu menuSearch = new JMenu("Search");
        menuSearch.setName("MenuSearch");
        menuSearch.setMnemonic(KeyEvent.VK_C);
        JMenuItem menuStartSearch = new JMenuItem("Start Search");
        menuStartSearch.setName("MenuStartSearch");

        menuStartSearch.addActionListener(actionEvent -> {
            ft.startFind(jTextArea.getText(), jTextField.getText());
            int index = ft.find();
            if (index != -1) {
                jTextArea.setCaretPosition(index + ft.getPatternLength());
                jTextArea.select(index, index + ft.getPatternLength());
                jTextArea.grabFocus();
            }
        });
        JMenuItem menuPreviousMatch = new JMenuItem("Previous Match");
        menuPreviousMatch.setName("MenuPreviousMatch");
        menuPreviousMatch.addActionListener(actionEvent -> {
            int index = ft.findPrevious();
            if (index != -1) {
                jTextArea.setCaretPosition(index + ft.getPatternLength());
                jTextArea.select(index, index + ft.getPatternLength());
                jTextArea.grabFocus();
            }

        });
        JMenuItem menuNextMatch = new JMenuItem("Next Match");
        menuNextMatch.setName("MenuNextMatch");
        menuNextMatch.addActionListener(actionEvent -> {
            int index = ft.findNext();
            if (index != -1) {
                jTextArea.setCaretPosition(index + ft.getPatternLength());
                jTextArea.select(index, index + ft.getPatternLength());
                jTextArea.grabFocus();
            }
        });

        JMenuItem menuUseRegEx = new JMenuItem("Use Regular Expressions");
        menuUseRegEx.setName("MenuUseRegExp");
        menuUseRegEx.addActionListener(actionEvent -> {
            ft1.startFind(jTextArea.getText(), jTextField.getText());
            int index = ft1.find();
            if (index != -1) {
                jTextArea.setCaretPosition(index + ft1.getPatternLength());
                jTextArea.select(index, index + ft1.getPatternLength());
                jTextArea.grabFocus();
            }
        });
        menuSearch.add(menuStartSearch);
        menuSearch.add(menuPreviousMatch);
        menuSearch.add(menuNextMatch);
        menuSearch.add(menuUseRegEx);


        jMenuBar.add(MenuFile);
        jMenuBar.add(menuSearch);
        setJMenuBar(jMenuBar);



        JPanel jPanel = new JPanel();
        jPanel.setBounds(20, 20, 500, 50);
        FlowLayout flowLayout = new FlowLayout();
        jPanel.setLayout(flowLayout);
        jPanel.add(jButton1);
        jPanel.add(jButton);
        jPanel.add(jTextField);
        jPanel.add(jButton2);
        jPanel.add(jButton3);
        jPanel.add(jButton4);
        jPanel.add(jCheckBox);

        jPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        add(jPanel, BorderLayout.NORTH);





    }

    public static void main(String[] args) {
        new TextEditor();
    }
}
