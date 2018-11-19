package main.model.services;


import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import org.apache.commons.codec.binary.Base64;

public class TokenUtil{
	
	public static String getRandomString(int length){
	     String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	     Random random=new Random();
	     StringBuffer sb=new StringBuffer();
	     for(int i=0;i<length;i++){
	       int number=random.nextInt(62);
	       sb.append(str.charAt(number));
	     }
	     return sb.toString();
	 }
	
	//¼ÓÃÜTOKEN
		public static  String encodeToken(String token,int userId) {
			String final_token = userId+"_"+token;
			
			String key = getRandomString(16);
			String encontent;
			try {
				encontent = CipherUtil.aesEncrypt(final_token,key);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "fail"+e.toString();
			}
		
			String back = encontent + key;
			return back;
			
		}
		//½âÃÜTOKEN
		public static String  decodeToken(String token1) {
			int len = token1.length();
			String key = token1.substring(len - 16);
			String token = token1.substring(0, len - 16);
			System.out.println(key);
			System.out.println(token);
			
			String decontent;
			
			try {
				decontent = CipherUtil.aesDecrypt(token,key);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return "fail2"+e.toString();
			}
			
			
			String sub = decontent.substring(0, decontent.indexOf("_"));
			return sub;
		}
}