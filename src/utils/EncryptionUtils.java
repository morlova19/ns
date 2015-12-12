package utils;


import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class EncryptionUtils {
    /**
     * Algorithm of encryption.
     */
    private static final String ALGO = "RSA";
    /**
     * Encrypts specified password with specified public key.
     * @param pass password.
     * @param public_key string with public key.
     * @return encrypted password.
     */
    public static String encrypt(String pass, String public_key) throws InvalidKeySpecException, NoSuchAlgorithmException {

        KeyFactory keyFactory = KeyFactory.getInstance(ALGO);
        byte[] public_key_bytes = Base64.getDecoder().decode(public_key);
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(public_key_bytes);
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        byte[] cipherText = null;
        try {
            final Cipher cipher = Cipher.getInstance(ALGO);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherText = cipher.doFinal(pass.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(cipherText);
    }
}
