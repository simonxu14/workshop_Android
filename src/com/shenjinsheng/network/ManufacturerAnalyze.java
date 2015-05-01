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


public class ManufacturerAnalyze {
	
	public static List<Map<String, Object>> anlyze(String str) {
		Document doc;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String title = null;
		try {
			doc = Jsoup.connect(str).get();
			title = doc.title(); 
			if (!title.equals("My JSP 'ManufacturerMessage.jsp' starting page")) {
				return null;
			}
			Elements es = doc.getElementsByTag("manufacturer");
			for (Element e : es) {
				HashMap<String, Object> map = new HashMap<String, Object>();				
				map.put(BasicInfo.FACTORY_ID, e.getElementsByTag("ID").text());
				map.put(BasicInfo.FACTORY_NAME, e.getElementsByTag("name").text());
				map.put(BasicInfo.FACTORY_PRINCIPLE, e.getElementsByTag("principal").text());
				map.put(BasicInfo.FACTORY_TEL, e.getElementsByTag("phone").text());
				map.put(BasicInfo.FACTORY_ADDR, e.getElementsByTag("address").text());
				map.put(BasicInfo.FACTORY_ICON, R.drawable.factory_info);				
				list.add(map);
			}
		} catch (IOException e) {
			return null;
		}  
		
		return list;
	}
}