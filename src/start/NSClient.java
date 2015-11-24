package start;
import controllers.classes.MainController;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;

public class NSClient {

    public static void main(String[] args) {
        try {
            new ServerSocket(7778);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(new JFrame("Message"),"Only one notification system can be started on one computer.");
        }
        MainController mainController = new MainController();
        mainController.start();
    }

}
