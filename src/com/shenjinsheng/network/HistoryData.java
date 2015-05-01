package com.shenjinsheng.network;

/*
 * 获取历史数据
 * 此文件包含三个函数：getActiveMachineHistory，getDynamicWorkshopHistory，formatNumber
 * 分别表示解析机床、解析车间、ID格式化
 * 当数据库中的数据是“1”而不是“00001”时，应当禁用getActiveMachineHistory和getDynamicWorkshopHistory
 * 中的formatNumber调用！
 * 
 * 
 * By.沈津生
 * 2013年12月10日 01:50:11
 */

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itdog.constraints.BasicInfo;

public class HistoryData {
	/**
	 * 查看机床历史 注意：出啊如的timeRequest格式！
	 * 
	 * @param machineID
	 *            查看的机床ID，String类型，长度低于5
	 * @param timeRequest
	 *            查看的时间，注意，空格用%20替换！
	 * @param getAMHurl
	 *            URL地址，，不包含Get信息
	 *            http://workshop.duapp.com/GetActiveMachineHistoryDataServlet
	 * @return List<Map<String, String>>
	 * @throws IOException
	 */
	public static List<Map<String, Object>> getActiveMachineHistory(
			String machineID, String timeRequest, String getAMHurl)
			throws IOException {
		// Log.e("function", "1");
		return getActiveMachineHistoryData(machineID, timeRequest, " ",
				getAMHurl);

	}

	/**
	 * 查看机床历史(带有间隔) 注意：出啊如的timeRequest格式！
	 * 
	 * @param machineID
	 *            查看的机床ID，String类型，长度低于5
	 * @param timeRequest
	 *            查看的时间，注意，空格用%20替换！
	 * @param time_space
	 *            间隔时间
	 * @param getAMHurl
	 *            URL地址，，不包含Get信息
	 *            http://workshop.duapp.com/GetActiveMachineHistoryDataServlet
	 * @return List<Map<String, String>>
	 * @throws IOException
	 */
	public static List<Map<String, Object>> getActiveMachineHistory(
			String machineID, String timeRequest, String time_space,
			String getAMHurl) throws IOException {
		// Log.e("function", "2");
		return getActiveMachineHistoryData(machineID, timeRequest, time_space,
				getAMHurl);
	}

	private static List<Map<String, Object>> getActiveMachineHistoryData(
			String machineID, String timeRequest, String time_space,
			String getAMHurl) throws IOException {
		machineID = formatNumber(machineID, "00000");
		Document doc;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// Log.e("test", getAMHurl + "?machineID=" + machineID + "&timeRequest="
		// + timeRequest);
		if (time_space.equals(" ")) {
			doc = Jsoup
					.connect(
							getAMHurl + "?machineID=" + machineID
									+ "&timeRequest=" + timeRequest)
					.ignoreContentType(true).get();
		} else {
			doc = Jsoup
					.connect(
							getAMHurl + "?machineID=" + machineID
									+ "&timeRequest=" + timeRequest
									+ "&time_space=" + time_space)
					.ignoreContentType(true).get();
		}
		// Log.e("test", "hahaha");
		Elements es = doc.getElementsByTag("active_machine_data");
		Map<String, Object> map = null;
		for (Element e : es) {
			map = new HashMap<String, Object>();
			map.put(BasicInfo.DYNAMIC_MACHINE_ID,
					e.getElementsByTag("machine_ID").text());
			map.put(BasicInfo.DYNAMIC_MACHINE_MOVE_TIME,
					e.getElementsByTag("moveTime").text());
			map.put(BasicInfo.DYNAMIC_MACHINE_STATIC_TIME,
					e.getElementsByTag("restTime").text());
			map.put(BasicInfo.DYNAMIC_MACHINE_DIST,
					e.getElementsByTag("displacement").text());
			map.put(BasicInfo.DYNAMIC_MACHINE_TURN_TIMES,
					e.getElementsByTag("sTime").text());
			list.add(map);
		}
		list.add(map);
		return list;
	}

	/**
	 * 查看车间历史
	 * 
	 * @param workshopID
	 *            车间编号，String类型，低于5位
	 * @param getDWHurl
	 *            URL信息，不包含Get信息
	 * @return List<Map<String, String>>
	 * @throws IOException
	 */
	public static List<Map<String, String>> getDynamicWorkshopHistory(
			String workshopID, String getDWHurl) throws IOException {
		workshopID = formatNumber(workshopID, "00000");
		Document doc;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		doc = Jsoup.connect(getDWHurl + "?workshopID=" + workshopID)
				.ignoreContentType(true).get();
		Elements es = doc.getElementsByTag("dynamic_workshop_data");

		for (Element e : es) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("workshop_ID", e.getElementsByTag("workshop_ID").text());
			map.put("temperature", e.getElementsByTag("temperature").text());
			map.put("humidity", e.getElementsByTag("humidity").text());
			map.put("noise", e.getElementsByTag("noise").text());
			list.add(map);
		}
		return list;

	}

	/**
	 * 车间、机床编号格式化，将低于5位的编号转为5位的编号。
	 * 
	 * @param str
	 * @param formatAs
	 * @return
	 */
	public static String formatNumber(String str, String formatAs) {
		DecimalFormat df = new DecimalFormat(formatAs);
		String str2 = df.format(Integer.parseInt(str));
		// Log.e("str2", str2);
		return str2;
	}

}
