package utils;


import client_impl.Client;
import client.IClient;
import server.IServer;
import journal.IJournalManager;

import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class to work with rmi registry.
 */
public class RegistryUtils {
    private static Registry registry;
    private static IServer server;
    private static IClient client;
    private static IJournalManager manager;

    private static IServer getServerInstance() throws RemoteException, NotBoundException {
        getRegistryInstance();
        if(server == null)
        {
            server = (IServer) registry.lookup("IAuthorizationService");
        }
        return server;
    }
    public static IJournalManager getJournalManagerInstance(String login) throws RemoteException, NotBoundException {
        getRegistryInstance();
        if(manager == null)
        {
            manager = (IJournalManager) registry.lookup(login);
        }
        return manager;
    }

    private static void getRegistryInstance() throws RemoteException {
        if(registry == null)
        {
            registry = LocateRegistry.getRegistry("localhost", 7777);
        }
    }

    public static boolean registerNSystem(String login, String pass) throws RemoteException, NotBoundException {
        getServerInstance();
        if(client == null)
        {
            client = new Client(login, pass);
            if(server != null) {
                return server.registerNotificationSystem(client);
            }
            else {
                JOptionPane.showMessageDialog(new JFrame(),"Server are not available");
                return false;
            }

        }
        else {
            return server.registerNotificationSystem(client);
        }

    }
    public static void unregisterNSystem() throws RemoteException, NotBoundException {
        getServerInstance();
        if(client != null)
        {
                server.unregisterNotificationSystem(client);
        }
    }
    public static boolean newUser(String login, String pass) throws RemoteException, NotBoundException {
        getServerInstance();
        if(server != null)
        {
            return server.newUser(new Client(login, pass));
        }
        return false;
    }
}
