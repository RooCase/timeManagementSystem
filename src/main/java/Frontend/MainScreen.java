/* Code by Roo Case, personal project
*  This class is way too big. It interfaces with JavaFX, but also contains code for internal workings, that would
* be better contained in a smaller class.*/
package Frontend;

import Backend.DataMunging;
import Backend.Job;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.TimerTask;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

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
    private JButton addTimeToJobButton;
    private JTextArea jobInformationTextArea;
    private JButton clearButton;
    private JButton startButton;
    private JTextField textField1;
    private JTextField timerTimeTextField = textField1;

    private static boolean activeJobSession = false;
    private Main pathLink;
    private Job activeJob;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Timer timer;

    private double minutes = 0;
    private double finishedTimerMinutes = 0;


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
                updateJobDetails("");
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!activeJobSession){
                    startButton.setText("Stop");
                    activeJobSession = true;
                    startTimer();
                }else{
                    activeJobSession = false;
                    startButton.setText("Start");
                    finishedTimerMinutes = 0;
/*                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }*/
                }
            }
        });

        addTimeToJobButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(activeJob != null){
                    pathLink.addTime(activeJob, (int) minutes);
                    minutes = 0;
                    updateTimerCount();
                    updateJobDetails(activeJob.getTitle());
                }else{
                    NoJobSelected dialog = new NoJobSelected();
                    dialog.pack();
                    dialog.setVisible(true);
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                minutes = 0;
                updateTimerCount();
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
        updateTimerCount();
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
        activeJob = (pathLink.fetchJob(name));
        if(activeJob != null) {
            String jobInfo = activeJob.toReadableString();
            jobInformationTextArea.setText(jobInfo);
            refresh();

        }
        else{
            jobInformationTextArea.setText("");
            refresh();
        }
    }

    private void updateTimerCount(){
        String hours = String.valueOf((int) minutes / 60);
        String remainingMinutes = String.valueOf((int) minutes % 60);
        if(Integer.valueOf(remainingMinutes) < 10) {
            remainingMinutes = "0" + remainingMinutes;
        }
        timerTimeTextField.setText(hours + ":" + remainingMinutes);
    }

    private void refresh(){
        frame.revalidate();
        frame.repaint();
    }

    public void startTimer() {
        timer = new Timer();

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (activeJobSession) {
                    minutes++;
                    updateTimerCount();
                } else {
                    timer.cancel();
                }
            }
        };

        timer.schedule(task, 1000, 1000);
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