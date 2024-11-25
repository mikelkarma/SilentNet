import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class MasterKeyGenerator {

    public static void generateMasterKeys() throws Exception {
        // Gerar o par de chaves RSA
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048); // Tamanho da chave em bits
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        // Obter a chave pública e privada
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        // Codificar as chaves em Base64 para facilitar a visualização
        String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

        // Imprimir as chaves no console
        System.out.println("Chave Pública Mestre (Base64):");
        System.out.println(publicKeyBase64);

        System.out.println("\nChave Privada Mestre (Base64):");
        System.out.println(privateKeyBase64);
    }

    public static void main(String[] args) {
        try {
            generateMasterKeys(); // Chama o método para gerar e imprimir as chaves
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
