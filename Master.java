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

    // Função de descriptografar
    public static String decrypt(String encryptedData, PrivateKey privateKeyMaster) throws Exception {
        // Divida os dados criptografados (cada parte está separada por uma vírgula)
        String[] parts = encryptedData.split(",");

        // Descriptografar a chave AES usando a chave privada do mestre
        String encryptedAESKey = parts[0];
        SecretKey aesKey = decryptRSA(encryptedAESKey, privateKeyMaster);

        // Descriptografar a chave pública do cliente (com AES)
        String cryptpublickeyclient = parts[1];
        String publicKeyClientBase64 = decryptAES(cryptpublickeyclient, aesKey);

        // Descriptografar os outros dados (informações)
        String id_crypt = parts[2];
        String info_crypt = parts[3];
        String idDecrypted = decryptAES(id_crypt, aesKey);
        String infoDecrypted = decryptAES(info_crypt, aesKey);

        // Retornar as informações descriptografadas
        return "ID: " + idDecrypted + ", Info: " + infoDecrypted + ", PublicKeyClient: " + publicKeyClientBase64;
    }

    public static void main(String[] args) {
        try {
            String net = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp3oeURrkzdjZ8moZ0fTZsf3FOcQiAwa7ifkQQMHh9NtAzjcmyOsh3z6COYikJ7qtloK3cHLqAeoy3qGWaFuYMod+4ghuHI9czUo7ezl2sB36qZz/ie1ovFGWowxfcoGYbGC8wZNCwdll60SxmhqVfjVORPO7NSpwuxRID0N7nOhQuAp3POB2feFNDW8GV2fszyryvMr967e7yMH/fwfw6iGV3Bil3PI4WH2vv0h0qQvCTAk485FGFNEgvOkysZEADQFfIpwoKSwiuWEKfivykIZk2Ox6daHLNfys/62WlPoHuwfUtSop8LJWS4GRL/gYBtR8nG0rK+0cIHoZR0tyLQIDAQAB";
            System.out.println("Dados criptografados: " + net);

            // Aqui você pode colocar a chave privada do mestre para descriptografar
            String privateKeyMasterBase64 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCneh5RGuTN2NnyahnR9Nmx/cU5xCIDBruJ+RBAweH020DONybI6yHfPoI5iKQnuq2WgrdwcuoB6jLeoZZoW5gyh37iCG4cj1zNSjt7OXawHfqpnP+J7Wi8UZajDF9ygZhsYLzBk0LB2WXrRLGaGpV+NU5E87s1KnC7FEgPQ3uc6FC4Cnc84HZ94U0NbwZXZ+zPKvK8yv3rt7vIwf9/B/DqIZXcGKXc8jhYfa+/SHSpC8JMCTjzkUYU0SC86TKxkQANAV8inCgpLCK5YQp+K/KQhmTY7Hp1ocs1/Kz/rZaU+ge7B9S1KinwslZLgZEv+BgG1HycbSsr7RwgehlHS3ItAgMBAAECggEAGp9tBlOSH67jJof7Rsb3fDXtDjvBdF2znkE7di/b7YKVELLvtWy7W7OiG+c+wRoJ0i1BX9zEmj1SjNV/BWPaE946K1BPwLaBeBjez73YwjJh0GimrpRQ2+7UVWL4IUm8oy3Vk6gHKq2WaAnpeafUL9XldUyRa+gtN3AH7HU7xztcoFRgoia4Y1gAuvZ9KFqKmowkNaDJrv6gZ4hRqhlR5MMRRchXOrztR5GmnA8NFIjbsjx3FdMiKv6oca2ZrQW8M4K77RNTow/rabPM4AP9Em1mdtTYNQPc2xDhJmHRI6VhrdQXfJzuVC41CtSyHM4E2kj8vpjQbR0X+ZGME9G6gQKBgQC4HP3bJSYVmY+meDwDOaMcpLrJOwlTaSFmOAty3AdBKGJm+0cJffIi9Zk0dRnm5DYDketiX6LaSe3eoOb70pf0rX+MmNcN8aS0L4oIRP+ONxfBhsIiW4zP7KvB8hTUfLacRY1rQ0JbBLkWeQ/Y0+PTTrvata4GKfw7oE9okkZFvQKBgQDo3kGEGLoSAu1XriKkyPNhN5dDdiI0hEAlvhsfCBI7VCDVtP2FsDv94elLIlVAq2EErwFy2qWJxKzRjraxeWcy9DL1F59wdURWErriFikSj4euLSz49ZCip2Mx8HqLmvpn5AMzg8jGMhdlDIp3Zr2VdUMRqz326SJXBhm+YpuNMQKBgQCOqXhTPN/b0NesqHHoFds8MLDDiMlbJpVIvZixcaj1ZjOJlJ+gzUS4rhH+wdZGQQNRlNoNqglol+yHm6of6zjv0RiKe0v4dAupy0Rrz/st2abAwIjhUosz+jHE4K4fUFu3j3VbbgyWtnws27XZz6YzcEjD2g90m0flc/zUDLQDCQKBgBUqpp/gM4GRnIs8u89oqJ6sk+ZA5akKYF5N4l82t5mG8xkZw3WUaE+Sakc3DrK7IS1wztvmvedA5kiCBV8qXtQF2F/ozshFEHoWZ8BHLWKPg1C0sILMp2HxfxW5+xmXc61fz16uWuCRCwlXU3q47wCX0QnZG9/GnyVz24Evq13xAoGBAK6kSfX7YfEAAskKebPyKiT0ejZpFBX6PwesQV0iv1XQJRdmPcXuvRBqrIau9yKXMutKfD3xa6pMGSYxH8/6kaJwiYs5XGVVhjA5pfEIiRLJbaWgzULOfrlYju1T5aoERoEjR9R8JD5kLBXKQgoYv438AiU4RWLY6N8QwCuLZe/b"; // Coloque a chave privada em Base64 aqui
            PrivateKey privateKeyMaster = KeyFactory.getInstance("RSA")
                    .generatePrivate(new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyMasterBase64)));

            String decryptedData = decrypt(net, privateKeyMaster);
            System.out.println("Dados descriptografados: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
