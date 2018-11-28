package main.util;

import main.model.moudle.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security; 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.util.Random;

public class TokenUtil{
	
	/*//KeyGenerator �ṩ�Գ���Կ�������Ĺ��ܣ�֧�ָ����㷨	
	private KeyGenerator keygen;	
	//SecretKey ���𱣴�Գ���Կ	
	private SecretKey deskey;	
	//Cipher������ɼ��ܻ���ܹ���	
	private Cipher c;	
	//���ֽ����鸺�𱣴���ܵĽ��	
	private byte[] cipherByte;
	//����TOKEN
	public static  String encodeToken(String token,int userId){
		String final_token = userId+"_"+token;
		return final_token;
			
	}
	//����TOKEN
	public static String  decodeToken(String token) {
		String sub = token.substring(0, token.indexOf("_"));
		return sub;
	}*/
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
	
	//����
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
		//����
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