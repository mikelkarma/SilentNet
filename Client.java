// > Gerando chave AES...
// > Chave AES gerada.
// > Criptografando dados com AES...
// > Dados criptografados com AES.
// > Criptografando chave AES com RSA...
// > Chave AES criptografada com RSA: gD9j9tb/dYlv7KBrD0aJ3K8pXoNuXn5Ya170AsoL7eH6Av6iDTLIOM9OBXAkdY7ZEth6vWbaPGpRBDbdyXwZoaUolSRofyns9rQ47qKcpigN032Ws0Iwm6CMTk9Cl2bicdASq3OEGDlyEvDuXek6uyegZQ0QSrVxxaEX9JkryXAhPu02Y3dspaGEVjvaDYMDMea7L/g5/hOEHFuqojfeaHlVRf8HCAuWp4WSTosq6m/PDqY1vO0qpjGCn0PkDN+2XCjOUqbf6pUnQe26Gdi0gmQ3tHB+oEbyOA37GmlCa+Wli9QR38oWXK0qMH2lRoQ/YlToyA9W2ln+760MZqaIUA==
// > Mensagem criptografada com AES: QkawECchSB5THEpOdo5Qf3INffU17iS/GP6TYtitWn4=

[Execution complete with exit code 0]
     
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.Base64;

public class Client {

    // Gerar chave AES
    public static SecretKey generateAESKey() throws Exception {
        System.out.println(" > Gerando chave AES...");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey key = keyGenerator.generateKey();
        System.out.println(" > Chave AES gerada.");
        return key;
    }

    // Criptografar com AES
    public static String encryptAES(String data, SecretKey key) throws Exception {
        System.out.println(" > Criptografando dados com AES...");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        String encryptedDataStr = Base64.getEncoder().encodeToString(encryptedData);
        System.out.println(" > Dados criptografados com AES.");
        return encryptedDataStr;
    }

    // Criptografar com RSA
    public static String encryptRSA(SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
        System.out.println(" > Criptografando chave AES com RSA...");
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] encryptedAESKey = cipher.doFinal(aesKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedAESKey);
    }

    // Função principal para criptografar os dados
    public static void main(String[] args) {
        try {
            // Chave pública do Master em Base64 (exemplo fictício)
            String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhTZ68up+znqCF+rMzUdSVNFuhD8fPBp/hDmEe7ktnc5rS84hKCrt7PH2jYYVN1v57OJ6VkJSbOlhVl0Z99i97UDaf7lZVlb7Nzkb9G4JTT5Ui7g/N6WUDWta/dmf+GispDwZvUVtWl47ZId6bRwvl1HgWrB9ywrJg5ephlWplXR2rWcSMlQ6SfqgUlqizvdwT9GVgu2lmrKsH4i9SoEZ8u8DQwibt0XXUUEMNRp3noHRQxlzAjgqR6heHtzfMf9rziV/YRy8UnMAxstLfZNOzSG4Twp3BEUaQMdSD7nsPdwNH+ywhpl6RhYYtKOKOkyIZyhPXA8OXXuz2oSMByzwqwIDAQAB";
            byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyStr);
            PublicKey publicKeyMaster = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(publicKeyBytes));

            // Gerar chave AES
            SecretKey keyAES = generateAESKey();

            // Criptografar a mensagem com AES
            String data = "Informação confidencial";
            String crypt = encryptAES(data, keyAES);

            // Criptografar a chave AES com RSA
            String encryptedAESKey = encryptRSA(keyAES, publicKeyMaster);
            System.out.println(" > Chave AES criptografada com RSA: " + encryptedAESKey);

            // Mostrar resultado criptografado
            System.out.println(" > Mensagem criptografada com AES: " + crypt);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
