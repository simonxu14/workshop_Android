/*
 * 车间解析类
 * 传入参数：String str,str表示需要解析的url页面
 * 返回值：List<Map<String, String>>
 * 其中Map中包括对应关系如下：
 * workshop_ID————————————String
 * workshop_type——————————String
 * workshop_bTime—————————String
 * workshop_manufacturer——String
 * workshop_principal—————String
 * 抛出异常WorkerException。
 * 2013.09.29
 * By 沈津生
 */
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

public class WorkshopAnalyze {
	public static List<Map<String, Object>> anlyze(String str) {
		Document doc;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String title = null;
		try {
			doc = Jsoup.connect(str).get();
			title = doc.title();
			if (!title.equals("My JSP 'WorkerMessage.jsp' starting page")) {
				return null;
			}
			
			Elements es = doc.getElementsByTag("workshop");
			for (Element e : es) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(BasicInfo.SHOP_ID, e.getElementsByTag("workshop_ID").text());
				map.put(BasicInfo.SHOP_TYPE, e.getElementsByTag("workshop_type").text());
				map.put(BasicInfo.SHOP_BORN, e.getElementsByTag("workshop_bTime").text());
				map.put(BasicInfo.SHOP_FACTORY, e.getElementsByTag("workshop_manufacturer").text());
				map.put(BasicInfo.SHOP_PRINCIPLE, e.getElementsByTag("workshop_principal").text());
				map.put(BasicInfo.SHOP_ICON, R.drawable.workshop_info);			
				list.add(map);
			}
		} catch (IOException e) {
			return  null;
		}

		return list;
	}
}