package com.example.payments;

import com.example.payments.model.Operation;
import org.apache.commons.lang3.ArrayUtils;
import org.bouncycastle.jcajce.provider.symmetric.AES;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest(classes = {Operation.class})
public class RSADecryptorTest {
    private static final String CRYPT_ALGORITHM = "AES/CBC/PKCS5Padding";
    @MockBean
    public Operation operation;
    KeyPair keyPair;
    String pubKeyData;

    public RSADecryptorTest(Operation operation) {
    }

    @BeforeEach
    public void setUp() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        keyPair = keyPairGenerator.generateKeyPair();
        pubKeyData = Base64.toBase64String(keyPair.getPublic().getEncoded());
        Mockito.when(operation.getKey(any())).thenReturn(pubKeyData);
    }

    @Test
    public void testDecrypt() throws Exception {//        RSADecryptor decryptor = new RSADecryptor(paymentsDao);
        RSADecryptorTest decryptorTest = new RSADecryptorTest(operation);
        //Prepare data to encrypt
        Random rnd = new Random();
        byte[] randomData = new byte[rnd.nextInt(100 - 10) + 10]; //size between 10 and 100
        rnd.nextBytes(randomData);
        //Generate symmetric key
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        SecretKey aesKey = keyGenerator.generateKey();
        //Generate initial vector
         byte[] iv = new byte[16];
        rnd.nextBytes(iv);
        //Encrypt data
         Cipher aesCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        aesCipher.init(Cipher.ENCRYPT_MODE, aesKey, ivSpec);
        byte[] encryptedData = aesCipher.doFinal(randomData);
        //Encrypt symmetric key + iv with private key
        byte[] keyData = ArrayUtils.addAll(aesKey.getEncoded(), iv);
        Cipher rsaCipher = Cipher.getInstance("RSA");
        rsaCipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
        byte[] encryptedKeyData = rsaCipher.doFinal(keyData);
        //Make encrypted result
        byte[] encryptedResult = ArrayUtils.addAll(encryptedKeyData, encryptedData);
        byte[] checkData = decryptorTest.decrypt("any uuid you like", encryptedResult);
        Assertions.assertArrayEquals(checkData, randomData);
    }

    private byte[] decrypt(String anyUuidYouLike, byte[] encryptedResult) {
        return encryptedResult;
    }
}