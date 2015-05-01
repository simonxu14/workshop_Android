package com.itdog.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itdog.acticity.R;
import com.itdog.constraints.BasicInfo;
import com.itdog.resourse.WorkShopApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public abstract class DetailBasicInfoActivity extends Activity implements
		OnClickListener {
	
	protected List<Map<String, Object>> listInfo = new ArrayList<Map<String, Object>>();
	protected ListView infoListView = null;
	protected BaseAdapter infoListAdapter = null;

	protected abstract List<Map<String,Object>> getInfoFromNetwork();
	protected abstract BaseAdapter getListAdapter();
	protected abstract String getSubTitle();
	protected abstract View mapToView(Map<String, Object> map);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_layout);
		((WorkShopApp) getApplication()).RegisterActivityToApp(this);
		infoListView = (ListView) findViewById(R.id.info_list);
		infoListAdapter = getListAdapter();
		infoListView.setAdapter(infoListAdapter);
		TextView subTv = (TextView) findViewById(R.id.subtitle_txt);
		subTv.setText(getSubTitle());
		//InitListData();
		SetListener();
		new BasicInfoNetwork().execute("");
	}

	private void SetListener() {
		// 后退
		ImageButton iv = (ImageButton) findViewById(R.id.subtitle_icon);
		iv.setOnClickListener(this);
		iv = (ImageButton) findViewById(R.id.subtitle_refresh);
		iv.setOnClickListener(this);
		infoListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				View dialogView = mapToView((listInfo.get(arg2)));
				AlertDialog.Builder builder = new AlertDialog.Builder(DetailBasicInfoActivity.this);
				builder.setView(dialogView);
				builder.setPositiveButton("确定", null);
				builder.create().show();															
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.subtitle_icon:
			Intent intent = new Intent(this, MainActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
			startActivity(intent);
			overridePendingTransition(R.anim.basic_zoom_in,
					R.anim.basic_zoom_out);
			break;
		case R.id.subtitle_refresh:
			new BasicInfoNetwork().execute("");
			break;
		}
	}

	private class BasicInfoNetwork extends
			AsyncTask<String, Integer, List<Map<String, Object>>> {		
		ProgressDialog progressDialog = new ProgressDialog(DetailBasicInfoActivity.this);
		
		public BasicInfoNetwork() {
			progressDialog.setMessage("正在从服务器下载数据...");
			progressDialog.setTitle("提示信息");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setIcon(R.drawable.machine_info);
			View progressbarView = getLayoutInflater().inflate(R.layout.progress_bar, null);
			//progressDialog.setView(progressbarView);
			
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			if(result == null)
			{
				Toast.makeText(DetailBasicInfoActivity.this, 
						getResources().getString(R.string.net_failed), Toast.LENGTH_LONG).show();
				progressDialog.dismiss();
				return ;
			}			
			listInfo.clear();
			listInfo.addAll(result);
			progressDialog.dismiss();
			infoListAdapter.notifyDataSetChanged();			
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			progressDialog.show();
			super.onPreExecute();
		}

		@Override
		protected List<Map<String, Object>> doInBackground(String... params) {					
			return getInfoFromNetwork();
		}
	}
}
