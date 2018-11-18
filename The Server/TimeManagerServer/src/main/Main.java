package main;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.tomcat.util.codec.binary.Base64;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import main.model.db.*;
import main.model.moudle.*;
import main.model.services.*;

public class Main {

	@SuppressWarnings("null")
	public static void main(String[] args) {
		ArrayList<SharedTable> sharedTables = SharedTableManager.sortWithNothing();
		System.out.println("success");
		for(int i = 0; i < sharedTables.size(); i++)
		{
			System.out.println(sharedTables.get(i).getThumbup());
		}
	}
}
