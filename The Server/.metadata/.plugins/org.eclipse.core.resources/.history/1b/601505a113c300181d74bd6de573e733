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
	
	//KeyGenerator 提供对称密钥生成器的功能，支持各种算法	
	private KeyGenerator keygen;	
	//SecretKey 负责保存对称密钥	
	private SecretKey deskey;	
	//Cipher负责完成加密或解密工作	
	private Cipher c;	
	//该字节数组负责保存加密的结果	
	private byte[] cipherByte;

	
	
	//加密TOKEN
		public static  String encodeToken(String token,int userId) throws NoSuchAlgorithmException, NoSuchPaddingException{
			String final_token = userId+"_"+token;
			/*Security.addProvider(new com.sun.crypto.provider.SunJCE());		//实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)		
			keygen = KeyGenerator.getInstance("AES");		//生成密钥		
			deskey = keygen.generateKey();		//生成Cipher对象,指定其支持的DES算法		
			c = Cipher.getInstance("AES");*/
			return final_token;
			
		}
		//解密TOKEN
		public static int  decodeToken(String token) {
			String sub = token.substring(0, token.indexOf("-"));
			int id = Integer.parseInt(sub);
			return id;
		}
}