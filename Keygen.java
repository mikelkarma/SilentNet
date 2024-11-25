
// > Gerando chaves RSA...
// > Chave pública gerada:
// MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhTZ68up+znqCF+rMzUdSVNFuhD8fPBp/hDmEe7ktnc5rS84hKCrt7PH2jYYVN1v57OJ6VkJSbOlhVl0Z99i97UDaf7lZVlb7Nzkb9G4JTT5Ui7g/N6WUDWta/dmf+GispDwZvUVtWl47ZId6bRwvl1HgWrB9ywrJg5ephlWplXR2rWcSMlQ6SfqgUlqizvdwT9GVgu2lmrKsH4i9SoEZ8u8DQwibt0XXUUEMNRp3noHRQxlzAjgqR6heHtzfMf9rziV/YRy8UnMAxstLfZNOzSG4Twp3BEUaQMdSD7nsPdwNH+ywhpl6RhYYtKOKOkyIZyhPXA8OXXuz2oSMByzwqwIDAQAB

// > Chave privada gerada:
// MIIEuwIBADANBgkqhkiG9w0BAQEFAASCBKUwggShAgEAAoIBAQCFNnry6n7OeoIX6szNR1JU0W6EPx88Gn+EOYR7uS2dzmtLziEoKu3s8faNhhU3W/ns4npWQlJs6WFWXRn32L3tQNp/uVlWVvs3ORv0bglNPlSLuD83pZQNa1r92Z/4aKykPBm9RW1aXjtkh3ptHC+XUeBasH3LCsmDl6mGVamVdHatZxIyVDpJ+qBSWqLO93BP0ZWC7aWasqwfiL1KgRny7wNDCJu3RddRQQw1GneegdFDGXMCOCpHqF4e3N8x/2vOJX9hHLxScwDGy0t9k07NIbhPCncERRpAx1IPuew93A0f7LCGmXpGFhi0o4o6TIhnKE9cDw5de7PahIwHLPCrAgMBAAECgf8oF5LQ9v9LhujFDQpNUDYJgcHBSHsR5Oe449/XE5tRsoVDDmU1aHk/ULfvx8BRxGj51jvvWJjjzPgQ7assQieR6uazWV59qfkujT6HRgR6d+Zn3YXT5Eqqy3PskRkB83LFOBRusbN0o8Mj7BxB3vzVUQwEJwG1DgP5r0oA2wQvArg3M3Ioc4hPIhYCopnNol+eILevQQhADY1gwS5eAigkEm0Tdwnv3c4MvfDEzvMgIzvSdXMJw1Ry7qFj5QPQT+o98uIg9SzKpiKQmHh/HWLw5MLmQ7GodKbBfn5t5HCReL/n9lCMBiVhIW217lSAdk8QoeJb3b+noc3LYVefvMECgYEAuIrxfDhIEbdRPrqBktc6nxg1Q/ZZkLOACBDkWBZv417Mtg3FIGkBXQyXHJmve2/Ixd/CCMboLLaVcM8ZaDLEzpQmzf2lAnEUZEoJ3w2d+OMyGqjIDuiaEY3DByatL/V3hbVmIpIzHY46fAce0yCgH74360gsSCnPk02HkQDdspsCgYEAuMtixk6P4G31R6SkFcoFpN6bhW+PfCCqR1UjVC97YM1ISgbK2lN8tlqUc0oi0qHGaD0f7VPDiLqbNQWN5/xm3id2DJULSRx++Uj2oC+EH00AHEe3apSvEaXgLg1sQtJWqC2egN8FwrIItbq4yt0h9moBLEFVSOaevJAD9mwY0zECgYBoI4hImB4bWT6QzoDGU4yyMOAGHSPH43ORma6yAt9bCKku5zBhrL/nOW6g5h0/5AIBn9aIS27DpLlKMqRcx6GGDdakEcmnSha+zYUqiS2nQNaKcsqKlzoyQ7RV+b5rvPKY0pfzVFtiB1v7l60OaW/S2e4GaPEMPAP2VR72de2llwKBgF/Ws5qPghTuUihe3e6jALxZ6ySQEEVbF5S5NgJiQcWZkaU60pfadrKIVbdZOqgs2J6VHOrT53QQ+4/LGqa3A46xvoqV/e1kBwI4chfvrMEjCSyhkSGIjSdE9PhNY9lgBytfxXWmtPEdrmC5vz8O4p9OqN8P3zBY0viOZE+4DkPxAoGBAJUT2oDnSy4q8QI65onTR0oWqjOLDOY6bAsuaTXwrGNWPJSWYyh60b6Bd3Zoob5zXw1uAkPDD8rbq8wmDA1tw6WxpWodrrqJeQZ/7oFdJ1rc8LDoaM6hBxsTRcos/MhgTqPku72oVMrm/oeNGRoY/nFFyjqcmrk5hau2lFL+VNPd

// > Chaves salvas nos arquivos publicKey.txt e privateKey.txt

//[Execution complete with exit code 0]
    
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
