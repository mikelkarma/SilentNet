import java.security.*;
import java.util.Base64;
import java.io.*;

public class Keygen {

    public static void main(String[] args) {
        try {
            // Gerar o par de chaves RSA
            System.out.println(" > Gerando chaves RSA...");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);  // Usando 2048 bits de segurança
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // Chave pública
            PublicKey publicKey = keyPair.getPublic();
            String publicKeyBase64 = Base64.getEncoder().encodeToString(publicKey.getEncoded());
            // Chave privada
            PrivateKey privateKey = keyPair.getPrivate();
            String privateKeyBase64 = Base64.getEncoder().encodeToString(privateKey.getEncoded());

            // Salvar as chaves em arquivos
            saveKeyToFile("publicKey.txt", publicKeyBase64);
            saveKeyToFile("privateKey.txt", privateKeyBase64);

            // Exibir as chaves geradas
            System.out.println(" > Chave pública gerada:");
            System.out.println(publicKeyBase64);
            System.out.println("\n > Chave privada gerada:");
            System.out.println(privateKeyBase64);

            System.out.println("\n > Chaves salvas nos arquivos publicKey.txt e privateKey.txt");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Função para salvar as chaves em arquivos
    private static void saveKeyToFile(String fileName, String key) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(key);
        } catch (IOException e) {
            System.out.println("Erro ao salvar a chave em arquivo: " + e.getMessage());
        }
    }
}
