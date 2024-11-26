import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Master {
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
        return 
    }
    public static boolean verifySignature(byte[] data, String signatureStr, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data);
        byte[] signatureBytes = Base64.getDecoder().decode(signatureStr);
        return signature.verify(signatureBytes);
    }

    public static String signData(byte[] data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data);
        byte[] signedData = signature.sign();
        return Base64.getEncoder().encodeToString(signedData);
    }
    
    public static void main(String[] args) throws Exception {
        // Entrada criptografada
        String base64MasterPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxxuO8AEH7eG0yh+ksm/lY64020Ptif8HGx/m29fPuthtEVYLrhZVUVmQhgCBk5+XF28c79s+EsVyJQZ9pNqcjtANLn2xL4qwPUcBRer5zUCnZAxJRhPrPZX1xMc3XHaqVOd3rGirrie/OA/yzFNPoqKEZgBrYxEeH/sDqMaiRWYyoVM8jRB2jIVjwbgDCx6qWlddNCfQq9wLkQFZSC3M2K+7o0FPnymL1VHxaiZki4/wsxfPX8OSRi2EzMLIWJwVO39LPhbC58X/SWAohMIwV74mU78/Tn1pW7IpGTWfVS3/FCdswbSc3kbH1/StfPy27+u+yMlQvECVY4BShIeCfQIDAQAB";
        String base64MasterPrivateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDHG47wAQft4bTKH6Syb+VjrjTbQ+2J/wcbH+bb18+62G0RVguuFlVRWZCGAIGTn5cXbxzv2z4SxXIlBn2k2pyO0A0ufbEvirA9RwFF6vnNQKdkDElGE+s9lfXExzdcdqpU53esaKuuJ784D/LMU0+iooRmAGtjER4f+wOoxqJFZjKhUzyNEHaMhWPBuAMLHqpaV100J9Cr3AuRAVlILczYr7ujQU+fKYvVUfFqJmSLj/CzF89fw5JGLYTMwshYnBU7f0s+FsLnxf9JYCiEwjBXviZTvz9OfWlbsikZNZ9VLf8UJ2zBtJzeRsfX9K18/Lbv677IyVC8QJVjgFKEh4J9AgMBAAECggEATflnaWtl8Q2W0R9CTxUOJxLmpUX9ZTEIVeGQ9eCW/Avc7tCdQ2CAqhqixC+3jxoNQEKl+PMJ5K0JbiGUG/b3H+CvgNj9hmpQabcAcyK6EF01ELPVEEolspkjRhxkfGQGgbiiP9VAArSTKL/qLIWl5sGxiwa4B3SBMTIK9VJQ408YbNxFljmTFxeJVV+zDO7uspaQO2aMpniCFcBV7UtQv1YRs5rA10nUxqdUuMWIPgwB7ZYmnUJuBG8wJndDkXGSKTILPEWwsVnpa+FGD3CbsEj0jh8hPR1PSL0xeyVYCRyYeu7y0uXgpzOE7o85NZfVpv9enw8yfwgeJmEI1rNeGwKBgQD5IrwUz5L6yetbI/Jj8qemAf5ju+pltyqu8RhbktkVvneZ0MWa1tnZ+1Ui4Q2a8JDRHoaWYK/0nnouOIrWbzvbId/NN4A2UvhPdqYYOSCsfrjoXy7JQEEu2DiEyKfLDxT9E/xZYl0uXAXcUm78lb0V3+WXWBaycz8wiuP4niuHTwKBgQDMl/QeL0cFBvxHLVgjKU1P92cqPDsLGdODjW2eszqXd4Msu4hLZY1hmhwiBBMbXtaHCOJF9EJEye+WlCUDWE5AfHAqAKdtIuTUck/BmYKNjxfbIeV+t0wWyNU69fj334imuAsjyr1ezuVRa4mDeK0Py8Ny0/sDNGWj/+S1d9wmcwKBgEG+/+GCEeirBrhLmTj1HXb6ybZqLeZf2xzFtUbdTvqlBA0Mgkllb6JN6F7HA38jAyScqKngpC6q79VoyILkYehnXRdcETLE3JCj4Bs+YDgFTSjNjZxAn7MMDoZEKbu1pDVRY+P2yF5mnIlTG8T+gyLVrlItRfgb384gqR7AJv6TAoGBAKTMybD+uCBOjivvVI2OGV6OlKUquwyoEv5mj6REDrAvbpQ7wv+on4e9EWg9C/8CF5/SAiIDLNYoKzuJvX7CRNYg5mCPAk4gy15tbd0ugEDywG6dETVPBJVd1JSXyaIwhux0V1TPXFbgJUu+aZhjPlP4+EiohAsCy9ScrWeXUtgNAoGATRkdOz/RJAZh1AV+dAxFPjFIMRMsMfp9si+vZkj3sh7y4qQPNFCUiSq3QZw/QHw9tz2q1MEkXve0NXHcYJX/y2g7NaWpLiNg/WBLiGkbnL8/L3vSJQkXWxtdMOHIdHjDob+wHF/9sHYtsVGUqZUnKWM5I/nb6bl9pP5i4iCpgRY=";
        byte[] publicKeyBytes = Base64.getDecoder().decode(base64MasterPublicKey);
        byte[] privateKeyBytes = Base64.getDecoder().decode(base64MasterPrivateKey);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKeyMaster = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
        privateKeyMaster = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        
        String input = "encryptedSignature,encryptedAESKey,encryptedData,encryptedPublicKeyClient";
        String[] parts = input.split(",");

        String encryptedSignature = parts[0];
        String encryptedAESKey = parts[1];
        String encryptedData = parts[2];
        String encryptedPublicKeyClient = parts[3];

        SecretKey decryptedAESKey = decryptRSA(encryptedAESKey, privateKeyMaster);
        print(" > Chave AES descriptografada");
        
        String decryptedSignature = decryptAES(encryptedSignature, decryptedAESKey);
        print(" > Assinatura descriptografada: " + decryptedSignature);

        String decryptkeypublic = decryptAES(encryptedPublicKeyClient, decryptedAESKey);
        byte[] publicKeyBytes = Base64.getDecoder().decode(decryptkeypublic);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        publicKeyClient = keyFactory.generatePublic(new X509EncodedKeySpec(publicKeyBytes));
     
        
        // Descriptografar os dados com AES
        String decryptedData = decryptAES(encryptedData, decryptedAESKey);
        print(" > Dados descriptografados: " + decryptedData);

        
        // Verificar assinatura
        boolean isSignatureValid = verifySignature(decryptedData.getBytes(), decryptedSignature, publicKeyClient);
        print(" > Verificação de assinatura: " + isSignatureValid);
    }
}
