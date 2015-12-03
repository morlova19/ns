package controllers.classes;

import controllers.interfaces.ILoginController;
import observer.Observer;
import utils.Constants;
import utils.RegistryUtils;
import view.forms.AuthorizationForm;
import view.interfaces.IView;
import view.forms.RegistrationForm;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class LoginController implements ILoginController {

    private IView aView;
    private IView rView;
    private Observer observer;

    public LoginController() {
        aView = new AuthorizationForm(this);
        rView = new RegistrationForm(this);
    }

    public void start() {
        aView.createView();
        aView.open();
    }

    @Override
    public void stop() {
        try {
            RegistryUtils.unregisterNSystem();
        } catch (RemoteException | NotBoundException e) {
            JOptionPane.showMessageDialog(new JFrame(),"Cannot unregister");
        }
        finally {
            System.exit(1);
        }
    }

    public void authorize(String login, char[] pass){
        String l = login.trim();
        String p = String.copyValueOf(pass).trim();
        boolean isCorrectLogin = !l.isEmpty();
        boolean isCorrectPass = !p.isEmpty();
        if(isCorrectLogin && isCorrectPass)
        {
            aView.resetError(Constants.LOGIN);
            aView.resetError(Constants.PASS);

            boolean isAuthorized;
            try {
                    isAuthorized = RegistryUtils.registerNSystem(l, p);
                    System.out.println(isAuthorized);
                    if (isAuthorized) {
                        aView.clearView();
                        aView.close();
                        observer.update(l);
                    }
                    else {
                        aView.showError(Constants.INCORRECT_LOGIN_OR_PASS, Constants.PASS);
                    }

            } catch (RemoteException | NotBoundException e) {
                aView.showError(Constants.CANNOT_AUTHORIZE, Constants.PASS);
            }
            return;
        }
        if(!isCorrectLogin)
        {
            aView.showError(Constants.LOGIN_NOT_EMPTY, Constants.LOGIN);
        }
        else {
            aView.resetError(Constants.LOGIN);
        }
        if(!isCorrectPass)
        {

            aView.showError(Constants.PASS_NOT_EMPTY, Constants.PASS);
        }
        else {
            aView.resetError(Constants.PASS);
        }
    }
    public void register(String login, char[] pass, char[] confirmPass) {
        String l = login.trim();
        String p = String.copyValueOf(pass).trim();
        String cp = String.copyValueOf(confirmPass).trim();
        boolean isCorrectLogin = !l.isEmpty();
        boolean isCorrectPass = !p.isEmpty();
        boolean isCorrectConfirmPass = p.equals(cp);
        if(isCorrectLogin && isCorrectPass & isCorrectConfirmPass)
        {
            rView.resetError(Constants.LOGIN);
            rView.resetError(Constants.PASS);
            rView.resetError(Constants.CONFIRM_PASS);

            boolean isRegistered ;
            try {
                    isRegistered = RegistryUtils.newUser(login, p);
                    if (isRegistered) {
                        rView.clearView();
                        rView.close();

                    }
               else {
                    rView.showError(Constants.USER_ALREADY_EXISTS,Constants.CONFIRM_PASS);
                }
            } catch (RemoteException | NotBoundException e) {
                rView.showError(Constants.CANNOT_REGISTER, Constants.CONFIRM_PASS);
            }
            return;
        }

        if(!isCorrectLogin)
        {
            rView.showError(Constants.LOGIN_NOT_EMPTY, Constants.LOGIN);
        }
        else {
            rView.resetError(Constants.LOGIN);
        }
        if(!isCorrectPass)
        {
            rView.showError(Constants.PASS_NOT_EMPTY, Constants.PASS);
        }
        else {
            rView.resetError(Constants.PASS);
        }
        if(!isCorrectConfirmPass)
        {
            rView.showError(Constants.NOT_SAME_PASS, Constants.CONFIRM_PASS);
        }
        else {
            rView.resetError(Constants.CONFIRM_PASS);
        }

    }

    public void open_registration_dialog()
    {
        rView.createView();
        rView.open();
    }

    @Override
    public void registerObserver(Observer observer) {
        this.observer = observer;
    }

}
