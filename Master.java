import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Main {
    // Gerar chave RSA
    public static KeyPair generateRSAKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }

    // Criptografar com RSA
    public static String encryptRSA(SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] encryptedAESKey = cipher.doFinal(aesKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedAESKey);
    }

    // Descriptografar com RSA
    public static SecretKey decryptRSA(String encryptedAESKey, PrivateKey rsaPrivateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] decodedEncryptedAESKey = Base64.getDecoder().decode(encryptedAESKey);
        byte[] decryptedAESKey = cipher.doFinal(decodedEncryptedAESKey);
        return new SecretKeySpec(decryptedAESKey, "AES");
    }

    // Gerar chave AES
    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    // Criptografar com AES
    public static String encryptAES(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // Descriptografar com AES
    public static String decryptAES(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(data);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }

    // Função para decodificar Base64
    public static byte[] base64dec(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static String SilentNet() throws Exception {
        // CHAVE PRIVADA DO MASTER (decodificada de Base64)
        String privateKeyMasterBase64 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCweL28gT+HB4U/sXKzqEaPhRRCiZsBhH1XVlJD5/dH4BQN3RoM2D49/1oz+5i/IR9sEW3gQO5nqlumc9lK5tn4BtiLvH9oZPhFmJMxfGe0uTt5vm38reYiRiK3QA93uanoXt5OiVlLl/oQ0mLzb7f0/CBgfgIfdU+0jUZQ04d4HKH/kv6kf1fgTTzLsXNCAt4jDh8cdCSIg8wESM2v3/RTJ83Xw2IHvGt9aG3A7xuwZ11DKUZzky/TEXrN6rrq/K5E+rt1gnZnyn5G9BLkWNHCnv94YVvxWzn0sWQhjdvW8DVNd+9aS5GXKxiYLE07gUxiBJKys/HJiIr4/+hIczqvAgMBAAECggEAKsHmfvD1+nqI/4i4GO2Rwx5kbUa4BuMaF3ozzn46vPB/C2jh8fDHC/B945B3Tv/whBeo/qfc28M8F5WENZC/iUfnWdwfJqUoPnycVWYmHnSvA21vasSbuPwzVyEvXjve/g1WYcMHnrhA/6hqDz+nW7VnBtohG4wp9r4wJk9KSICIFUreN46/U0CqzendUOEvi6/zIePqeYTWLETqRZeUtabShwP4Lr3exuRJHBnrjE2n8iPhxORO1bJNrTsrjh/SqLc3cZZlmmovv/lmAk3NSXbcnhvU67UUdhgtyLt4IBJ7ybNPKT1ObjTUcxV7iz8ErlPt9mrnX+8fnoFPSGbEKQKBgQDo9PJ220raLBq5F458hLvlW7z/2FlRonwTYk9gRMCMXP5OLr0G+ozTF2BYxvmvRdlzx1ORy96hzvu0HaqrmFUZ/B3sCkMtC8agrZ8BbxPIMBnER2+oMXhql4tJS/nQap4vvKj4wOiWbaZngPFl4KADU0x88Ds6BDf2NpIIrML7fQKBgQDB7XJ6XZuf9oXlaMWdPA1L/GHcDmsCJRgXBl02oMM/kolCj49v6v2PhOuP7pu+LtoA5WNGFBd7t65ZFUGs7RB0sW5rvYD69ikCBAp46tFSIgDN7vMrNE1nyzgRDLow/p31T9HFTjwd0Oh1su/MNcZABg1An0uxUxlaxmFVgy6umwKBgQCuIOHX/ATQXaU/8x67bo/wWglFCBeDkLvD1XlVkS6H/L7N+9upisCWzONkmJ5r5/8prrWFxmjMdCXqtT4sc3oxKSXImMcrtaCr2/t9m+Sif2nTMD6C7y9+JXakvBioM/m33wJCphd00dNPjK9kImoY8ju7K+ppbFvUO2ge+dvpFQKBgHwC0MMJWzyG1Abo4f3zaflecmHbYnijqe0HZ9JIKbNcjKPg0/PYzL7bXBvVlQigQ05xYfNtY7r8seRLoIzpqC1HTXpeW72gbobYeK3sU2D3V5ZZKG1Ha/xkyvy0K9iq6e32VgmbRvw2HIEvpQLsnFNSy2S4uRmzLzKKS7H4SoDjAoGBANl+TJrrSPD2uKjjjqJVVEmFYHHZZWQOppgiiIQQ+f6Jez3MltlWpUKEv0iY1yIQIssXM+losk6MfU+5JthyzvWO0CTMwjjI5nkfk6uLhUKRHRh2q0GHMGbEslZZTRTGbVeN1wLE5H/ZETsc/ta09UpvlhFgU6I63jB+tROh1qJ4Fy9zPVre0Sy1UBIEIQNmCpccIlZcbLydwLuFjJwPRg2Ofy8sbkFtvrVqqTe/8o3D2pQH/yJj5/FkA==" ;
        byte[] decodedPrivateKeyMasterBytes = base64dec(privateKeyMasterBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKeyMaster = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(decodedPrivateKeyMasterBytes));
        
        // DESCRIPTOGRAFAR AES COM RSA
        String encryptedAESKey = "VG1yVzdvVTF6NGVlVkhtemlNeHRUdktOZHJBZHFhc0RCVVFJeE10clBLUHFKY1pTSERPSFU1V0dqMit0Vy9peVBFbGZhNjhlc3JUNkgzK1hJSWpjVHFBb2RPeGt5bUZOWlhHcW9TbjVaOFVDT3Nsd0VXVzlmeURaclk0Q1E3UHhuYVhrcEErT0FBYm56UUhwZ2l2VWRKdFpxM3hpQ1dtbHA2ZnhSaGszSjJlTTBqSXBoTFRpZGQ5TGhwZ0w1ZWNRUHZ6M0RjenZ3SjNESW1YYjRpcWlTSmJnWkNJUkJ5V28rRDRjRm5ubFRTdnNoOWNIN21qdXY2bERtQ3dYdWFodjdZM05Wb3dzV3pmMDVrZmhNc2RaK3FKSVl0MklWcmpNRFFXOUtFOWI4czhTYjJ5aVY0UU91S2I5a0htd2RzMGtpTFRKalJqZ1U3b0N0eDdUZThTdURRPT0=,blE3M1k5RDJ0UUEvLzlpek1xbEZQeGhKUWVLUlROM1hNeWttaENtK1BBTnhqVm9PLzBYUGRoVWF6L2ZtYmtYeUNleTRLN1hGOVlKSXdUVWF4RGNOeHV2U3QxTStnTnMwYkgwWFVkclhLU1RFUE5POGYzVmVuM3lDQWdlaFdwZjlQc3c4ejcvYmZIYVVqRm4yT2JTYXpuRmVQUFJlcnVuR2djY3hIMkZrVTdISmZKRlo0b2NuZmswK0xETVA5STJyTUUrejdjbm9qSUV0RzJiMHYzSjB1MXF0Ykl4b3o4WjBNU1NPMER3QTI3TE1EclF5R1RvcEd1eU5rNFQ1eCt0c0F0Z3ZwRDNqcEZRUmdGcThXRUkxTldQNXk5dmtYSVByTjJMWnVCRUhxc0JYaWFUNG5uQ3J0SUNBNnBKVmNVaTRiYnliZlpxazAxZ1dEb1JDbzVlTTdHTHRFaFZhbVd4YXBiWXZMandrUkRnbEJoQllvb3NGS2Q1NVFyV1d2ZWZDb09ES0xsRVNiQWJPbm5QOEk4WWFubHcxS002aXFyN3lRL21sWkFqMlZNOU1KNTdoRGlPSUxYY2YwTFhxMFVkODQ1KzlQWnIxSjczUmx5MGVFbXJ6djB3bnN4VWVuUzVhT0FnNHRvME85ZS9ZeS9ua3I2MDhjcVNhMXcxTFNiUkszVm1vbkkya2I5NDFsVytLblZzWXp3PT0=,ZE5MMUw5K3Y1cTZoOEo4YittelFiMnBsR2c2SVJQTm45WGorYklZSXl4QVpOT3BFbDVJZzRPdEMxcktiekRKSQ==,NmRiaVNKL0lZdmMyZ2VmMkFQN29aMXlFaDJBV2JYVHo1UWZ6SXFoYXZya0JnTzhjdVo1OUlCQ25mUi9yK3RYbm1MMGRsOWtVSU5KVnFHOEFOSGxMbnhiSDhWb24wUG01dlViaDkxcjN0UnVlRDI2VjROTlphamdGbGUzWVI1M3U="; // Substitua com o valor real
        SecretKey decryptedAESKey = decryptRSA(encryptedAESKey, privateKeyMaster);

        // EXEMPLO DE CRIPTOGRAFIA E DESCRIPTOGRAFIA AES
        String encryptedData = encryptAES("text_to_encrypt", decryptedAESKey);
        String decryptedData = decryptAES(encryptedData, decryptedAESKey);

        System.out.println("Decrypted Data: " + decryptedData);

        return decryptedData;
    }
}
