package ejercicio8_generarPubKey;

import java.io.*;
import java.security.*;

public class MainGenPubKey {
    private static final int KEY_SIZE =1024;

    private static final String ALGORITHM = "RSA";

    public static KeyPair generateKeyPair()
            throws NoSuchAlgorithmException, NoSuchProviderException {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
        keyGen.initialize(KEY_SIZE, random);
        return keyGen.generateKeyPair();
    }

    public static void main(String[] args) {

        byte[] publicKey=new byte[KEY_SIZE];
        byte[] privateKey=new byte[KEY_SIZE];
        KeyPair generateKeyPair;
        try {
            generateKeyPair = generateKeyPair();
            publicKey = generateKeyPair.getPublic().getEncoded();
            privateKey = generateKeyPair.getPrivate().getEncoded();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        try (FileOutputStream fos = new FileOutputStream("publicKeyFile");
             BufferedOutputStream os = new BufferedOutputStream(fos);
             FileOutputStream privateFos = new FileOutputStream("privateKeyFile");
             BufferedOutputStream privateOs = new BufferedOutputStream(privateFos)){
            os.write(publicKey);
            privateOs.write(privateKey);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
