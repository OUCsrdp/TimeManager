package main.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;


//import org.apache.commons.codec.binary.Base64;

public class CipherUtil {

	/**使用AES对字符串加密
     * @param str utf8编码的字符串
     * @param key 密钥（16字节）
     * @return 加密结果
     * @throws Exception
     */
    public static String aesEncrypt(String str, String key) throws Exception { 
           if (str == null || key == null) return null; 
           Cipher cipher = Cipher.getInstance("AES"); 
           cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getBytes("utf-8"), "AES")); 
           byte[] bytes = cipher.doFinal(str.getBytes("utf-8")); 
          
           
           return  Base64.encodeBase64String(bytes);
       } 
    /**使用AES对数据解密
     * @param bytes utf8编码的二进制数据
     * @param key 密钥（16字节）
     * @return 解密结果
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
