package server;

import client.IClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
    String getPublicKey()throws RemoteException;
    boolean registerUserInterface(IClient client) throws RemoteException;
    boolean registerNotificationSystem(IClient client) throws RemoteException;
    boolean newUser(IClient client)throws RemoteException;
    boolean unregisterUserInterface(IClient client)throws RemoteException;
    boolean unregisterNotificationSystem(IClient client)throws RemoteException;
}
