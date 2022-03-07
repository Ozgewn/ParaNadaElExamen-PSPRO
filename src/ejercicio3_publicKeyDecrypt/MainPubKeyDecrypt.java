package ejercicio3_publicKeyDecrypt;

import java.io.*;
import java.util.Scanner;

public class MainPubKeyDecrypt {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        byte[] valorClave;
        byte[] encryptedBytes;
        String outputFileName;
        String keyFileName;
        File inputFile;
        File outputFile;

        System.out.println("Introduce el nombre del archivo encriptado (con extensión): ");
        inputFile = new File(sc.nextLine());
        System.out.println("Introduce el nombre del archivo resultante de la desencriptación (sin extensión): ");
        outputFileName = sc.nextLine();
        outputFile = new File(outputFileName + ".decript");
        System.out.println("Introduce el nombre de la clave a usar para desencriptar (sin extensión): ");
        keyFileName = sc.nextLine() + ".raw";

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
            encryptedBytes = is.readAllBytes();
            os.write(CryptographyUtils.decrypt(valorClave, encryptedBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
