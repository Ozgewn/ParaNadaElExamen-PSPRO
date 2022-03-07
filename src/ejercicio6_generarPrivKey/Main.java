package ejercicio6_generarPrivKey;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {

    public static void main(String[] args) {
        CustomKeyGenerator keyGenerator = new CustomKeyGenerator();
        try {
            keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
    }
}
