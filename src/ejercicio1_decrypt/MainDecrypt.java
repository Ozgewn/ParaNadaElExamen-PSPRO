package ejercicio1_decrypt;

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

public class MainDecrypt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        byte[] valorClave;
        String outputFileName;
        String keyFileName;
        String algorithm = "";
        SecretKeySpec keySpec;
        File inputFile;
        File outputFile;
        SecretKey clave;

        System.out.println("Introduce el nombre del archivo encriptado (con extensión): ");
        inputFile = new File(sc.nextLine());
        System.out.println("Introduce el nombre del archivo resultante de la desencriptación (sin extensión): ");
        outputFileName = sc.nextLine();
        outputFile = new File(outputFileName + ".decript");
        System.out.println("Introduce el nombre de la clave a usar para encriptar (sin extensión): ");
        keyFileName = sc.nextLine();
        keyFileName = keyFileName + ".raw";

        algorithm = askAlgorithm(sc, algorithm);

        try (FileInputStream fisClave = new FileInputStream(keyFileName)) {
            valorClave = fisClave.readAllBytes();
        } catch (FileNotFoundException e) {
            System.err.printf("No existe fichero de clave %s\n.", keyFileName);
            return;
        } catch (IOException e) {
            System.err.printf("Error de E/S leyendo clave de fichero %s\n.", keyFileName);
            return;
        }

        keySpec = new SecretKeySpec(valorClave, algorithm);
        clave = new SecretKeySpec(keySpec.getEncoded(), algorithm);

        DecryptManager decryptingManager = new DecryptManager();
        try {
            decryptingManager.encryptFile(algorithm, clave, inputFile, outputFile);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IOException | IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
    }

    private static String askAlgorithm(Scanner sc, String algorithm) {
        System.out.println("Elige el algoritmo usado en la generacion de la clave: || 1. AES  || 2. DES || 3. DESede ");
        int selectedOption = sc.nextInt();

        switch (selectedOption) {
            case 1 -> algorithm = "AES";
            case 2 -> algorithm = "DES";
            case 3 -> algorithm = "DESede";
        }
        return algorithm;
    }
}
