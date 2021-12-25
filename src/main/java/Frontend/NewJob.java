package Frontend;

import Backend.Job;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NewJob extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField titleInput;
    private JTextArea Title;
    private JTextArea clientNameTextArea;
    private JTextField clientInput;
    private JTextField hoursWorkedInput;
    private JTextArea hoursAlreadyWorkedDecimalTextArea;
    private JTextField billedPerHourInput;
    private JTextArea amountBilledPerHourTextArea;
    private JTextArea createNewJobTextArea;
    private Job newJob;

    public NewJob(Job newJob) {
        this.newJob = newJob;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        Title.setEditable(false);
        clientNameTextArea.setEditable(false);
        hoursAlreadyWorkedDecimalTextArea.setEditable(false);
        amountBilledPerHourTextArea.setEditable(false);
        hoursAlreadyWorkedDecimalTextArea.setLineWrap(true);
        createNewJobTextArea.setEditable(false);
        contentPane.setPreferredSize(new Dimension(350, 250));

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        Boolean valid = true;
        Double hoursWorked = null;
        Double billedPerHour = null;
        try{
            hoursWorked = Double.valueOf(Float.valueOf(hoursWorkedInput.getText()));
            billedPerHour =  Double.valueOf(billedPerHourInput.getText());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            valid =false;
        } catch (NullPointerException e){
            e.printStackTrace();
            valid = false;
        }

        if(valid) {
            newJob.setAll(titleInput.getText(), clientInput.getText(), hoursWorked, billedPerHour);
            dispose();
        }else{
            createNewJobTextArea.setText("There's something wrong with the numbers you entered. Please check again.");
        }
    }

    private void onCancel() {
        dispose();
    }

}
