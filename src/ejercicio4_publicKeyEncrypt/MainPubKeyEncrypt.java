package ejercicio4_publicKeyEncrypt;

import ejercicio3_publicKeyDecrypt.CryptographyUtils;

import java.io.*;
import java.util.Scanner;

public class MainPubKeyEncrypt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        byte[] valorClave;
        byte[] decrypted;
        String outputFileName;
        String keyFileName;
        File inputFile;
        File outputFile;

        System.out.println("Introduce el nombre del archivo encriptado (con extensión): ");
        inputFile = new File(sc.nextLine());
        System.out.println("Introduce el nombre del archivo resultante de la encriptacion (sin extensión): ");
        outputFileName = sc.nextLine();
        outputFile = new File(outputFileName + ".encrypt");
        System.out.println("Introduce el nombre de la clave a usar para encriptar");
        keyFileName = sc.nextLine() ;

        try (FileInputStream fisClave = new FileInputStream(keyFileName)) {
            valorClave = fisClave.readAllBytes();
        } catch (FileNotFoundException e) {
            System.err.printf("No existe fichero de clave %s\n.", keyFileName);
            return;
        } catch (IOException e) {
            System.err.printf("Error de E/S leyendo clave de fichero %s\n.", keyFileName);
            return;
        }
        try (FileInputStream fis = new FileInputStream(inputFile);
             FileOutputStream fos = new FileOutputStream(outputFile);
             BufferedInputStream is = new BufferedInputStream(fis);
             BufferedOutputStream os = new BufferedOutputStream(fos)) {
            decrypted = is.readAllBytes();
            os.write(CryptographyUtils.encrypt(valorClave, decrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
