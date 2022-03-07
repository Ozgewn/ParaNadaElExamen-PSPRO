package ejercicio6_generarPrivKey;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.*;

public class CustomKeyGenerator {
    public void generateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
        Scanner sc = new Scanner(System.in);
        String file_name;
        String algorithm = "";
        System.out.println("Elige el nombre que tendrá el fichero que contendrá la clave:  (Sin extensión)");
        file_name =  sc.next() + ".raw";
        System.out.println("Elige el algoritmo a usar: || 1. AES  || 2. DES || 3. DESede ");
        switch (sc.nextInt()) {
            case 1 -> algorithm = "AES";
            case 2 -> algorithm = "DES";
            case 3 -> algorithm = "DESede";
        }
        writeKey(algorithm, file_name);
    }

    private void writeKey(String algorithm, String file_name) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String RAND_NUM_GENERATOR_ALGORITHM = "SHA1PRNG";
        SecureRandom srand = SecureRandom.getInstance(RAND_NUM_GENERATOR_ALGORITHM);

        switch (algorithm) {
            case "AES" -> generateAESKey(file_name, srand);
            case "DESede" -> generateDESedeKey(file_name, srand);
            case "DES" -> generateDESKey(file_name, srand);
        }
    }

    private void generateAESKey(String file_name, SecureRandom srand) throws NoSuchAlgorithmException {
        KeyGenerator keyGen;
        SecretKey key;
        keyGen = KeyGenerator.getInstance("AES");
        keyGen.init(srand);
        key = keyGen.generateKey();
        byte[] raw = key.getEncoded();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        try (FileOutputStream fos = new FileOutputStream(file_name)) {
            fos.write(skeySpec.getEncoded());
        } catch (IOException e) {
            System.out.println("Error de E/S escribiendo clave en fichero");
            e.printStackTrace();
        }
    }

    private void generateDESKey(String file_name, SecureRandom srand) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keySpecFactory;
        KeyGenerator keyGen;
        SecretKey key;
        byte[] keyBytes;
        keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(srand);
        key = keyGen.generateKey();
        keySpecFactory = SecretKeyFactory.getInstance("DES");
        DESKeySpec DESKeySpec = (DESKeySpec) keySpecFactory.getKeySpec(key, DESKeySpec.class);
        keyBytes = DESKeySpec.getKey();
        try (FileOutputStream fos = new FileOutputStream(file_name)) {
            fos.write(keyBytes);
        } catch (IOException e) {
            System.out.println("Error de E/S escribiendo clave en fichero");
            e.printStackTrace();
        }
    }

    private void generateDESedeKey(String file_name, SecureRandom srand) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeyGenerator keyGen;
        SecretKey key;
        byte[] keyBytes;
        SecretKeyFactory keySpecFactory;
        keyGen = KeyGenerator.getInstance("DESede");
        keyGen.init(srand);
        key = keyGen.generateKey();
        keySpecFactory = SecretKeyFactory.getInstance("DESede");
        DESedeKeySpec DESedkeySpec = (DESedeKeySpec) keySpecFactory.getKeySpec(key, DESedeKeySpec.class);
        keyBytes = DESedkeySpec.getKey();
        try (FileOutputStream fos = new FileOutputStream(file_name)) {
            fos.write(keyBytes);
        } catch (IOException e) {
            System.out.println("Error de E/S escribiendo clave en fichero");
            e.printStackTrace();
        }
    }
}
