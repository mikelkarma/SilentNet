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
            String privateKeyBase64 = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCt7W+2z6lrVYpBYEstUZWYIJgcTsn7waK8K8rWWEnAjzdMUaVDAIzJiM/kwbOCPBvqX+PxuaG4ssXnCPhWR4lB1pKLxlRJkqrXcg3RiWHYIjc2xgwMO0cOjDlGEnFdG+MuNr4AwbQkfXz69MBvfvKHyNb5SJNJ0Fytkd1v0NRAZlx2vhsKh0SaDmDej1OhRu+WkXqnIIRMnNCx0Z7ll1uff+I5gAMZcdh+MuprXqma5TQuLxsyNGxIY6bnQ96d10QQp4YgFp/7P0fEfPdWQceSZ+n+GEmwuTekMmIFQ6uW2qjp8SJqN4qrU7rxnP6WjSjZjQgt2E1G30iQiawy8dflAgMBAAECggEAPip2OhjaXNdrvbdN14poFkJcLA4LStVotapPazKOVem6oagzEhJBwA0LBaAoab3TWn/gqPcpMyuO6ee+X5RdHm+z3IrMeKmYL75/6uuYUdjU9FpwVvTfDneXRxDzfQypNEzzt7CEk2BZChfuKkvtIxRpphtHLeRwI8fVppA1NTjpFoMSNDeLrqCu2PpqAZm2J8d9ss0L8ALPuDGS5imnl3NtqqpfqWJXWlaR5vpBpqd7PEYipncwCsJ/hpNMzWovuv4uqfTXHgDya2WdKIfGjoC4h5FrfclAEonLLr1r5NDPWH1VYzJM4OhjMFpA3uRLS4ksQOhRbNR8Hs9qwWEjxwKBgQC7ENSYcsrXD08Sblw0gGMHU51FVyr+YuZJmwn74ZvRIOctAGKeYuz8ik46DIRSWStKvkha4VuTTQsijEtu9bihCn8g5zDulDUuTuWmn9RzY66ndEktYWPkgevlqt/fTEN+3Hi9ApXeGXCashu2izQ9bO8HdstaeEp8Si8UdYVRKwKBgQDuBS+J/86+3FqEN3+jpmupQIayedMI7m52DREC4Tkh7WLjWix85Tp0LVBOlc/N3JtRbp5gZhKKTnVT1iI30GRkr0OxkwVdFszOd9YpC5LRosRwjPI1IdsxOW9NWSdcVbrnqEYWSTQzQNTV6egH6FGnyzB2Ax/qxzddAv0hKPVTLwKBgQCPOcmsl2ea1cvS7tCGGi64M1rc9mqirxJnFN/GDO0CZ9DYcc0QO+n7SeGvLFo7uq4c4if//T/iPkDiSMyQvm6ITsMbbCfY43z8YcgPlZxSZOYarDxDFKgpExiMlhImo/uhrCh9fvdEP/IpX2k8kMSEq10wN9JnCGbvVtVJMtnKVQKBgQDrYi94LoUb1LijJmtQwKSZtZk/aSCdJYYRsqxPB8JDXgo8QhHa1apv99wCSz89G1xyNI3lUSwmoJ6A8TPXtHuD4pt0Qau9Om4CBRrCbpKcRHY1w/1HNBsbW+xJNtCpDJMQwyXEplI8GXIhtfbj0wjlghiDrGVk/ewvTGRd5a5uuwKBgGwOAYAlsntpJxuJKhyQv/o3lGHjEcsR07cDkqCAkxARKldmB+dcY5HwJY1B7x3hK5q35lmyQpw+MzcK5pAQ7XSPovM+WxJqyszkKIemzrkUkMa5wuLOMpu87BajqNTpbhWQkUS/q5LWxoOWFWKgcoBVmjdPKDsUTTKmU9a7AO59"; // Sua chave privada em Base64 aqui
            // Exemplo de dado criptografado (substitua pelo dado criptografado que deseja descriptografar)
            String encryptedDataBase64 = "UXYrKzJsVEFXbGpadlYyMkVZazBmNUc3R01XR1huMTJlclJNV3h4eVZCMjVoZDJ4dUN1Tjd5eVlqNDhORHlPWkpKbmcwOEQ3WWNtTDNVRlIwNk1FR2p4Yy9CZE0reWVDZjUxdU5Yb3pqdTFUbDBwMGR4YVhxclZROVNxV3VhZDRlK1BrMEJrSnVWOHZCYmhjSzBTSS9XSkR3WGpjSDZMMy9NOE5ZYTErcGpqbmlERERWV2RZR0xwazd4UlVVcy9LejhGWXhHYmRDWVRkZTBJRW9JN2FhYlZoNW9EcWJjU2VJOTV6Y21MVHpCcDlHNUZlTGhVWE5PUmI0ZVBMZWU4d2ZzU0FYS2FzNWdORHZzdFBDNCtSNHhwYk5wYmJjUHh5a09vR0RhYWJEVU1QaUpZM0Qxb0RlcHhnME0rVStuWkNQdXRzWSt3dzU3MVl2alE2dTZUeE13PT0=,Z3JrdjZvZzVXWTVPN3dwWGFVZFB2R0FaaS9vRzJqZTFDL0UvTUZyTVBicmxhdFUwZXNoYjZLN3llNCs5VVEvN2FKKys0UFhIaVZ4OGJjZ1hWSXZ2aGRRaXZmZkNiMHVKc24wS3NHOWZ5WitGajFqcUtYTDNvQUJZS3RXTXQ4SmRreFFxanVFOUNSZ2N3bVFJK1FjdjhGQmhrNWVGQlFXUDkzK254SVFHOFoxU2s5ODQ5NGpkMWw0YlF2OEpxRVJURlRINkNBTW5kRCs0L09MdUYrUmlHYTIrWUVkYWthbmRDc2ZJbjBPNVdEci9YcmZZN3Y0UFJ6VFJCSzNzMWVKVC9VLzFSVU14ZDBYMVBUcEhuYzRKTmNmKzNkc1EwR1dDb1FEcnBQdHhUbjVJZHpvWmZFNnRyYmxhQTFHT2h0ekxGRUduNnVhdEtEV010eVgzQ2hXQkMzMUhtanR5YUhVS09URjdQZXNqWldoVVNqbWg4M2dxTFFzN2g4Zkl0UnB1NjdNOTJnOEJWWEQ0dUpJQUtoMWRNclRjZEsweGYyMlBwMms2QlpEUHNTOGFJSEp2dmxOWDZNL01ZcStoTHkzSjZJakZRam0vRTkwWU1uVi9oU3ZvdU5tYnA3K3UyU0JsSTU5S2o1V201SURZeXEwa3Y5Qm5aQlZ4MEY1OU1JYUJCMGJlaTFteDdjU3BJNWhsdXBZSXB3PT0=,ay95VEJqa0ZIWnZ4elFwZnVKQVNoczAwVEU4bEd4NW5ONjR3eUludjNOMGM0SWtGeGFGejZHTG8zNStCN3ZRMQ==,S2lCYnVNRDdZdHcrak1XdVRMMGxBQ2twM1R5R1YxdjMwMDBhTzE4SUY0YzNaS3MrbjhETzFiZmJETHRhUWlhdVh6ZHJ0UVVFRVNkRGNGNG15cldEUnJwVGN2cTBWTnBXVzJOUVo3Q2cvUlJBQk1VSXF4ZEVrRjIvVXBKUDdGb2c="; // Dado criptografado em Base64 aqui

            // Descriptografar os dados
            String decryptedData = decryptRSA(encryptedDataBase64, privateKeyBase64);

            // Imprimir os dados descriptografados
            System.out.println("Dados Descriptografados: " + decryptedData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
