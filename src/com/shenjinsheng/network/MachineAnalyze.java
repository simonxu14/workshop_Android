/*
 * 车间解析类
 * 传入参数：String str,str表示需要解析的url页面
 * 返回值：List<Map<String, String>>
 * 其中Map中包括对应关系如下：
 * machine_ID————————————String
 * machine_type——————————String
 * machine_mNumber———————String
 * machine_sNumber———————String
 * machine_workshop——————String
 * machine_manufacturer——String
 * machine_pDate—————————String
 * machine_workerID——————String
 * 抛出异常MachineException。
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

import android.widget.BaseAdapter;

import com.itdog.acticity.R;
import com.itdog.constraints.BasicInfo;

public class MachineAnalyze {
	public static List<Map<String, Object>> anlyze(String str)  {
		Document doc;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		String title = null;
		try {
			doc = Jsoup.connect(str).get();
			title = doc.title();
			if (!title.equals("My JSP 'MachineMessage.jsp' starting page")) {
		
			}
			
			Elements es = doc.getElementsByTag("machine");
			for (Element e : es) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put(BasicInfo.MACHINE_ID, e.getElementsByTag("machine_ID").text());
				map.put(BasicInfo.MACHINE_TYPE, e.getElementsByTag("machine_type").text());
				map.put(BasicInfo.MACHINE_TYPENUMBER, e.getElementsByTag("machine_mNumber").text());
				map.put(BasicInfo.MACHINE_SEQ, e.getElementsByTag("machine_sNumber").text());
				map.put(BasicInfo.MACHINE_SHOP, e.getElementsByTag("machine_workshop").text());
				map.put(BasicInfo.MACHINE_FACTORY, e.getElementsByTag("machine_manufacturer").text());
				map.put(BasicInfo.MACHINE_BORN, e.getElementsByTag("machine_pDate").text());
				map.put(BasicInfo.MACHINE_LEADER, e.getElementsByTag("machine_workerID").text());
				map.put(BasicInfo.MACHINE_ICON, R.drawable.machine_info);				
				list.add(map);
			}
		} catch (IOException e) {
			return null;
		}

		return list;
	}
}