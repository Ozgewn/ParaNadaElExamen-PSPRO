package ejercicio2_encrypt;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class MainEncrypt {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        byte[] valorClave;
        String inputFileName = "";
        String outputFileName = "";
        String keyFileName = "";
        String algorithm = "";
        SecretKeySpec keySpec;
        File inputFile;
        File outputFile;
        SecretKey clave;

        System.out.println("Introduce el nombre del archivo a encriptar (con extensión): ");
        inputFileName = sc.nextLine();
        System.out.println("Introduce el nombre del archivo resultante de la encriptación (sin extensión): ");
        outputFileName = sc.nextLine();
        outputFileName = outputFileName + ".txt";
        System.out.println("Introduce el nombre de la clave a usar para encriptar (sin extensión): ");
        keyFileName = sc.nextLine();
        keyFileName = keyFileName + ".raw";

        algorithm = askAlgorithm(sc, algorithm);
        inputFile = new File(inputFileName);
        outputFile = new File(outputFileName);

        try (FileInputStream fisClave = new FileInputStream(keyFileName)) {
            valorClave = fisClave.readAllBytes();
        } catch (FileNotFoundException e) {
            System.out.printf("ERROR: no existe fichero de clave %s\n.", keyFileName);
            return;
        } catch (IOException e) {
            System.out.printf("ERROR: de E/S leyendo clave de fichero %s\n.", keyFileName);
            return;
        }

        keySpec = new SecretKeySpec(valorClave, algorithm);
        clave = new SecretKeySpec(keySpec.getEncoded(), algorithm);

        EncryptingManager encryptingManager = new EncryptingManager();
        try {
            encryptingManager.encryptFile(algorithm, clave, inputFile, outputFile );
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | BadPaddingException | IllegalBlockSizeException | IOException | InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    private static String askAlgorithm(Scanner sc, String algorithm) {
        int chosen_algorithm;
        System.out.println("Elige el algoritmo usado en la generacion de la clave: || 1. AES  || 2. DES || 3. DESede ");
        chosen_algorithm = sc.nextInt();
        switch (chosen_algorithm) {
            case 1 -> algorithm = "AES";
            case 2 -> algorithm = "DES";
            case 3 -> algorithm = "DESede";
        }
        return algorithm;
    }
}
