package controllers.interfaces;

import observer.Observer;


public interface ILoginController extends IController{
    void authorize(String login, char[] pass);
    void register(String login, char[] pass, char[] confirmPass);
    void open_registration_dialog();
    void registerObserver(Observer observer);
}
