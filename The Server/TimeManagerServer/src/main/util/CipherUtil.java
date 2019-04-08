package main.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;


//import org.apache.commons.codec.binary.Base64;

public class CipherUtil {

	/**ʹ��AES���ַ�������
     * @param str utf8������ַ���
     * @param key ��Կ��16�ֽڣ�
     * @return ���ܽ��
     * @throws Exception
     */
    public static String aesEncrypt(String str, String key) throws Exception { 
           if (str == null || key == null) return null; 
           Cipher cipher = Cipher.getInstance("AES"); 
           cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES")); 
           byte[] bytes = cipher.doFinal(str.getBytes("utf-8")); 
          
           
           return  Base64.encodeBase64String(bytes);
       } 
    /**ʹ��AES�����ݽ���
     * @param bytes utf8����Ķ���������
     * @param key ��Կ��16�ֽڣ�
     * @return ���ܽ��
     * @throws Exception
     */
       public static String aesDecrypt(String byte1, String key) throws Exception { 
    	   byte[] bytes = Base64.decodeBase64(byte1);
           if (bytes == null || key == null) return null; 
           Cipher cipher = Cipher.getInstance("AES"); 
           cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES")); 
           bytes = cipher.doFinal(bytes);
           return new String(bytes, "utf-8"); 
       } 


<<<<<<< HEAD
}
=======
}
>>>>>>> 8342845777e737e912c6b21b32707e1561d43aea
