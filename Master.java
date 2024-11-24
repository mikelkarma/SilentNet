import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
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
        String privateKeyMasterBase64 = "MIIBPAIBAAQGAnbNdh9+GnzmO2K8pvgybkW+QShj/fzljkMkksHklghwMZ3u+m3LNj8eUG3O+zjqPi6Ogspt8hl9W5OfdrBeP2dW4TuUQZfS7LbdLhDskJjD96MGlD+Rx3+EjQJtW9jIkxqzdcY+8gLvgNYjpxB4Mgc/NFhxrdKmK8KHjDF3KrPqF5yH7pRzqL0dqZ1G5fZfOjA5TxQ++u/FQwE5A1i8A4Cjkq3LPjX6IWVsRjGly6do5xtEFbggt2C6wVXZ5ZcAqCVVXHTpX+yYoEu2TflFTo5sb7Xf+GRzO0lbOg9bjsagk1SszT7ISf2ffzkW3OYH0eTYFP/n4SKnOwEIQDW70xShGF0JKVt/Z//DHDhChhs0VYxtk0x6dpuxGpmJcdQJ3zF7exhuxjiwQyPT1fr0cZ7AN7zY0QtANg00yDmtPrZq2FqsFOIuk1fFFl3oeu/Uw==";
        byte[] decodedPrivateKeyMasterBytes = Base64.getDecoder().decode(privateKeyMasterBase64);
        
        // Criando a chave privada do Master a partir do Base64
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedPrivateKeyMasterBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKeyMaster = keyFactory.generatePrivate(keySpec);

        // Simulação da separação de variáveis no código
        String cryptSilent = "coloque_o_resultado_do_codigo_anterior_aqui"; // Substitua isso pelo resultado gerado no código anterior

        // Separando as partes do resultado criptografado
        String[] parts = cryptSilent.split(",");
        String encryptedAESKey = new String(base64dec(parts[0]));
        String cryptpublickeyclient = new String(base64dec(parts[1]));
        String id_crypt = new String(base64dec(parts[2]));
        String info_crypt = new String(base64dec(parts[3]));

        // Descriptografar a chave AES com a chave privada do Master
        SecretKey decryptedAESKey = decryptRSA(encryptedAESKey, privateKeyMaster);

        // Descriptografar as informações com a chave AES
        String decryptedInfo = decryptAES(info_crypt, decryptedAESKey);
        String decryptedId = decryptAES(id_crypt, decryptedAESKey);
        String decryptedPublicKeyClient = decryptAES(cryptpublickeyclient, decryptedAESKey);

        // Agora, as variáveis estão separadas e descriptografadas
        System.out.println("Informações descriptografadas:");
        System.out.println("ID: " + decryptedId);
        System.out.println("Informações: " + decryptedInfo);
        System.out.println("Chave pública do cliente: " + decryptedPublicKeyClient);
    }

    public static void main(String[] args) {
        try {
            SilentNet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
