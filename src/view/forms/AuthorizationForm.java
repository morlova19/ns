package view.forms;

import controllers.interfaces.ILoginController;
import listeners.CustomKeyListener;
import utils.*;
import view.interfaces.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI for authorization.
 */
public class AuthorizationForm extends JFrame implements IView, ActionListener, CustomKeyListener {
    public static final String SIGN_IN = "Sign in";
    public static final String SIGN_UP = "Sign up";
    private JPanel main;
    private JPasswordField passwordField;
    private JTextField loginTextField;
    private JLabel login_err_label;
    private JLabel pass_err_label;
    private JButton signInButton;
    private JButton signUpButton;
    private ILoginController controller;

    public AuthorizationForm(ILoginController controller) throws HeadlessException {
        this.controller = controller;
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
        switch (component)
        {
            case Constants.LOGIN:
                showLoginError(error);
                break;
            case Constants.PASS:
                showPassError(error);
                break;
        }
    }

    public void showPassError(String error) {
        if(!error.equals(Constants.INCORRECT_LOGIN_OR_PASS)) {
            passwordField.setBorder(BorderFactory.createLineBorder(Color.red));
        }
        pass_err_label.setForeground(Color.red);
        pass_err_label.setText(error);
        pack();
    }
    public void showLoginError(String error) {
        loginTextField.setBorder(BorderFactory.createLineBorder(Color.red));
        login_err_label.setForeground(Color.red);
        login_err_label.setText(error);
        pack();
    }
    public void resetError(int component)
    {
        switch (component)
        {
            case Constants.LOGIN:
                resetLoginError();
                break;
            case Constants.PASS:
                resetPassError();
                break;
        }
    }

    @Override
    public void createView() {
        setContentPane(main);
        setTitle("Authorization");
        setIconImage(utils.Icon.getIcon());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        signInButton.addActionListener(this);
        signUpButton.addActionListener(this);
        signInButton.setActionCommand(SIGN_IN);
        signUpButton.setActionCommand(SIGN_UP);

        passwordField.addKeyListener(this);
        setLocationRelativeTo(null);
    }

    @Override
    public void clearView() {
        loginTextField.setText("");
        passwordField.setText("");
        login_err_label.setText("");
        pass_err_label.setText("");
    }

    public void resetLoginError() {
        loginTextField.setBorder(UIManager.getBorder("TextField.border"));
        login_err_label.setForeground(Color.red);
        login_err_label.setText("");
        pack();

    }


    public void resetPassError() {
        passwordField.setBorder(UIManager.getBorder("TextField.border"));
        pass_err_label.setForeground(Color.red);
        pass_err_label.setText("");
        pack();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand())
        {
            case SIGN_IN:
                controller.authorize(loginTextField.getText(),passwordField.getPassword());
                break;
            case SIGN_UP:
                controller.open_registration_dialog();
                break;
        }
    }
}
