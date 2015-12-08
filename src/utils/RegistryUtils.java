package utils;


import client_impl.Client;
import client.IClient;
import server.IServer;
import journal.IJournalManager;

import javax.crypto.Cipher;
import javax.swing.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * Class to work with rmi registry.
 */
public class RegistryUtils {
    private static final String ALGO = "RSA";
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
           // client = new Client(login, pass);
            try {
                KeyFactory keyFactory = KeyFactory.getInstance(ALGO);
                byte[] public_key_bytes = Base64.getDecoder().decode(server.getPublicKey());

                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(public_key_bytes);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

                String pass1 = encrypt(pass,publicKey);
                client = new Client(login, pass1);
            } catch (NoSuchAlgorithmException e) {
               // e.printStackTrace();
            } catch (InvalidKeySpecException e) {
              //  e.printStackTrace();
            }
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
            KeyFactory keyFactory = null;
            try {
                keyFactory = KeyFactory.getInstance(ALGO);
                byte[] public_key_bytes = Base64.getDecoder().decode(server.getPublicKey());
                X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(public_key_bytes);
                PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
                String encrypted_pass = encrypt(pass,publicKey);
                return server.newUser(new Client(login, encrypted_pass));
            } catch (NoSuchAlgorithmException e) {
                //e.printStackTrace();
            } catch (InvalidKeySpecException e) {
               // e.printStackTrace();
            }

            //  return server.newUser(new Client(login, pass));
        }
        return false;
    }

    /**
     * Encrypts specified password with specified public key.
     * @param pass password.
     * @param key public key.
     * @return encrypted password.
     */
    private static String encrypt(String pass, PublicKey key) {
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(pass.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(cipherText);
    }
}
