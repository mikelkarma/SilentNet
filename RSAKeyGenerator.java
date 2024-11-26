import java.security.*;
import java.util.Base64;

public class RSAKeyGenerator {
    public static void main(String[] args) {
        try {
            // Gerando o par de chaves RSA (pública e privada)
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);  // Tamanho da chave (2048 bits é seguro)
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Chave pública
            PublicKey publicKey = keyPair.getPublic();
            String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            System.out.println("Chave pública: " + publicKeyString);

            // Chave privada
            PrivateKey privateKey = keyPair.getPrivate();
            String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            System.out.println("Chave privada: " + privateKeyString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
