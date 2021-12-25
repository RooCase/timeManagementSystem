package Frontend;

import Backend.DataMunging;
import Backend.Job;
import Backend.Timer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class MainScreen{
    private DefaultListModel<String> jobList;
    private JFrame frame;
    private JPanel p;
    private JComboBox dataComboBox;
    private JButton newButton;
    private JButton deleteButton;
    private JTextField timerTextField;
    private JList jobDetails;
    private JButton refresherButton;
    private JTextArea jobInformationTextArea;
    private JButton button1;
    private JButton startButton;
    private JButton button4;
    private JTextField textField1;
    private JTextField timerTimeTextField = textField1;

    private static boolean activeJobSession;
    private Timer timer;
    private Main pathLink;
    private Job activeJob;
    private double minutes;

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
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(startButton.getText() == "Start"){
                    startButton.setText("Stop");
                    activeJobSession = true;

                }else{
                    activeJobSession = false;
                    startButton.setText("Start");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    System.out.println(Timer.getMinutes());
                }
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
        timerTextField.setEditable(false);
        timerTextField.setHorizontalAlignment(SwingConstants.CENTER);
        jobInformationTextArea.setEditable(false);
        timerTimeTextField.setEditable(false);
        timerTimeTextField.setHorizontalAlignment(SwingConstants.CENTER);
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

    private void updateTimerCount(){
        String hours = String.valueOf((int) minutes / 60);
        String remainingMinutes = String.valueOf((int) minutes % 60);
        timerTimeTextField.setText(hours + ":" + remainingMinutes);
    }

    private void refresh(){
        frame.revalidate();
        frame.repaint();
    }

    public static Boolean getActiveJobSession() {
        return activeJobSession;
    }

    public void setPathLink(Main pathLink) {
        this.pathLink = pathLink;
    }

    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();
        Main main = new Main(mainScreen);
        mainScreen.setPathLink(main);

    }

    private void timer(){
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final ScheduledExecutorService stopper = Executors.newScheduledThreadPool(1);

        minutes = 0;
        Runnable increment = () -> minutes++; updateTimerCount();
        ScheduledFuture<?> scheduleHandle = scheduler.scheduleAtFixedRate(increment,
                1, 1, SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                if(activeJobSession){
                    scheduleHandle.cancel(true);
                }
            }
        }, 1,  SECONDS);

    }
}
