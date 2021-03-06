package main.model.services;
//package main.model.services.services;  

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;


import main.model.moudle.*;
import main.model.db.UserManager;
import main.model.moudle.User;
import main.util.TokenUtil;
import main.model.db.*;

public class UserService {
	private static String verify="";
//	private String verifyR;
	public void test(int id)
	{
		System.out.println(UserManager.findWithId(2).getName());
	}
	public String register(User user,String verify) {
		if(UserManager.findWithName(user.getName()) == null) { //该用户名没有重复注册过
			if(verify.equals(this.verify)) { //验证码正确
				if(UserManager.add(user.getNumStu(),user.getSchool(),user.getMajor(),user.getGPA(),user.getName(),user.getImage(),user.getPwd(),user.getTimeRegister()) != -1) return "success";
				else return "fail";
			}
			else return "verifyfail";
		}
		else return "usernamefail";
	} //注册成功返回1，失败返回0
	
	int judgeVerify(String verify) {
		System.out.println("this:"+this.verify);
		if(verify.equals(this.verify)) return 1;
		else return 0;
	}
	
	
	
	public byte[] getVerify() {
		verify = "";
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
			int lines = 5;
			BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D)img.getGraphics();
	
			g2d.setFont(new Font("宋体", Font.BOLD, 20));
	
	
			Random r = new Random(new Date().getTime());
	
			//设置背景色
			g2d.setColor(Color.WHITE);
			g2d.drawRect(0, 0, width, height);//绘制指定矩形的边框。
			g2d.setColor(Color.WHITE);
			g2d.fillRect(0, 0, width, height);//填充指定的矩形。
			
			
			for(int i=0;i<4;i++){
				String str = ""+r.nextInt(10);
				verify += str;
			   //处理旋转
			   AffineTransform Tx = new AffineTransform();
			   Tx.rotate(Math.random(), 5+i*15, height-5);
			   //用弧度测量的旋转角度,旋转锚点的 X 坐标,旋转锚点的 Y 坐标
			   //Tx.scale(0.7+Math.random(), 0.7+Math.random());
			   //x坐标方向的缩放倍数，y坐标方向的缩放倍数
			   g2d.setTransform(Tx);
			   Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			   g2d.setColor(c);
			   g2d.drawString(str, 2+i*width/4, height-13);
			}
			
			//干扰线
			for(int i=0;i<lines;i++){
			   Color c = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			   g2d.setColor(c);
			   g2d.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
			}
			
			g2d.dispose();
			//ImageIO.write(img,"JPG", new FileOutputStream("./verify.jpg"));
			//ImageIO.write(img, "JPG", new FileOutputStream("D:\\srdp\\TimeManager\\The Server\\TimeManagerServer\\verify.jpg")); 
			ImageIO.write(img, "JPG", new FileOutputStream("D:\\srdp\\TimeManagerNew\\TimeManager\\The Server\\TimeManagerServer\\verify.jpg")); 
			ByteArrayOutputStream baos = new ByteArrayOutputStream();   
            ImageIO.write(img,"jpg", baos);   
            byte[] bytes = baos.toByteArray(); 
            System.out.println("verify:"+verify);
            for(int i=0;i<bytes.length;i++) {
    			System.out.print(bytes[i]);
    		}
            return bytes; 
		}
		catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
			
        
	}
	
	
	public String login(String username,String password,String verify,String token) throws Exception, NoSuchPaddingException {
		if(token != null) {
			System.out.println("verify2:"+verify);
			if(UserManager.findWithName(username) != null) { //该用户存在
				User curU = UserManager.findWithName(username);
				token = TokenUtil.encodeToken(token,curU.getId());
				if(password.equals(curU.getPwd())) { //密码正确
					if(judgeVerify(verify) == 1) { //验证码正确
						//保存token到数据库
						if(TokenManager.add(token) != -1) return token;
						else return "fail";
					}
					else return "verifyfail";
				}
				else return "passwordfail";
			}
			else return "usernamefail";
		}
		else return "fail";
	} //登录成功返回token,失败返回”usernamefail”,”passwordfail”,”verifyfail”
	
	public int judgeToken(String token) {
		if(TokenManager.findWithToken(token) != null) return 1;
		else return 0;
	} //判断token是否存在，如果存在返回1，不存在返回0
	
	public int changeInfor(User user) {
		int id = user.getId();
		//如果该用户存在
		if(UserManager.findWithId(id) != null) {
			if(UserManager.change(user)) return 1;
			else return 0;
		}
		else return 0;
	} //修改用户信息
	
	public User getUserInfor(String userName) {
			User user = UserManager.findWithName(userName);
			return user;
	}
	public User getUserInfor(int UserId) {
		User user = UserManager.findWithId(UserId);
		return user;
	}
	public int logout(int UserId,String token) { //这里我需要该用户的token
		
		User user = UserManager.findWithId(UserId);
		if(user != null) { //该用户存在
			Token token1 = TokenManager.findWithToken(token);
			if(token != null) { //token存在
				if(TokenManager.delete(token1.getId())) return 1; //删除成功
				else return 0;
			}
			else return 0;
		}
		else return 0;
	}
	
	public int changePassword(int UserId,String pwdOld,String pwdNew) { //我需要旧密码的输入以及新密码两个密码
		User user = UserManager.findWithId(UserId);
		if(user != null) { //该用户存在
			String pwd = user.getPwd();
			if(pwdOld == pwd) { //旧密码输入正确
				user.setPwd(pwdNew); //修改密码
				if(UserManager.change(user)) return 1;
				else return 0;
			}
			else return 0;
		}
		else return 0;
	}
}
