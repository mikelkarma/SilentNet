import java.security.PrivateKey;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.util.Base64;
import javax.crypto.Cipher;

public class RSADecryption {

    // Função para descriptografar dados com chave privada RSA
    public static String decryptRSA(String encryptedDataBase64, String privateKeyBase64) throws Exception {
        // Converter a chave privada de Base64 para objeto PrivateKey
        byte[] decodedPrivateKey = Base64.getDecoder().decode(privateKeyBase64);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(new java.security.spec.PKCS8EncodedKeySpec(decodedPrivateKey));

        // Inicializar o Cipher para descriptografia com RSA
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        // Converter o dado criptografado de Base64 para byte[]
        byte[] decodedEncryptedData = Base64.getDecoder().decode(encryptedDataBase64);

        // Descriptografar os dados
        byte[] decryptedData = cipher.doFinal(decodedEncryptedData);

        // Converter os dados descriptografados para String
        return new String(decryptedData);
    }

    public static void main(String[] args) {
        try {
            // Exemplo de chave privada em Base64 (substitua por sua chave privada)
            String privateKeyBase64 = "MIIB..."; // Sua chave privada em Base64 aqui
            // Exemplo de dado criptografado (substitua pelo dado criptografado que deseja descriptografar)
            String encryptedDataBase64 = "U2FsdGVk..."; // Dado criptografado em Base64 aqui

            // Descriptografar os dados
            String decryptedData = decryptRSA(encryptedDataBase64, privateKeyBase64);

            // Imprimir os dados descriptografados
            System.out.println("Dados Descriptografados: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
