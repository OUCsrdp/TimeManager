package main.model;

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


public class TokenUtil{
	
	//KeyGenerator �ṩ�Գ���Կ�������Ĺ��ܣ�֧�ָ����㷨	
	private KeyGenerator keygen;	
	//SecretKey ���𱣴�Գ���Կ	
	private SecretKey deskey;	
	//Cipher������ɼ��ܻ���ܹ���	
	private Cipher c;	
	//���ֽ����鸺�𱣴���ܵĽ��	
	private byte[] cipherByte;

	
	
	//����TOKEN
		public static  String encodeToken(String token,int userId) throws NoSuchAlgorithmException, NoSuchPaddingException{
			String final_token = userId+"_"+token;
			/*Security.addProvider(new com.sun.crypto.provider.SunJCE());		//ʵ����֧��DES�㷨����Կ������(�㷨���������谴�涨�������׳��쳣)		
			keygen = KeyGenerator.getInstance("AES");		//������Կ		
			deskey = keygen.generateKey();		//����Cipher����,ָ����֧�ֵ�DES�㷨		
			c = Cipher.getInstance("AES");*/
			return final_token;
			
		}
		//����TOKEN
		public static int  decodeToken(String token) {
			String sub = token.substring(0, token.indexOf("-"));
			int id = Integer.parseInt(sub);
			return id;
		}
}