import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class Main {

    static KeyPair rsaMasterKeyPair;
    static KeyPair rsaClientKeyPair;
    static PublicKey publicKeyMaster;
    static PrivateKey privateKeyMaster;
    static PublicKey publicKeyClient;
    static PrivateKey privateKeyClient;

    static {
        try {
            // Inicializando as chaves RSA
            rsaMasterKeyPair = generateRSAKey();
            rsaClientKeyPair = generateRSAKey();

            publicKeyMaster = rsaMasterKeyPair.getPublic();
            privateKeyMaster = rsaMasterKeyPair.getPrivate();
            publicKeyClient = rsaClientKeyPair.getPublic();
            privateKeyClient = rsaClientKeyPair.getPrivate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao gerar chaves RSA");
        }
    }

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

    // Assinar dados com a chave privada RSA
    public static String signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        byte[] signedData = signature.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }

    // Verificar assinatura
    public static boolean verifySignature(byte[] data, String signatureStr, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
        return signature.verify(signatureBytes);
    }

    // Imprimir mensagens
    public static void print(String text) {
        System.out.println(text);
    }

    // Função principal
    public static void main(String[] args) {
        try {
            // Gerar chave AES e criptografar dados
            SecretKey aesKey = generateAESKey();
            String data = "Informação confidencial";
            print("Chave AES gerada: " + Base64.getEncoder().encodeToString(aesKey.getEncoded()));

            String encryptedData = encryptAES(data, aesKey);
            print("Mensagem criptografada com AES: " + encryptedData);

            // Criptografar a chave AES com RSA
            String encryptedAESKey = encryptRSA(aesKey, publicKeyMaster);
            print("Chave AES criptografada com RSA: " + encryptedAESKey);

            // Assinar a mensagem
            String signature = signData(encryptedData.getBytes(), privateKeyClient);
            print("Assinatura da mensagem: " + signature);

            // Verificar e descriptografar
            SecretKey decryptedAESKey = decryptRSA(encryptedAESKey, privateKeyMaster);
            boolean isSignatureValid = verifySignature(encryptedData.getBytes(), signature, publicKeyClient);
            print("Assinatura válida: " + isSignatureValid);

            String decryptedData = decryptAES(encryptedData, decryptedAESKey);
            print("Mensagem descriptografada: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
