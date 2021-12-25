package Frontend;

import Backend.DataMunging;
import Backend.Job;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class MainScreen{
    private DefaultListModel<String> jobList;
    private JFrame frame;
    private JPanel p;
    private JComboBox dataComboBox;
    private JButton newButton;
    private JButton deleteButton;
    private JButton timerButton;
    private JList jobDetails;
    private JButton refresherButton;
    private JTextArea jobInformationTextArea;
    private JButton button1;
    private JButton button2;
    private JButton button4;
    private JTextField textField1;

    private Main pathLink;
    private Job activeJob;

    public MainScreen() {
        jobList = new DefaultListModel<String>();
        jobDetails = new JList(jobList);
        dataComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED) {
                    updateJobDetails((String) e.getItem());
                }
            }
        });
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Job newJobObject = new Job();
                NewJob dialog = new NewJob(newJobObject);
                dialog.pack();
                dialog.setVisible(true);
                pathLink.addData(newJobObject);


            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean confirm = false;
                DeleteJob dialog = new DeleteJob(confirm);
                dialog.pack();
                dialog.setVisible(true);
                pathLink.deleteData(activeJob);
            }
        });
    }


    public void setUp(){
        frame = new JFrame("Workspace Timer");
        frame.setContentPane(p);
        Dimension newDimension = new Dimension();
        newDimension.setSize(500,500);
        frame.setPreferredSize(newDimension);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
    }
    public void dataComboBoxRefresh(String[] entries){

        dataComboBox.removeAllItems();
        for (String s : entries) {
            dataComboBox.insertItemAt(s, dataComboBox.getItemCount());
        }
        refresh();
    }

    public void setVisible(boolean b){
        frame.setVisible(b);
    }

    private void updateJobDetails(String name){
        activeJob = (DataMunging.pullJSON("test.json").fetch(name));
        if(activeJob != null) {
            String jobInfo = activeJob.toReadableString();
            jobInformationTextArea.setText(jobInfo);
            refresh();

        }
    }

    private void refresh(){
        frame.revalidate();
        frame.repaint();
    }

    public void setPathLink(Main pathLink) {
        this.pathLink = pathLink;
    }

    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();
        Main main = new Main(mainScreen);
        mainScreen.setPathLink(main);

    }
}
