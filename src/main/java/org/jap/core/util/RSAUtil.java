package org.jap.core.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static KeyPair generateKeyPair() throws Exception {
        byte[] publicBytes = Base64.getDecoder().decode(UtilConstants.PUBLIC_KEY_RSA);
        byte[] privateBytes = Base64.getDecoder().decode(UtilConstants.PRIVATE_KEY_RSA);

        KeyFactory keyFactory = KeyFactory.getInstance("RSA", "BC");
        X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicBytes);
        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateBytes);

        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
        PrivateKey privateKey = keyFactory.generatePrivate(privateKeySpec);

        return new KeyPair(publicKey, privateKey);
    }

    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(UtilConstants.RSA_TRANSFORMATION, "BC");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(UtilConstants.RSA_TRANSFORMATION, "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(java.util.Base64.getDecoder().decode(cipherText));
        return new String(decryptedBytes);
    }
}
