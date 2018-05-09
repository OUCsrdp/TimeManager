package main.model.services;
//package main.model.services;  

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;


import main.model.moudle.*;
import main.model.db.*;

public class UserService {
	private String verify;
	private String verifyR;
	
	public String register(User user,String verify) {
		UserManager managerU = new UserManager();
		if(managerU.findWithName(user.getName()) != null) { //���û���û���ظ�ע���
			if(verify.equals(this.verifyR)) { //��֤����ȷ
				if(managerU.add(user.getNumStu(),user.getSchool(),user.getMajor(),user.getGPA(),user.getName(),user.getImage(),user.getPwd(),user.getTimeRegister()) != -1) return "success";
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
	
	
	
	public byte[] getVerify() {
		/*String randomCode = CodeUtils.getRandomCode(CodeUtils.TYPE_NUM_CHAR, 4, null);
		verify = randomCode;
		BufferedImage imageFromCode = ImageUtils.getImageFromCode(randomCode, 100, 50, 3, true, Color.WHITE, Color.BLACK, null);
		try {
			File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
		    ImageIO.write(imageFromCode,"jpg",file);
		    return Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg";
		} 
		catch (IOException e) {}*/
		try {
			int width = 80;
			int height = 40;
			int lines = 10;
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)img.getGraphics();
	
			g2d.setFont(new Font("����", Font.BOLD, 20));
	
	
			Random r = new Random(new Date().getTime());
	
			//���ñ���ɫ
			g2d.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			g2d.drawRect(0, 0, width, height);//����ָ�����εı߿�
			g2d.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
			g2d.fillRect(0, 0, width, height);//���ָ���ľ��Ρ�
			
			
			for(int i=0;i<4;i++){
				String str = ""+r.nextInt(10);
				verify += str;
			   //������ת
			   AffineTransform Tx = new AffineTransform();
			   Tx.rotate(Math.random(), 5+i*15, height-5);
			   //�û��Ȳ�������ת�Ƕ�,��תê��� X ����,��תê��� Y ����
			   //Tx.scale(0.7+Math.random(), 0.7+Math.random());
			   //x���귽������ű�����y���귽������ű���
			   g2d.setTransform(Tx);
			   Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			   g2d.setColor(c);
			   g2d.drawString(str, 2+i*width/4, height-13);
			}
			
			//������
			for(int i=0;i<lines;i++){
			   Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			   g2d.setColor(c);
			   g2d.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
			}
			
			g2d.dispose();
			
			//ImageIO.write(img, "JPG", new FileOutputStream("./verify.jpg")); 
            ByteArrayOutputStream baos = new ByteArrayOutputStream();   
            ImageIO.write(img, "jpg", baos);   
            byte[] bytes = baos.toByteArray();   
               
            return bytes; 
		}
		catch(Exception e) {}
		return null;
			
        
	}
	
	public String login(String username,String password,String verify,String token) {
		if(token != null) {
			TokenManager managerT = new TokenManager();
			UserManager managerU = new UserManager();
			if(managerU.findWithName(username) != null) { //���û�����
				User curU = managerU.findWithName(username);
				if(password.equals(curU.getPwd())) { //������ȷ
					if(judgeVerify(verify) == 1) { //��֤����ȷ
						//����token�����ݿ�
						if(managerT.add(token) != -1) return token;
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
		int id = user.getId();
		//������û�����
		if(manager.findWithId(id) != null) {
			if(manager.change(user)) return 1;
			else return 0;
		}
		else return 0;
	} //�޸��û���Ϣ
	
	User getUserInfor(String UserName) {
		UserManager managerU = new UserManager();
		if(managerU.findWithName(UserName) != null) { //���û����ڣ������û���Ϣ
			User user = managerU.findWithName(UserName);
			return user;
		}
		else return null; //���û������ڣ�����null
	}

	int logout(int UserId,String token) { //��������Ҫ���û���token
		UserManager managerU = new UserManager();
		TokenManager managerT = new TokenManager();
		User user = managerU.findWithId(UserId);
		if(user != null) { //���û�����
			Token token = managerT.findWithToken(token.getToken());
			if(token != null) { //token����
				if(managerT.delete(token.getId())) return 1; //ɾ���ɹ�
				else return 0;
			}
			else return 0;
		}
		else return 0;
	}
	
	int changePassword(int UserId,String pwdOld,String pwdNew) { //����Ҫ������������Լ���������������
		UserManager managerU = new UserManager();
		User user = managerU.findWithId(UserId);
		if(user != null) { //���û�����
			String pwd = user.getPwd();
			if(pwdOld == pwd) { //������������ȷ
				user.setPwd(pwdNew); //�޸�����
				if(managerU.change(user)) return 1;
				else return 0;
			}
			else return 0;
		}
		else return 0;
	}
}
