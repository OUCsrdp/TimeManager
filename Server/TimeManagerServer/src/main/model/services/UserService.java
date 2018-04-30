package main.model.services;
package com.zzw.getPhoneInfos;  

import android.app.Activity;  
import android.content.Context;  
import android.os.Build;  
import android.os.Bundle;  
import android.telephony.TelephonyManager;  
import android.util.Log;
import main.model.moudle.*;
import main.model.db.*;

public class UserService {
	private String verify;
	private String verifyR;
	
	public String register(User user,String verify) {
		UserManager managerU = new UserManager();
		if(!managerU.findWithName(user.getName())) { //���û���û���ظ�ע���
			if(verify.equals(this.verifyR)) { //��֤����ȷ
				if(managerU.add(user)) return "success";
				else return "fail";
			}
			else return "verifyfail";
		}
		else return "usernamefail";
	} //ע��ɹ�����1��ʧ�ܷ���0
	
	int judgeVerify(String verify) {
		if(verify == this.verify) return 1;
		else return 0;
	}
	
	public String getVerify() {
		String randomCode = CodeUtils.getRandomCode(CodeUtils.TYPE_NUM_CHAR, 4, null);
		verify = randomCode;
		BufferedImage imageFromCode = ImageUtils.getImageFromCode(randomCode, 100, 50, 3, true, Color.WHITE, Color.BLACK, null);
		try {
			File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
		    ImageIO.write(imageFromCode,"jpg",file);
		    return Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg";
		} 
		catch (IOException e) {}
        
	}
	
	public String login(String username,String password,String verify��String token) {
		if(token != null) {
			TokenManager managerT = new TokenManager();
			UserManager managerU = new UserManager();
			if(managerU.findWithName(username)) { //���û�����
				User curU = managerU.findWithName(username);
				if(password.equals(curU.getPwd())) { //������ȷ
					if(judgeVerify(verify)) { //��֤����ȷ
						//����token�����ݿ�
						if(managerT.add(token)) return token;
						else return "fail";
					}
					else return "verifyfail";
				}
				else return "passwordfail";
			}
			else return "usernamefail";
		}
	} //��¼�ɹ�����token,ʧ�ܷ��ء�usernamefail��,��passwordfail��,��verifyfail��
	
	public int judgeToken(String token) {
		TokenManager manager = new TokenManager();
		if(manager.findWithToken(token) != null) return 1;
		else return 0;
	} //�ж�token�Ƿ���ڣ�������ڷ���1�������ڷ���0
	
	public int changeInfor(User user) {
		UserManager manager = new UserManager();
		long id = user.getid();
		//������û�����
		if(manager.findWithId(id) != null) {
			if(manager.change(user)) return 1;
			else return 0;
		}
		else return 0;
	} //�޸��û���Ϣ
	
}
