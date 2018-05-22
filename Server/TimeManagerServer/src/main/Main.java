package main;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;
import main.model.moudle.*;
import main.model.services.*;

public class Main {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AffairManager.add(13, 1, 5, "离散数学", "离散数学真开心", "14:00", "15:00", "14:45");
		AffairManager.add(14, 1, 5, "离散数学", "离散数学真开心", "15:00", "16:00", "15:45");
		AffairManager.add(15, 1, 5, "离散数学", "离散数学真开心", "16:00", "15:00", "16:45");
		
	}
}
