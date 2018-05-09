package main.model.services;
//package main.model.services;  

import main.model.moudle.*;
import main.model.db.*;

public class UserService {
	private String verify="1234";
	private String verifyR="1234";
	
	public String register(User user,String verify) {
		UserManager managerU = new UserManager();
		if(managerU.findWithName(user.getName()) == null) { //该用户名没有重复注册过
			if(verify.equals(this.verifyR)) { //验证码正确
				if(managerU.add(user.getNumStu(),user.getSchool(),user.getMajor(),user.getGPA(),user.getName(),user.getImage(),user.getPwd(),user.getTimeRegister()) != -1) return "success";
				else return "fail";
			}
			else return "verifyfail";
		}
		else return "usernamefail";
	} //注册成功返回1，失败返回0
	
	int judgeVerify(String verify) {
		if(verify.equals(this.verify)) return 1;
		else return 0;
	}
	
	public String getVerify() {
		/*String randomCode = CodeUtils.getRandomCode(CodeUtils.TYPE_NUM_CHAR, 4, null);
		verify = randomCode;
		BufferedImage imageFromCode = ImageUtils.getImageFromCode(randomCode, 100, 50, 3, true, Color.WHITE, Color.BLACK, null);
		try {
			File file = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");
		    ImageIO.write(imageFromCode,"jpg",file);
		    return Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg";
		} 
		catch (IOException e) {e.}*/
		return "1234";
        
	}
	
	public String login(String username,String password,String verify,String token) {
		if(token != null) {
			TokenManager managerT = new TokenManager();
			UserManager managerU = new UserManager();
			if(managerU.findWithName(username) != null) { //该用户存在
				User curU = managerU.findWithName(username);
				if(password.equals(curU.getPwd())) { //密码正确
					if(judgeVerify(verify) == 1) { //验证码正确
						//保存token到数据库
						if(managerT.add(token) != -1) return token;
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
		//TokenManager manager = new TokenManager();
		if(TokenManager.findWithToken(token) != null) return 1;
		else return 0;
	} //判断token是否存在，如果存在返回1，不存在返回0
	
	public int changeInfor(User user) {
		UserManager manager = new UserManager();
		int id = user.getId();
		//如果该用户存在
		if(manager.findWithId(id) != null) {
			if(manager.change(user)) return 1;
			else return 0;
		}
		else return 0;
	} //修改用户信息
	
	public User getUserInfor(String UserName) {
		//UserManager managerU = new UserManager();
		if(UserManager.findWithName(UserName) != null) { //该用户存在，返回用户信息
			User user = UserManager.findWithName(UserName);
			return user;
		}
		else return null; //该用户不存在，返回null
	}

	public int logout(int UserId,String token) { //这里我需要该用户的token
		UserManager managerU = new UserManager();
		TokenManager managerT = new TokenManager();
		User user = managerU.findWithId(UserId);
		if(user != null) { //该用户存在
			Token token1 = managerT.findWithToken(token);
			if(token1 != null) { //token存在
				if(managerT.delete(token1.getId())) return 1; //删除成功
				else return 0;
			}
			else return 0;
		}
		else return 0;
	}
	
	public int changePassword(int UserId,String pwdOld,String pwdNew) { //我需要旧密码的输入以及新密码两个密码
		UserManager managerU = new UserManager();
		User user = managerU.findWithId(UserId);
		if(user != null) { //该用户存在
			String pwd = user.getPwd();
			if(pwdOld == pwd) { //旧密码输入正确
				user.setPwd(pwdNew); //修改密码
				if(managerU.change(user)) return 1;
				else return 0;
			}
			else return 0;
		}
		else return 0;
	}
}
