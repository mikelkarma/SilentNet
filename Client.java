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
        // CHAVE PUBLICA DO MASTER (exemplo fictício)
        String KeyPublicMaster = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp3oeURrkzdjZ8moZ0fTZsf3FOcQiAwa7ifkQQMHh9NtAzjcmyOsh3z6COYikJ7qtloK3cHLqAeoy3qGWaFuYMod+4ghuHI9czUo7ezl2sB36qZz/ie1ovFGWowxfcoGYbGC8wZNCwdll60SxmhqVfjVORPO7NSpwuxRID0N7nOhQuAp3POB2feFNDW8GV2fszyryvMr967e7yMH/fwfw6iGV3Bil3PI4WH2vv0h0qQvCTAk485FGFNEgvOkysZEADQFfIpwoKSwiuWEKfivykIZk2Ox6daHLNfys/62WlPoHuwfUtSop8LJWS4GRL/gYBtR8nG0rK+0cIHoZR0tyLQIDAQAB";
        byte[] decodedBytes = Base64.getDecoder().decode(KeyPublicMaster);
        String idAleatorio = UUID.randomUUID().toString();

        // Obtendo informações do dispositivo
        String osName = System.getProperty("os.name"); // Nome do sistema operacional
        String osArch = System.getProperty("os.arch"); // Arquitetura do sistema
        String osVersion = System.getProperty("os.version"); // Versão do sistema operacional
        String hostName = System.getenv("COMPUTERNAME"); // Nome do computador (Windows) ou "HOSTNAME" (Linux)
        String cores = String.valueOf(Runtime.getRuntime().availableProcessors()); // Número de núcleos de CPU

        String info = String.format("OS: %s, Arch: %s, Version: %s, Host: %s, Cores: %s", osName, osArch, osVersion, hostName, cores);

        // CLIENTE CRIPTOGRAFA A CHAVE PÚBLICA DO CLIENTE COM AES
        KeyPair rsaClientKeyPair = generateRSAKey();
        PublicKey publicKeyClient = rsaClientKeyPair.getPublic();
        PrivateKey privateKeyClient = rsaClientKeyPair.getPrivate();

        // Gerar chave AES
        SecretKey keyAES = generateAESKey();
        System.out.println(" > Chave AES gerada: " + base64enc(keyAES.getEncoded()));

        // Criptografar a chave pública do cliente com AES
        String cryptpublickeyclient = encryptAES(base64enc(publicKeyClient.getEncoded()), keyAES);
        System.out.println(" > Chave pública do cliente criptografada com AES: " + cryptpublickeyclient);

        // CRIPTOGRAFANDO A CHAVE AES COM A CHAVE PUBLICA DO MASTER
        // Corrigido para usar o método correto
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey rsaPublicKeyMaster = keyFactory.generatePublic(new X509EncodedKeySpec(decodedBytes));
        String encryptedAESKey = encryptRSA(keyAES, rsaPublicKeyMaster);
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
            System.out.println("Dados criptografados: " + net);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
