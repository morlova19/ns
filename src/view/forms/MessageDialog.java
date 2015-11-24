package view.forms;

import controllers.interfaces.ITaskController;
import journal.Task;
import utils.*;
import utils.Icon;
import view.interfaces.IMessageView;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.Date;

/**
 * GUI to display notification about the task.
 */
public class MessageDialog extends JDialog implements IMessageView,ActionListener {
    /**
     * Constant for actionCommand.
     */
    private static final String DELAY_ACTION = "delay";
    /**
     * Constant for actionCommand.
     */
    private static final String COMPLETE_ACTION = "complete";
    /**
     * Constant for error description_tf that will be displayed if the entered date is not correct.
     */
    public static final String ENTER_DATE_MSG = "Please enter correct date of task";
    /**
     * Container for components of this form.
     */
    private JPanel contentPane;
    /**
     * Button to delay the task.
     */
    private JButton delayButton;
    /**
     * Button to complete the task.
     */
    private JButton completeButton;
    /**
     * Component for displaying the details of the task.
     */
    private JTextArea description_tf;
    /**
     * Component for selecting new date of the task.
     */
    private JFormattedTextField dateField;
    /**
     * Label to display error description_tf if the entered date is not correct.
     */
    private JLabel err_label;
    /**
     * Component for task's name.
     */
    private JTextField name_tf;
    /**
     * Component for task's contacts.
     */
    private JTextArea contacts_tf;
    /**
     * Component for new task's date .
     */
    private JLabel date_label;
    /**
     * Provides methods to work with data.
     */
    private ITaskController controller;

    /**
     * Identifier of task about which shows notification.
     */
    private int id;
    /**
     * Creates new dialog window.
     * @param c controllers.
     */
    public MessageDialog(ITaskController c) {
        this.controller = c;
    }
    /**
     * Configures {@link #dateField}.
     */
    private void configDateField() {
        MaskFormatter dateFormatter = null;
        try {
            dateFormatter = new MaskFormatter("##.##.#### ##:##");
            dateFormatter.setPlaceholderCharacter('_');

        } catch (ParseException e) {

        }
        DefaultFormatterFactory dateFormatterFactory = new
                DefaultFormatterFactory(dateFormatter);
        dateField.setFormatterFactory(dateFormatterFactory);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand())
        {
            case DELAY_ACTION:
                delay();
                break;
            case COMPLETE_ACTION:
                complete();
                break;
        }
    }
    /**
     * Informs controllers about that user wants to delay the task.
     */
    private void delay() {
        String newDate = (String) dateField.getValue();
        Date date = DateUtil.parse(newDate);
        if(controller != null) {
            controller.delay(id, date);
        }
    }
    /**
     * Informs controllers about that user wants to complete the task.
     */
    private void complete() {
        if(controller != null) {
            controller.complete(id);
        }
    }

    @Override
    public void open() {
        pack();
        setVisible(true);
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void displayTaskName(String name) {
        name_tf.setEditable(false);
        name_tf.setText(name);
    }

    @Override
    public void displayTaskDesc(String desc) {
        description_tf.setEditable(false);
        description_tf.setText(desc);
    }

    @Override
    public void displayTaskDate(String date) {
        dateField.setValue(date);
    }

    @Override
    public void displayTaskContacts(String contacts) {
        contacts_tf.setEditable(false);
        contacts_tf.setText(contacts);
    }

    @Override
    public void showError(String error, int component) {
        if(component == Constants.DATE)
        {
            err_label.setText(error);
            dateField.setBorder(BorderFactory.createLineBorder(Color.red));
        }
    }
    @Override
    public void resetError(int component) {
        if(component == Constants.DATE)
        {
            err_label.setText("");
            dateField.setBorder(UIManager.getBorder("TextField.border"));
        }
    }

    @Override
    public void createView() {
        setContentPane(contentPane);
        setTitle("Notification");
        setIconImage(Icon.getIcon());
        date_label.setText("<html>New date<br>(dd.mm.yyyy hh:mm)</html>");
        configDateField();

        err_label.setForeground(Color.red);

        delayButton.setActionCommand(DELAY_ACTION);
        delayButton.addActionListener(this);

        completeButton.addActionListener(this);
        completeButton.setActionCommand(COMPLETE_ACTION);

        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        setResizable(false);
        setAlwaysOnTop(true);
        setModal(true);
    }
}
