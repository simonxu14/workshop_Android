package com.shenjinsheng.network;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itdog.acticity.R;
import com.itdog.constraints.BasicInfo;

public class WorkerAnalyze {		
	public static List<Map<String, Object>> anlyze(String str){
		Document doc;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String title = null;
		
		try {
			doc = Jsoup.connect(str).get();
			title = doc.title(); 
			if (!title.equals("My JSP 'WorkerMessage.jsp' starting page")) {
				return null;
			}
			Elements es = doc.getElementsByTag("worker");
			for (Element e : es) {
				HashMap<String, Object> map = new HashMap<String, Object>();											
				map.put(BasicInfo.WORKER_NAME, e.getElementsByTag("name").text());
				map.put(BasicInfo.WORKER_AUTHORITY, e.getElementsByTag("Authority").text());
				map.put(BasicInfo.WORKER_ID, e.getElementsByTag("IDCard").text());
				map.put(BasicInfo.WORKER_TYPE, e.getElementsByTag("profession").text());
				map.put(BasicInfo.WORKER_PHONE, e.getElementsByTag("pNumber").text());				
				map.put(BasicInfo.WORKER_AGE, e.getElementsByTag("age").text());
				map.put(BasicInfo.WORKER_ADDR, e.getElementsByTag("address").text());
				map.put(BasicInfo.WORKER_MAIL, e.getElementsByTag("email").text());
				//将性别转换为图片显示
				String sex = e.getElementsByTag("sex").text();
				if(sex.equals("M"))
					map.put(BasicInfo.WORKER_SEX, R.drawable.male);
				else
					map.put(BasicInfo.WORKER_SEX, R.drawable.female);					
				list.add(map);
			}
		} catch (IOException e) {			
			return null;
		}  		
		return list;
	}
}
