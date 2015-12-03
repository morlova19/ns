package view.forms;

import controllers.interfaces.ILoginController;
import listeners.CustomKeyListener;
import utils.Constants;
import view.interfaces.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for registration.
 */
public class RegistrationForm extends JDialog implements IView,ActionListener, CustomKeyListener {
    public static final String SUBMIT = "submit";
    public static final String CANCEL = "cancel";
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField loginTextField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JLabel login_err_label;
    private JLabel pass_err_label;
    private JLabel conf_pass_err_label;
    private ILoginController controller;

    public RegistrationForm(ILoginController controller) {
        this.controller = controller;
    }

    @Override
    public void createView() {
        setContentPane(contentPane);
        setTitle("Registration");
        setIconImage(utils.Icon.getIcon());
        setModal(true);

        buttonOK.addActionListener(this);
        buttonOK.setActionCommand(SUBMIT);
        buttonCancel.setActionCommand(CANCEL);
        buttonCancel.addActionListener(this);

        passwordField.addKeyListener(this);
        conf_pass_err_label.addKeyListener(this);

        login_err_label.setForeground(Color.red);
        pass_err_label.setForeground(Color.red);
        conf_pass_err_label.setForeground(Color.red);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    @Override
    public void clearView() {
        loginTextField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        login_err_label.setText("");
        pass_err_label.setText("");
        conf_pass_err_label.setText("");
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
    public void showError(String error, int component) {
        switch (component) {
            case Constants.LOGIN:
                showLoginError(error);
                break;
            case Constants.PASS:
                showPassError(error);
                break;
            case Constants.CONFIRM_PASS:
                showConfirmPassError(error);
                break;

        }
    }
    public void resetError(int component) {
        switch (component)
        {
            case Constants.LOGIN:
                resetLoginError();
                break;
            case Constants.PASS:
                resetPassError();
                break;
            case Constants.CONFIRM_PASS:
                resetConfirmPassError();
                break;
        }
    }

    public void showPassError(String error) {
        passwordField.setBorder(BorderFactory.createLineBorder(Color.red));
        pass_err_label.setText(error);
        pack();
    }
    public void showLoginError(String error) {
        loginTextField.setBorder(BorderFactory.createLineBorder(Color.red));
        login_err_label.setText(error);
        pack();
    }

    public void resetLoginError() {
        loginTextField.setBorder(UIManager.getBorder("TextField.border"));
        login_err_label.setText("");
        pack();

    }
    public void resetPassError() {
        passwordField.setBorder(UIManager.getBorder("TextField.border"));
        pass_err_label.setText("");
        pack();
    }
    public void showConfirmPassError(String error) {
        if(!error.equals(Constants.USER_ALREADY_EXISTS)) {
            confirmPasswordField.setBorder(BorderFactory.createLineBorder(Color.red));
        }
        conf_pass_err_label.setText(error);
        pack();
    }

    public void resetConfirmPassError() {
        confirmPasswordField.setBorder(UIManager.getBorder("TextField.border"));
        conf_pass_err_label.setText("");
        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand())
        {
            case SUBMIT:
                controller.register(loginTextField.getText(),
                        passwordField.getPassword(),
                        confirmPasswordField.getPassword());
                break;
            case CANCEL:
                close();
                break;

        }
    }

}
