import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;
import java.util.UUID;

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

    // Função para codificar em Base64
    public static String base64enc(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    // Função para decodificar Base64
    public static byte[] base64dec(String data) {
        return Base64.getDecoder().decode(data);
    }

    public static String SilentNet() throws Exception {
        // CHAVE PUBLICA DO MASTER
        String KeyPublicMaster = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsHi9vIE/hweFP7Fys6hGj4UUQombAYR9V1ZSQ+f3R+AUDd0aDNg+Pf9aM/uYvyEfbBFt4EDuZ6pbpnPZSubZ+AbYi7x/aGT4RZiTMXxntLk7eb5t/K3mIkYit0APd7mp6F7eTolZS5f6ENJi82+39PwgYH4CH3VPtI1GUNOHeByh/5L+pH9X4E08y7FzQgLeIw4fHHQkiIPMBEjNr9/0UyfN18NiB7xrfWhtwO8bsGddQylGc5Mv0xF6zeq66vyuRPq7dYJ2Z8p+RvQS5FjRwp7/eGFb8Vs59LFkIY3b1vA1TXfvWkuRlysYmCxNO4FMYgSSsrPxyYiK+P/oSHM6rwIDAQAB";
        byte[] decodedBytes = Base64.getDecoder().decode(KeyPublicMaster);
        String idAleatorio = UUID.randomUUID().toString();

        // Obtendo informações do dispositivo
        String osName = System.getProperty("os.name"); // Nome do sistema operacional
        String osArch = System.getProperty("os.arch"); // Arquitetura do sistema
        String osVersion = System.getProperty("os.version"); // Versão do sistema operacional
        String hostName = System.getenv("COMPUTERNAME"); // Nome do computador (Windows) ou "HOSTNAME" (Linux)
        String cores = String.valueOf(Runtime.getRuntime().availableProcessors()); // Número de núcleos de CPU

        String info = String.format("OS: %s, Arch: %s, Version: %s, Host: %s, Cores: %s", osName, osArch, osVersion, hostName, cores);

        // CLIENTE CRIPTOGRAFA A CHAVE PUBLICA CLIENT COM AES
        KeyPair rsaClientKeyPair = generateRSAKey();
        PublicKey publicKeyClient = rsaClientKeyPair.getPublic();
        PrivateKey privateKeyClient = rsaClientKeyPair.getPrivate();

        // Gerar chave AES
        SecretKey keyAES = generateAESKey();
        System.out.println(" > Chave AES gerada: " + base64enc(keyAES.getEncoded()));

        // Criptografar a chave pública do cliente com AES
        String cryptpublickeyclient = encryptAES(base64enc(publicKeyClient.getEncoded()), keyAES);
        System.out.println(" > Chave pública do cliente criptografada com AES: " + cryptpublickeyclient);

        // CRIPTOGRANFANDO A CHAVE AES COM A CHAVE PUBLICA DO MASTER
        String encryptedAESKey = encryptRSA(keyAES, rsaClientKeyPair.getPublic());
        System.out.println(" > Chave AES criptografada com RSA (pelo Public Master): " + encryptedAESKey);

        // Criptografando as informações com a chave AES
        String info_crypt = encryptAES(info, keyAES);
        String id_crypt = encryptAES(idAleatorio, keyAES);

        // Concatenando a mensagem
        String cryptSilent = base64enc(encryptedAESKey.getBytes()) + "," 
                + base64enc(cryptpublickeyclient.getBytes()) + "," 
                + base64enc(id_crypt.getBytes()) + "," 
                + base64enc(info_crypt.getBytes());
        return cryptSilent;
    }

    public static void main(String[] args) {
        try {
            String net = SilentNet();
            System.out.println(net);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
