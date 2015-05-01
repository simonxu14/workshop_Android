package com.itdog.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.itdog.acticity.R;
import com.itdog.constraints.BasicInfo;
import com.shenjinsheng.network.WorkshopAnalyze;

public class WorkshopInfoActivity extends DetailBasicInfoActivity{
	protected BaseAdapter getListAdapter() {		

		SimpleAdapter infoListAdapter = new SimpleAdapter(this, listInfo,
				R.layout.info_listview_layout, new String[] {
						BasicInfo.SHOP_ICON, BasicInfo.SHOP_FACTORY,
						BasicInfo.SHOP_TYPE}, new int[] { R.id.photo,
						R.id.name, R.id.description });
		return infoListAdapter;
	}

	
	@Override
	protected List<Map<String, Object>> getInfoFromNetwork() {	
		return WorkshopAnalyze.anlyze("http://183.220.109.72:8080/workshop/MessageFindWorkshopServlet");
	}


	@Override
	protected View mapToView(Map<String, Object> map) {
		LayoutInflater inflater = this.getLayoutInflater();
		View v = inflater.inflate(R.layout.dlg_info, null);		
		TextView tv = (TextView) v.findViewById(R.id.dlg_worker_name);
		tv.setText((CharSequence) map.get(BasicInfo.SHOP_FACTORY));
		tv = (TextView) v.findViewById(R.id.dlg_worker_info);
		String info = "����:" + 	(CharSequence) map.get(BasicInfo.SHOP_ID) +"\n"		
					+ "����:" + 	(CharSequence) map.get(BasicInfo.SHOP_TYPE)+"\n"
					+ "��������:" + 	(CharSequence) map.get(BasicInfo.SHOP_BORN)+"\n"
					+ "����:" + 	(CharSequence) map.get(BasicInfo.SHOP_FACTORY)+"\n"
					+ "������:" + 	(CharSequence) map.get(BasicInfo.SHOP_PRINCIPLE);
		tv.setText(info);
		
		ImageView iv = (ImageView) v.findViewById(R.id.dlg_worker_photo);
		iv.setImageResource((Integer) map.get(BasicInfo.SHOP_ICON));
		return v;
	}


	@Override
	protected String getSubTitle() {
		// TODO Auto-generated method stub
		return "车间";
	}



}
