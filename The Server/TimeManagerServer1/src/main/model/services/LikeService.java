package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import main.model.db.*;

public class LikeService{
	public int thumb(int userId,int idTs) {
		//用户是指当前用户id
		//操作：1.添加到like表2.sharedTable中的这一项thumbup要加一
		//要先查看是否已经有这层关系，即点赞只能一次
		LikeManager managerL = new LikeManager();
		ArrayList<Like> findList = managerL.findWithIdTS(idTs);
		int count = 0;
		for(Like a:findList) {
			if(a.getIdUser() == userId) {
				count ++;
				return -1; //已经赞过，不能再次赞
			}
		}
		if(count == 0) {
			SharedTableManager managerTS =  new SharedTableManager();
			SharedTable curTS = managerTS.findWithIdTS(idTs); //需要最新版类，idTS
			int thumb = curTS.getThumbup();
			thumb = thumb + 1;
			curTS.setThumbup(thumb); //点赞数加一
			if(managerL.add(userId, idTs) != -1) {
				if(managerTS.change(curTS)) return 1;
			}
			else  return -1;
		}
		else return -1;
		return -1;
		
	}
	//注意一个用户只能给一张时间分配表点赞一次，返回用户点赞后的点赞数，失败返回-1

}