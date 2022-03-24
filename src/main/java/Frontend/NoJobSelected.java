package Frontend;

import javax.swing.*;
import java.awt.event.*;

public class NoJobSelected extends JDialog{
    private JTextPane errorNoJobHasTextPane;
    private JPanel panel1;
    private JButton OKButton;
    private JTextField errorNoActiveJobsTextField;

    public NoJobSelected() {
        setContentPane(errorNoJobHasTextPane);
        setModal(true);
        getRootPane().setDefaultButton(OKButton);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onOK();
            }
        });

        // call onCancel() on ESCAPE
        errorNoJobHasTextPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        dispose();
    }

}
