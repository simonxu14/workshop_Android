package com.shenjinsheng.network;

/*
 * ��ȡ��ʷ����
 * ���ļ���������������getActiveMachineHistory��getDynamicWorkshopHistory��formatNumber
 * �ֱ��ʾ�����������������䡢ID��ʽ��
 * �����ݿ��е������ǡ�1�������ǡ�00001��ʱ��Ӧ������getActiveMachineHistory��getDynamicWorkshopHistory
 * �е�formatNumber���ã�
 * 
 * 
 * By.�����
 * 2013��12��10�� 01:50:11
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
	 * �鿴������ʷ ע�⣺�������timeRequest��ʽ��
	 * 
	 * @param machineID
	 *            �鿴�Ļ���ID��String���ͣ����ȵ���5
	 * @param timeRequest
	 *            �鿴��ʱ�䣬ע�⣬�ո���%20�滻��
	 * @param getAMHurl
	 *            URL��ַ����������Get��Ϣ
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
	 * �鿴������ʷ(���м��) ע�⣺�������timeRequest��ʽ��
	 * 
	 * @param machineID
	 *            �鿴�Ļ���ID��String���ͣ����ȵ���5
	 * @param timeRequest
	 *            �鿴��ʱ�䣬ע�⣬�ո���%20�滻��
	 * @param time_space
	 *            ���ʱ��
	 * @param getAMHurl
	 *            URL��ַ����������Get��Ϣ
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
	 * �鿴������ʷ
	 * 
	 * @param workshopID
	 *            �����ţ�String���ͣ�����5λ
	 * @param getDWHurl
	 *            URL��Ϣ��������Get��Ϣ
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
	 * ���䡢������Ÿ�ʽ����������5λ�ı��תΪ5λ�ı�š�
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
