package start;
import controllers.classes.MainController;
import controllers.interfaces.IController;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

public class NSClient {

    public static void main(String[] args) {
        try {
            new ServerSocket(7778);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame("Message"),"Notification system is already started.");
        }
        IController mainController = new MainController();
        mainController.start();
    }

}
