package utils;


import callback_impl.CallbackClientImpl;
import callback.ICallbackClient;
import callback.ICallbackServer;
import journal.IJournalManager;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Class to work with rmi registry.
 */
public class RegistryUtils {
    private static Registry registry;
    private static ICallbackServer server;
    private static ICallbackClient client;
    private static IJournalManager manager;

    private static ICallbackServer getServerInstance() {
        getRegistryInstance();
        if(server == null)
        {
            try {
                server = (ICallbackServer) registry.lookup("IAuthorizationService");
            } catch (RemoteException | NotBoundException e) {
                server = null;
            }
        }
        return server;
    }
    public static IJournalManager getJournalManagerInstance(String login)
    {
        getRegistryInstance();
        if(manager == null)
        {
            try {
                manager = (IJournalManager) registry.lookup(login);
            } catch (RemoteException | NotBoundException e) {
                manager = null;
            }
        }
        return manager;
    }

    private static void getRegistryInstance() {
        if(registry == null)
        {
            try {
                registry = LocateRegistry.getRegistry("localhost", 7777);

            } catch (RemoteException e) {
                registry = null;
            }
        }
    }

    public static boolean registerNSystem(String login, String pass) throws RemoteException {
        getServerInstance();
        if(client == null)
        {
            client = new CallbackClientImpl(login, pass);
            return server.registerNotificationSystem(client);

        }
        else {
            return server.registerNotificationSystem(client);
        }

    }
    public static void unregisterNSystem()
    {
        getServerInstance();
        if(client != null)
        {
            try {
                server.unregisterNotificationSystem(client);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean newUser(String login, String pass) throws RemoteException {
        getServerInstance();
        if(server != null)
        {
            return server.newUser(new CallbackClientImpl(login, pass));
        }
        return false;
    }
}
