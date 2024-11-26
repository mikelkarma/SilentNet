
// > Assinatura gerada: F8zURJX...
// > Chave AES gerada: I70+l6oDwfDfh6hqWGCr4A==
// > Dados criptografados com AES: ....
// > Chave AES criptografada com RSA: pkzRlK1F6mxOYZ5yvO7PCY...
// > Assinatura criptografada com AES: rBTmiT39d6g2R...
// > Chave pública do cliente criptografada: SQrgbKTyMe26ZxUkXk8VmBoJfzkl9QlG0w7TN2eIv9XY...
// > Formato dos dados: encryptedSignature,encryptedAESKey,encryptedData,encryptedPublicKeyClient

// [Execution complete with exit code 0]

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;
import java.security.spec.X509EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;


public class Main {

    static KeyPair rsaMasterKeyPair;
    static KeyPair rsaClientKeyPair;
    static PublicKey publicKeyMaster;
    static PrivateKey privateKeyMaster;
    static PublicKey publicKeyClient;
    static PrivateKey privateKeyClient;

    static {
        try {
            // Chave RSA master fornecida em Base64 (em um ambiente real, isso seria uma chave real)
            String base64MasterPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxxuO8AEH7eG0yh+ksm/lY64020Ptif8HGx/m29fPuthtEVYLrhZVUVmQhgCBk5+XF28c79s+EsVyJQZ9pNqcjtANLn2xL4qwPUcBRer5zUCnZAxJRhPrPZX1xMc3XHaqVOd3rGirrie/OA/yzFNPoqKEZgBrYxEeH/sDqMaiRWYyoVM8jRB2jIVjwbgDCx6qWlddNCfQq9wLkQFZSC3M2K+7o0FPnymL1VHxaiZki4/wsxfPX8OSRi2EzMLIWJwVO39LPhbC58X/SWAohMIwV74mU78/Tn1pW7IpGTWfVS3/FCdswbSc3kbH1/StfPy27+u+yMlQvECVY4BShIeCfQIDAQAB";
            
            // Convertendo as chaves de Base64 para Key objects
            byte[] publicKeyBytes = Base64.getDecoder().decode(base64MasterPublicKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKeyMaster = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            
            // Gerando as chaves do cliente
            rsaClientKeyPair = generateRSAKey();
            publicKeyClient = rsaClientKeyPair.getPublic();
            privateKeyClient = rsaClientKeyPair.getPrivate();
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inicializar as chaves RSA");
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

        // Assinar a mensagem
        String signature = signData(data.getBytes(), privateKeyClient);
        print(" > Assinatura gerada: " + signature);
        print(" > Chave AES gerada: " + Base64.getEncoder().encodeToString(aesKey.getEncoded()));

        // Criptografar os dados com AES
        String encryptedData = encryptAES(data, aesKey);
        print(" > Dados criptografados com AES: " + encryptedData);

        // Criptografar a chave AES com RSA
        String encryptedAESKey = encryptRSA(aesKey, publicKeyMaster);
        print(" > Chave AES criptografada com RSA: " + encryptedAESKey);

        // Criptografar a assinatura com AES
        String encryptedSignature = encryptAES(signature, aesKey);
        print(" > Assinatura criptografada com AES: " + encryptedSignature);

        // Serializar e criptografar chave pública do cliente
        String serializedPublicKeyClient = Base64.getEncoder().encodeToString(publicKeyClient.getEncoded());
        String encryptedPublicKeyClient = encryptAES(serializedPublicKeyClient, aesKey);
        print(" > Chave pública do cliente criptografada: " + encryptedPublicKeyClient);

        // Simulação de envio
        print(" > Formato dos dados: " + encryptedSignature + "," + encryptedAESKey + "," + encryptedData + "," + encryptedPublicKeyClient);

    } catch (Exception e) {
        e.printStackTrace();
    }
}}
