package com.example.payments.encrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Base64;
import java.util.Random;

public class RequestEncryptor {
    private static final String CRYPT_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final int AES_KEY_SIZE = 256;
    private static final int IV_SIZE = 16;

    private final KeyPair keyPair;

    public RequestEncryptor() throws Exception {
        // Генерируем пару ключей RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
    }

    public String encrypt(String plainText) throws Exception {
        // Генерируем случайный ключ AES и начальный массив (IV)
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey aesKey = keyGenerator.generateKey();
        byte[] iv = new byte[16];
        new Random().nextBytes(iv);

        // Зашифровываем ключ и начальный массив (IV) с помощью закрытого ключа RSA
        byte[] keyData = aesKey.getEncoded();
        byte[] ivAndKeyData = new byte[keyData.length + iv.length];
        System.arraycopy(iv, 0, ivAndKeyData, 0, iv.length);
        System.arraycopy(keyData, 0, ivAndKeyData, iv.length, keyData.length);

        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        byte[] encryptedKeyAndIvData = rsaCipher.doFinal(ivAndKeyData);

        // Шифруем содержимое запроса AES ключом и начальным массивом (IV)
        Cipher aesCipher = Cipher.getInstance(CRYPT_ALGORITHM);
        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivParameterSpec);
        byte[] encryptedData = aesCipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        // Объединяем зашифрованный ключ и начальный массив с зашифрованным содержимым запроса
        byte[] encryptedRequestData = new byte[encryptedKeyAndIvData.length + encryptedData.length];
        System.arraycopy(encryptedKeyAndIvData, 0, encryptedRequestData, 0, encryptedKeyAndIvData.length);
        System.arraycopy(encryptedData, 0, encryptedRequestData, encryptedKeyAndIvData.length, encryptedData.length);

        // Кодируем полученный массив в Base64 и возвращаем в виде строки
        return Base64.getEncoder().encodeToString(encryptedRequestData);
    }
}
