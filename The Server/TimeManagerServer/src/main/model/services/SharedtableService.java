package main.model.services;

import main.model.moudle.*;

import java.util.ArrayList;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;

public class SharedtableService{
	
	//鎼滅储涓撲笟鍑芥暟
	public JSONObject getMajorList(String majorKeyword)// majorKeyword浠モ�滅數瀛愪俊鎭�濅负渚?	
	{
		JSONObject back=new JSONObject();
		back.put("majorKeyword", majorKeyword);
		ArrayList<Major> arrayList = MajorManager.findWithWords(majorKeyword);
		JSONArray array = new JSONArray();
		  if(arrayList!=null) {
			  for(Major a:arrayList) {
					String name = a.getMajor();
					if(name.equals(majorKeyword)) { //瀹屽叏涓�鑷存渶鍏?						
						JSONObject js = new JSONObject();
						js.put("major",name);
						array.add(js);
					}
				}
			  
			for(Major a:arrayList) { //鍖呮嫭鐨?				
				String name = a.getMajor();
				if(name.indexOf(majorKeyword) != -1 && !name.equals(majorKeyword)) {
					JSONObject js = new JSONObject();
					js.put("major",name);
					
					array.add(js);
				}
			}
			
			for(Major a:arrayList) { //鏈夊嚭鍏ョ殑
				String name = a.getMajor();
				if(name.indexOf(majorKeyword) == -1) {
					JSONObject js = new JSONObject();
					js.put("major",name);
					
					array.add(js);
				}
			}
		}
		else return null;
		back.put("majors",array);
		
		return back;
	//鏍规嵁鐩稿叧绋嬪害鎺掑垪鍑哄厛鍚庨『搴忥紝姣斿绀轰緥鏌ヨ鐢靛瓙淇℃伅锛屽厛鈥滅數瀛愪俊鎭�濓紝鍐嶁�滅數瀛愪俊鎭笌鎶�鏈�?	
	}

	
	public String share(int idTS) 
	{
		//閿炲拑绶敐璁圭珐gpa閿濓綇浜?		//閿炲拑绶敐璁圭珐閿炲绶￠敐濂夊幀閿炴﹫绀侀敐杈炬閿炴牭绶╅敓锟?		//閿濊绶敒鎿勭反SharedTables閿濈绶為敐鎰佹儢閿濈》浜ら敐锟?		
		SharedTable sharedTable = SharedTableManager.findWithIdTS(idTS); //閿炴劙娼归敐顏庣藩閿炲偊妗ㄧ挒搴獎閿烇拷閿燂拷
		if(sharedTable.getIdTS() != idTS)
			return "fail";
		User curUser = UserManager.findWithId(sharedTable.getIdUser());
		float gpa = curUser.getGPA();
		if(gpa >= 3) 
		{
			if(SharedTableManager.add(sharedTable.getIdUser(), sharedTable.getIdTS(), sharedTable.getTimeShared(), sharedTable.getSummary(), 0) != -1) 
				return "success";
			else return "fail";
		}
		else return "gpafail";
	}
	//gpa閿濊浜ら敒妤冪反閿濈浜ら敒姒瀙afail 閿濊绶抽敒妤婃焽閿澭嶆〃閿炴粣妞掗敐纰変氦閿炴ail

	public JSONArray getSTList(String major) 
	{
		JSONArray sharedTableArray = new JSONArray();
		if(major.equals("all")) 
		{ //閿濊浜ら敒蹇ョ犯閿炴寤洪敒鎺為檷
			ArrayList<SharedTable> sharedTables = SharedTableManager.sortWithNothing();
			for(int i = 0; i < sharedTables.size(); i++)
			{
				JSONObject sharedTable = new JSONObject();
				User user = UserManager.findWithId(sharedTables.get(i).getIdUser());
				sharedTable.put("name", user.getName());
				sharedTable.put("userId", user.getId());
				sharedTable.put("image", user.getImage());
				sharedTable.put("school", user.getSchool());
				sharedTable.put("major", user.getMajor());
				sharedTable.put("summary", sharedTables.get(i).getSummary());
				sharedTable.put("timeShared", sharedTables.get(i).getTimeShared());
				sharedTable.put("thumbup", Integer.toString(sharedTables.get(i).getThumbup()));
				sharedTable.put("idTS", Integer.toString(sharedTables.get(i).getIdTS()));
				sharedTable.put("idST", Integer.toString(sharedTables.get(i).getId()));
				sharedTableArray.add(sharedTable);
			 }
		 }
		 else 
		 { //閿濇唻楠勯敒妤嬪缓閿炴帪闄?			 
			 ArrayList<SharedTable> sharedTables = SharedTableManager.sortWithMajor(major);
			 if(sharedTables == null)
				 return null;
			 for(int i = 0; i < sharedTables.size(); i++)
			 {
				 JSONObject sharedTable = new JSONObject();
				 User user = UserManager.findWithId(sharedTables.get(i).getIdUser());
				 sharedTable.put("name", user.getName());
				 sharedTable.put("userId", user.getId());
				 sharedTable.put("image", user.getImage());
				 sharedTable.put("school", user.getSchool());
				 sharedTable.put("major", user.getMajor());
				 sharedTable.put("summary", sharedTables.get(i).getSummary());
				 sharedTable.put("timeShared", sharedTables.get(i).getTimeShared());
				 sharedTable.put("thumbup", Integer.toString(sharedTables.get(i).getThumbup()));
				 sharedTable.put("idTS", Integer.toString(sharedTables.get(i).getIdTS()));
				 sharedTable.put("idST", Integer.toString(sharedTables.get(i).getId()));
				 sharedTableArray.add(sharedTable);
			 }
		 }
		 return sharedTableArray;
	}
	/*Userid閿炲函濮滈敐纰夊閿炲浄妗ㄩ敒鎿勭窛閿濅紮娑d,Major閿濆尅绶ｉ敒鍕剁范閿炲函濮滈敒鍕跺尃閿濆壊鎽奸敐顭掔番閿濈绶濋摲銈忕窢閿濓綇娴嗛敐鈽呭original閿濃槄濂栭敐杈攭閿濇拝濂栭敐鎾呯繁閿濐煉绶敐锟?閿濃槄濂朼ll閿濃槄濂栭敐杈攭閿濇拝璁查敐浼欑珐閿炵儑绶遍敐顭掔番閿濓拷
	閿濆嚖闄嶉敐浼欑凡jsonArray*/
	//閿炲拑绶熼敒鎰舵嫹

}