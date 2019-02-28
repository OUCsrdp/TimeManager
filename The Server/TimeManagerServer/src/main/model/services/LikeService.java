package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import main.model.db.*;

public class LikeService{
	public String thumb(int userId,int idTs) {
		//用户是指当前用户id
		//操作：1.添加到like表2.sharedTable中的这一项thumbup要加一
		//要先查看是否已经有这层关系，即点赞只能一次
		LikeManager managerL = new LikeManager();
		ArrayList<Like> findList = managerL.findWithIdTS(idTs);
		int count = 0;
		if(findList!=null)
		{
			for(Like a:findList) {
				if(a.getIdUser() == userId) {
					count ++;
					return "likedfail"; //已经赞过，不能再次赞
				}
			}
		}
		if(count == 0) {
			SharedTableManager managerTS =  new SharedTableManager();
			SharedTable curTS = managerTS.findWithId(idTs); //需要最新版类，idTS
			int thumb = curTS.getThumbup();
			thumb = thumb + 1;
			curTS.setThumbup(thumb); //点赞数加一
			if(managerL.add(userId, idTs) != -1) {
				if(managerTS.change(curTS)) return "success";
			}
			else  return "fail";
		}
		else return "fail";
		return "fail";

		
	}
	//注意一个用户只能给一张时间分配表点赞一次，返回用户点赞后的点赞数，失败返回-1

}