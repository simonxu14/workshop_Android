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

import com.itdog.constraints.BasicInfo;

public class MachineCondition {
	public static List<Map<String, Object>> MachineConditionData( String getMCurl)
			throws IOException {
		Document doc;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		doc = Jsoup.connect(getMCurl).ignoreContentType(true).get();
		Elements es = doc.getElementsByTag("MachineCondition");
		
		for (Element e : es) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(BasicInfo.DYNAMIC_MACHINE_ID,
					e.getElementsByTag("MachineCondition_machineID").text());
			map.put(BasicInfo.DYNAMIC_MACHINE_MOVE_TIME,
					e.getElementsByTag("MachineCondition_moveTime").text());
			map.put("BasicInfo.DYNAMIC_MACHINE_STATIC_TIME",
					e.getElementsByTag("MachineCondition_restTime").text());
			map.put(BasicInfo.DYNAMIC_MACHINE_DIST,
					e.getElementsByTag("MachineCondition_displacement").text());
			map.put(BasicInfo.DYNAMIC_MACHINE_TURN_TIMES,
					e.getElementsByTag("MachineCondition_sTime").text());
			String isOpr = e.getElementsByTag("MachineCondition_condition").text();
			if(isOpr.equals("on"))
				map.put(BasicInfo.DYNAMIC_MACHINE_PERSON,"1");			
			else
				map.put(BasicInfo.DYNAMIC_MACHINE_PERSON,"0");						
			list.add(map);
		}
		return list;
	}
}
