package com.itdog.fragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itdog.acticity.R;
import com.itdog.activity.DetailBasicInfoActivity;
import com.itdog.activity.DynamicMachineChartActivity;
import com.itdog.activity.MainActivity;
import com.itdog.constraints.BasicInfo;
import com.itdog.gifview.GifHouse;
import com.itdog.gifview.MyGifView;
import com.shenjinsheng.network.MachineCondition;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class DynamicMachineFragment extends MyFragment {
	private List<Map<String, Object>> dynamicInfo = new ArrayList<Map<String, Object>>();

	private Context context = null;
	private GridView dynamicMachineGridview = null;
	private BaseAdapter gridViewAdapter = null;

	private Button btnRefresh = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainLayout = inflater.inflate(R.layout.dynamic_machine_layout, null);
		context = getActivity();
		dynamicMachineGridview = (GridView) mainLayout
				.findViewById(R.id.dynamic_machine_list_gridview);
		gridViewAdapter = new DynamicGridViewAdapter();
		dynamicMachineGridview.setAdapter(gridViewAdapter);
		btnRefresh = (Button) (mainLayout
				.findViewById(R.id.refresh_dynamic_machine));
		btnRefresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new DynamicNetwork().execute("");
			}
		});

		dynamicMachineGridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {				
				Intent intent = new Intent(context, DynamicMachineChartActivity.class);
				Map<String,Object> item = dynamicInfo.get((int) arg3);
				String strId = (String) (item.get(BasicInfo.DYNAMIC_MACHINE_ID));				
				intent.putExtra("ID", strId);
				
				context.startActivity(intent);
			}
		});
				
		return mainLayout;
	}

	class DynamicNetwork extends
			AsyncTask<String, Integer, List<Map<String, Object>>> {
		ProgressDialog progressDialog = new ProgressDialog(getActivity());

		public DynamicNetwork() {
			progressDialog.setMessage("��������������������...");
			progressDialog.setTitle("��������");
			progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			progressDialog.setIcon(R.drawable.machine_info);
		}

		@Override
		protected void onPostExecute(List<Map<String, Object>> result) {
			super.onPostExecute(result);
			if (result == null) {
				Toast.makeText(getActivity(),
						getResources().getString(R.string.net_failed),
						Toast.LENGTH_LONG).show();
				progressDialog.dismiss();
				return;
			}
			if (progressDialog.isShowing())
				progressDialog.dismiss();
			dynamicInfo.clear();
			dynamicInfo.addAll(result);
			gridViewAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			progressDialog.show();
		}

		@Override
		protected List<Map<String, Object>> doInBackground(String... arg0) {
			List<Map<String, Object>> list = null;
			try {
				list = MachineCondition
						.MachineConditionData("http://183.220.109.72:8080/workshop/MessageFindMachineConditionServlet");
			} catch (IOException e) {
				e.printStackTrace();
			}
			return list;
		}
	}

	class DynamicGridViewAdapter extends BaseAdapter {
		private LayoutInflater inflater = null;

		public DynamicGridViewAdapter() {
			inflater = ((MainActivity) context).getLayoutInflater();
		}

		@Override
		public int getCount() {
			return dynamicInfo.size();
		}

		@Override
		public Object getItem(int arg0) {
			return dynamicInfo.get(arg0);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		private class ItemHolder {
			TextView textView;
			MyGifView machineIcon;
		};

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Map<String, Object> item = null;
			ItemHolder holder = new ItemHolder();
			// ��������������
			item = (Map<String, Object>) getItem(position);
			if (convertView == null) {
				// inflate view
				convertView = inflater.inflate(
						R.layout.machine_gridview_layout, null);
				// ����GridView��TextView����
				holder.machineIcon = (MyGifView) convertView
						.findViewById(R.id.ItemImage);
				holder.machineIcon.setGifHouse(((MainActivity) getActivity())
						.getGifHouse());
				holder.textView = (TextView) convertView
						.findViewById(R.id.ItemText);
				convertView.setTag(holder);

			} else {
				holder = (ItemHolder) convertView.getTag();
			}

			// ����GridView������
			double ddist = 0; // ����
			String strPerson = (String) item
					.get(BasicInfo.DYNAMIC_MACHINE_PERSON);
			if (strPerson.equals("1")) {
				// holder.machineIcon.setGifImage(R.drawable.machine_person);
				holder.machineIcon.setGifTag(GifHouse.MACHINE_PERSON);
			} else {
				String str_dist = (String) item
						.get(BasicInfo.DYNAMIC_MACHINE_DIST);
				try {
					ddist = Double.parseDouble(str_dist);
					if (ddist < 0)
						holder.machineIcon.setGifTag(GifHouse.MACHINE_LEFT);
					else if (ddist > 0)
						holder.machineIcon.setGifTag(GifHouse.MACHINE_RIGHT);
					else
						holder.machineIcon.setGifTag(GifHouse.MACHINE_STILL);
				} catch (NumberFormatException e) {
					ddist = 0;
					holder.machineIcon.setGifTag(GifHouse.MACHINE_STILL);
					Toast.makeText(context, "������������", Toast.LENGTH_SHORT)
							.show();
				}
			}

			// ������������������������������������
			String str_text = "����:"
					+ (String) (item.get(BasicInfo.DYNAMIC_MACHINE_ID));
			str_text += "\n����:" + ddist;
			holder.textView.setText(str_text);
			return convertView;
		}
	}

}
