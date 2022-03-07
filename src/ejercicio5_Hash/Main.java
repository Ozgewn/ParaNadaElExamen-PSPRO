package ejercicio5_Hash;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        File fichero = new File(askFileName());
        BufferedInputStream bin = createBufferInputStream(fichero);
        byte[] hash = getFileHash(bin, askAlgorithm());
        toHexString(hash);
    }
    private static String askAlgorithm()
    {
        String algorithm="SHA-256";
        Scanner sc= new Scanner(System.in);
        System.out.println("""
                Seleccione el algoritmo que desea utilizar:
                1.SHA-256
                2.SHA-1
                3.MD5
                """);
        String s=sc.next();
        if(s.equals("2"))
            algorithm="SHA-1";
        else if(s.equals("3"))
            algorithm="MD5";
        return algorithm;
    }
    private static String askFileName()
    {
        String fileName;
        System.out.println("introduzca el nombre del fichero");
        Scanner sc= new Scanner(System.in);
        fileName=sc.next();
        return fileName;
    }
    private static byte[] getFileHash(BufferedInputStream bin, String algorithm) {
        byte[] hash=new byte[0];
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.update(bin.readAllBytes());
            hash = md.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
        return hash;
    }

    private static BufferedInputStream createBufferInputStream(File fichero) {
        BufferedInputStream bin = null;
        try {
            bin = new BufferedInputStream(new FileInputStream(fichero));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bin;
    }

    private static void toHexString(byte[] hash) {
        StringBuilder result= new StringBuilder();
        for (byte b : hash) {
            result.append(String.format("%x", b));
        }
        System.out.println(result);
    }
}
