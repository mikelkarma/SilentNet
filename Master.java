// > encryptedSignature: rBTmiT39d6g2RdnQxOa4SLFhcnFGY0yLmcEhiy8EPvVPJpffwzIc0WSfqqJhs/HpjrSQyr0jW4jbC6pkZJfPhQon08FqHjfDA4qQzCG/PtPaDPNU3KnYn8rNoWpCK5I8xw+8w4+4NXij0Um2FP5nXgjsiyCTRENnbjmrh7Z0WFDLMN/84VOUjx9ifpQba8nP1NBY9l3Hv5GqOxZaJbzq8rPfuobKyldiYeTRVi8elQfstmTvI96nn6Mcc32JoFsBB76VpskhD4V77+TuCNYSHNi423eoOX9zFLhzdWXnADjwpqxlNZzlZah/i0txM3hJcA0cwFN8Cn32qfqPQgZhsTPsi8vVjqlV2w5Z4JwkIXgCGc8mkFsWRTstlFlQVNtyhhAJkPgwthtMVyYJSXJWMhL32wQ41vDWf12RyJWJx8BaJEeyBNLL6KjCglIhZMNWLOonsYL9eVCzt+NqknlYJQ==
// > encryptedAESKey: pkzRlK1F6mxOYZ5yvO7PCYrW2mhF3y1bG/ZzjST7jWSA2yo1vVnYhZG8xoBc1cffucDp1+N7FSAmh80OG+THOAKQKBTjjY3Ee3p5OP4TYAUwXm7jIfUGXQ/OedjAe69/jrUu9cH2ob1TQjEh/FSQE4s97nN0x70YgdYQFJKrpWHwi4fPYbVjYjLHheUQnjIyq3uaY0AIYN+HRsfIOZY9/b7WkqPMwmB+0pZCeAzucXOPluj8CGKn1q5ZaMBqxPm7GBTh5vjJhJ08HGa3k3kC+yA1o+6g9IyzqLyz8ytbtuuGBKpuUndiAR3/8blpSPtn5uKDO0lMCSSnQOF6Rv7YBA==
// > encryptedData: uFsJxAA3vt5fzA2j6sOBLXylI1bOwf1tB8q0Vk6ZirI=
// > encryptedPublicKeyClient: SQrgbKTyMe26ZxUkXk8VmBoJfzkl9QlG0w7TN2eIv9XY+JYF7v62w3keWXRMot0z5QJ9lzfjIhpNnOz2llnZghjJhvnU7H0W/LXo85DJxWDIHdW+Yjatc47yddDFrAbBh3hu+YkJDj83bUXd1d5A9Ei2wY25zrmxpDEdkgWulvR8tIa3LKKldHN2J55uCV4pYRcyQ0fTX7sDZ4It7b1wvemwxJHal7ASGMH1XHScXFur0uMw3DufKpxEPmmsAh56PVZaYm62p/3ez5t+nvENFJNMmIK2HmHTdJSdA8VzukeYj6fFwwbBnVLbd/XqSjT3oW8hBJ5/rolxTyUxs8phMzzaFoXKOGFEqcGrFEo29thaY+Ve7DaVU34d8/FJthaHqRf9hjBtmzAq1TjuPrL+nqwLxo4fjMOxzadMRoVwZ+5YOoPAR+/tniFhz552cnuX4v+s9o+mvvuY5LStoqXzjPlGWSZj1TwchF0UtGB2MKIXjNm2ckAh6svQI5biH8zofAkPRU7djHAmepJ5/Jc52A==

// > Decrypting...
// > MasterKeyPrivate: sun.security.rsa.RSAPrivateCrtKeyImpl@7b7b0db6
// > Chave AES descriptografada
// > Assinatura descriptografada: F8zURJXD1peawri3WxQcqNoBPgv9tokqtoSnNPNxO/O/ucKh7+0N5yjhdoAaXOTjdPLwDfmKgMpp1j4KN40KdYhcQFqopgOSdq0qc/usjqOVZwWwzrVvl4w3FpPPY/FjrmtDCYsu0PfTpX5qkN/0QFg1+xQZIgFbrInYO4Ohp2BA6z5pnEbfj/KiSH/17y4/8Xc253X1iuxrBnkzuDEyIl6E95jt0vffoeWmXiYjRA5qJBk6shA2dh82nA1Oq2ntxdDxpRMxadMZ2TpVHHHyLNQX46C5n1Im+naeTwmPS+n6I9P2EYoCw23ruzNN6oQDpTlKHTsl/fURvbShY4zslw==
// > Dados descriptografados: Informação confidencial
// > Verificação de assinatura: true

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
    static PublicKey publicKeyMaster;
    static PrivateKey privateKeyMaster;

    static {
        try {
            // Chave RSA master fornecida em Base64 (em um ambiente real, isso seria uma chave real)
            String base64MasterPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxxuO8AEH7eG0yh+ksm/lY64020Ptif8HGx/m29fPuthtEVYLrhZVUVmQhgCBk5+XF28c79s+EsVyJQZ9pNqcjtANLn2xL4qwPUcBRer5zUCnZAxJRhPrPZX1xMc3XHaqVOd3rGirrie/OA/yzFNPoqKEZgBrYxEeH/sDqMaiRWYyoVM8jRB2jIVjwbgDCx6qWlddNCfQq9wLkQFZSC3M2K+7o0FPnymL1VHxaiZki4/wsxfPX8OSRi2EzMLIWJwVO39LPhbC58X/SWAohMIwV74mU78/Tn1pW7IpGTWfVS3/FCdswbSc3kbH1/StfPy27+u+yMlQvECVY4BShIeCfQIDAQAB";
            String base64MasterPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHG47wAQft4bTKH6Syb+VjrjTbQ+2J/wcbH+bb18+62G0RVguuFlVRWZCGAIGTn5cXbxzv2z4SxXIlBn2k2pyO0A0ufbEvirA9RwFF6vnNQKdkDElGE+s9lfXExzdcdqpU53esaKuuJ784D/LMU0+iooRmAGtjER4f+wOoxqJFZjKhUzyNEHaMhWPBuAMLHqpaV100J9Cr3AuRAVlILczYr7ujQU+fKYvVUfFqJmSLj/CzF89fw5JGLYTMwshYnBU7f0s+FsLnxf9JYCiEwjBXviZTvz9OfWlbsikZNZ9VLf8UJ2zBtJzeRsfX9K18/Lbv677IyVC8QJVjgFKEh4J9AgMBAAECggEATflnaWtl8Q2W0R9CTxUOJxLmpUX9ZTEIVeGQ9eCW/Avc7tCdQ2CAqhqixC+3jxoNQEKl+PMJ5K0JbiGUG/b3H+CvgNj9hmpQabcAcyK6EF01ELPVEEolspkjRhxkfGQGgbiiP9VAArSTKL/qLIWl5sGxiwa4B3SBMTIK9VJQ408YbNxFljmTFxeJVV+zDO7uspaQO2aMpniCFcBV7UtQv1YRs5rA10nUxqdUuMWIPgwB7ZYmnUJuBG8wJndDkXGSKTILPEWwsVnpa+FGD3CbsEj0jh8hPR1PSL0xeyVYCRyYeu7y0uXgpzOE7o85NZfVpv9enw8yfwgeJmEI1rNeGwKBgQD5IrwUz5L6yetbI/Jj8qemAf5ju+pltyqu8RhbktkVvneZ0MWa1tnZ+1Ui4Q2a8JDRHoaWYK/0nnouOIrWbzvbId/NN4A2UvhPdqYYOSCsfrjoXy7JQEEu2DiEyKfLDxT9E/xZYl0uXAXcUm78lb0V3+WXWBaycz8wiuP4niuHTwKBgQDMl/QeL0cFBvxHLVgjKU1P92cqPDsLGdODjW2eszqXd4Msu4hLZY1hmhwiBBMbXtaHCOJF9EJEye+WlCUDWE5AfHAqAKdtIuTUck/BmYKNjxfbIeV+t0wWyNU69fj334imuAsjyr1ezuVRa4mDeK0Py8Ny0/sDNGWj/+S1d9wmcwKBgEG+/+GCEeirBrhLmTj1HXb6ybZqLeZf2xzFtUbdTvqlBA0Mgkllb6JN6F7HA38jAyScqKngpC6q79VoyILkYehnXRdcETLE3JCj4Bs+YDgFTSjNjZxAn7MMDoZEKbu1pDVRY+P2yF5mnIlTG8T+gyLVrlItRfgb384gqR7AJv6TAoGBAKTMybD+uCBOjivvVI2OGV6OlKUquwyoEv5mj6REDrAvbpQ7wv+on4e9EWg9C/8CF5/SAiIDLNYoKzuJvX7CRNYg5mCPAk4gy15tbd0ugEDywG6dETVPBJVd1JSXyaIwhux0V1TPXFbgJUu+aZhjPlP4+EiohAsCy9ScrWeXUtgNAoGATRkdOz/RJAZh1AV+dAxFPjFIMRMsMfp9si+vZkj3sh7y4qQPNFCUiSq3QZw/QHw9tz2q1MEkXve0NXHcYJX/y2g7NaWpLiNg/WBLiGkbnL8/L3vSJQkXWxtdMOHIdHjDob+wHF/9sHYtsVGUqZUnKWM5I/nb6bl9pP5i4iCpgRY=";
            
            // Convertendo as chaves de Base64 para Key objects
            byte[] publicKeyBytes = Base64.getDecoder().decode(base64MasterPublicKey);
            byte[] privateKeyBytes = Base64.getDecoder().decode(base64MasterPrivateKey);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            publicKeyMaster = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
            privateKeyMaster = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao inicializar as chaves RSA");
        }
    }
    public static KeyPair generateRSAKey() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(3072);
        return keyPairGenerator.generateKeyPair();
    }

    public static SecretKey generateAESKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        return keyGenerator.generateKey();
    }
    public static boolean verifySignature(byte[] data, String signatureStr, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
        return signature.verify(signatureBytes);
    }
    

    public static String encryptRSA(SecretKey aesKey, PublicKey rsaPublicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        byte[] encryptedAESKey = cipher.doFinal(aesKey.getEncoded());
        return Base64.getEncoder().encodeToString(encryptedAESKey);
    }

    public static SecretKey decryptRSA(String encryptedAESKey, PrivateKey rsaPrivateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        byte[] decodedEncryptedAESKey = Base64.getDecoder().decode(encryptedAESKey);
        byte[] decryptedAESKey = cipher.doFinal(decodedEncryptedAESKey);
        return new SecretKeySpec(decryptedAESKey, "AES");
    }
    public static void print(String text) {
        System.out.println(text);
    }

    public static String encryptAES(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static String decryptAES(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedData = Base64.getDecoder().decode(data);
        byte[] decryptedData = cipher.doFinal(decodedData);
        return new String(decryptedData);
    }

    public static void main(String[] args) throws Exception {
        
        String input = "rBTmiT39d6g2RdnQxOa4SLFhcnFGY0yLmcEhiy8EPvVPJpffwzIc0WSfqqJhs/HpjrSQyr0jW4jbC6pkZJfPhQon08FqHjfDA4qQzCG/PtPaDPNU3KnYn8rNoWpCK5I8xw+8w4+4NXij0Um2FP5nXgjsiyCTRENnbjmrh7Z0WFDLMN/84VOUjx9ifpQba8nP1NBY9l3Hv5GqOxZaJbzq8rPfuobKyldiYeTRVi8elQfstmTvI96nn6Mcc32JoFsBB76VpskhD4V77+TuCNYSHNi423eoOX9zFLhzdWXnADjwpqxlNZzlZah/i0txM3hJcA0cwFN8Cn32qfqPQgZhsTPsi8vVjqlV2w5Z4JwkIXgCGc8mkFsWRTstlFlQVNtyhhAJkPgwthtMVyYJSXJWMhL32wQ41vDWf12RyJWJx8BaJEeyBNLL6KjCglIhZMNWLOonsYL9eVCzt+NqknlYJQ==,pkzRlK1F6mxOYZ5yvO7PCYrW2mhF3y1bG/ZzjST7jWSA2yo1vVnYhZG8xoBc1cffucDp1+N7FSAmh80OG+THOAKQKBTjjY3Ee3p5OP4TYAUwXm7jIfUGXQ/OedjAe69/jrUu9cH2ob1TQjEh/FSQE4s97nN0x70YgdYQFJKrpWHwi4fPYbVjYjLHheUQnjIyq3uaY0AIYN+HRsfIOZY9/b7WkqPMwmB+0pZCeAzucXOPluj8CGKn1q5ZaMBqxPm7GBTh5vjJhJ08HGa3k3kC+yA1o+6g9IyzqLyz8ytbtuuGBKpuUndiAR3/8blpSPtn5uKDO0lMCSSnQOF6Rv7YBA==,uFsJxAA3vt5fzA2j6sOBLXylI1bOwf1tB8q0Vk6ZirI=,SQrgbKTyMe26ZxUkXk8VmBoJfzkl9QlG0w7TN2eIv9XY+JYF7v62w3keWXRMot0z5QJ9lzfjIhpNnOz2llnZghjJhvnU7H0W/LXo85DJxWDIHdW+Yjatc47yddDFrAbBh3hu+YkJDj83bUXd1d5A9Ei2wY25zrmxpDEdkgWulvR8tIa3LKKldHN2J55uCV4pYRcyQ0fTX7sDZ4It7b1wvemwxJHal7ASGMH1XHScXFur0uMw3DufKpxEPmmsAh56PVZaYm62p/3ez5t+nvENFJNMmIK2HmHTdJSdA8VzukeYj6fFwwbBnVLbd/XqSjT3oW8hBJ5/rolxTyUxs8phMzzaFoXKOGFEqcGrFEo29thaY+Ve7DaVU34d8/FJthaHqRf9hjBtmzAq1TjuPrL+nqwLxo4fjMOxzadMRoVwZ+5YOoPAR+/tniFhz552cnuX4v+s9o+mvvuY5LStoqXzjPlGWSZj1TwchF0UtGB2MKIXjNm2ckAh6svQI5biH8zofAkPRU7djHAmepJ5/Jc52A==";
        String[] parts = input.split(",");
        
        String encryptedSignature = parts[0];
        String encryptedAESKey = parts[1];
        String encryptedData = parts[2];
        String encryptedPublicKeyClient = parts[3];

    
        print(" > encryptedSignature: " + encryptedSignature);
        print(" > encryptedAESKey: " + encryptedAESKey);
        print(" > encryptedData: " + encryptedData);
        print(" > encryptedPublicKeyClient: " + encryptedPublicKeyClient);
        print("\n > Decrypting...");
        print(" > MasterKeyPrivate: " + privateKeyMaster);
        
        SecretKey decryptedAESKey = decryptRSA(encryptedAESKey, privateKeyMaster);
        print(" > Chave AES descriptografada");

        // Descriptografar a chave pública do cliente com AES
        String decryptedPublicKeyClient = decryptAES(encryptedPublicKeyClient, decryptedAESKey);
        byte[] clientKeyBytes = Base64.getDecoder().decode(decryptedPublicKeyClient);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey pclientKeyMaster = keyFactory.generatePublic(new X509EncodedKeySpec(clientKeyBytes));

        // Descriptografar a assinatura com AES
        String decryptedSignature = decryptAES(encryptedSignature, decryptedAESKey);
        print(" > Assinatura descriptografada: " + decryptedSignature);

        // Descriptografar os dados com AES
        String decryptedData = decryptAES(encryptedData, decryptedAESKey);
        print(" > Dados descriptografados: " + decryptedData);

        // Verificar assinatura
        boolean isSignatureValid = verifySignature(decryptedData.getBytes(), decryptedSignature, pclientKeyMaster);
        print(" > Verificação de assinatura: " + isSignatureValid);
        
    }
}
