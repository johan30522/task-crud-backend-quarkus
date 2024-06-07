package org.jap.core.util;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RSAKeyPairGeneratorUtil {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // Generar el par de claves RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Codificar las claves en Base64
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());

        // Imprimir las claves codificadas
        System.out.println("Public Key: " + publicKey);
        System.out.println("Private Key: " + privateKey);
    }
}
