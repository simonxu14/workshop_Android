package com.itdog.activity;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.itdog.acticity.R;
import com.itdog.chartengine.DynamicMachineChartTimeSpace;
import com.itdog.chartengine.IDemoChart;
import com.itdog.chartengine.ITimeSpace;
import com.itdog.chartengine.SensorValuesChart;
import com.shenjinsheng.network.HistoryData;
import com.shenjinsheng.network.MachineCondition;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class DynamicMachineChartActivity extends Activity {
	private ImageButton btnRefresh = null;
	ImageButton iv = null;
	TextView tvTitle = null;
	TextView tvMachineId = null;
	String strMachineId = "00001";
	int nDefaultTimeSpace = 60;
	String nDefaultTime = "2013-12-15%2023:59:59.0";
	LinearLayout chartLayout = null;
	private Spinner timeSpaceSpinner = null;
	private DatePicker datepicker = null;
	private ArrayAdapter<String> timeSpaceAdapter;
	private static final String[] timeSpaceTags = { "60����", "30����", "15����",
			"5����" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dynamic_machine_chart_layout);
		tvTitle = (TextView) findViewById(R.id.subtitle_txt);
		btnRefresh = (ImageButton) (findViewById(R.id.subtitle_refresh));
		timeSpaceSpinner = (Spinner) findViewById(R.id.timespace);
		chartLayout = (LinearLayout) findViewById(R.id.chart_view_layout);
		datepicker = (DatePicker) this.findViewById(R.id.date);
		datepicker.updateDate(2013, 12, 15);
		iv = (ImageButton) findViewById(R.id.subtitle_icon);
		SetListener();
		strMachineId = getIntent().getStringExtra("ID");
		tvTitle.setText("����" + strMachineId + "������������");
		SetSpinner();
	}

	private String formatTimeString(DatePicker picker) {
		return picker.getYear() + "-" + (picker.getMonth() + 1) + "-"
				+ picker.getDayOfMonth() + "%2023:59:59.0";
	}

	private String getCurTimeString() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(calendar.YEAR) + "-"
				+ (calendar.get(calendar.MONTH) + 1) + "-"
				+ calendar.get(calendar.DAY_OF_MONTH) + "%2023:59:59.0";
	}

	private void SetSpinner() {
		timeSpaceAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, timeSpaceTags);
		timeSpaceAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		timeSpaceSpinner.setAdapter(timeSpaceAdapter);
		timeSpaceSpinner.setVisibility(View.VISIBLE);
		timeSpaceSpinner
				.setOnItemSelectedListener(new SpinnerSelectedListener());
	}

	// ����������������
	class SpinnerSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			switch ((int) arg3) {
			case 0:
				nDefaultTimeSpace = 60;
				break;
			case 1:
				nDefaultTimeSpace = 30;
				break;
			case 2:
				nDefaultTimeSpace = 15;
				break;
			case 3:
				nDefaultTimeSpace = 5;
				break;
			default:
				break;
			}
			new DynamicNetwork().execute("");
		}

		public void onNothingSelected(AdapterView<?> arg0) {

		}
	}

	private void SetListener() {
		// ����
		btnRefresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				nDefaultTime = formatTimeString(datepicker);
				System.out.println(nDefaultTime);
				Toast.makeText(DynamicMachineChartActivity.this, nDefaultTime,
						Toast.LENGTH_SHORT).show();
				new DynamicNetwork().execute("");

			}
		});
		iv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(DynamicMachineChartActivity.this,
						MainActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
				overridePendingTransition(R.anim.basic_zoom_in,
						R.anim.basic_zoom_out);
			}
		});
	}

	class DynamicNetwork extends
			AsyncTask<String, Integer, List<Map<String, Object>>> {
		ProgressDialog progressDialog = new ProgressDialog(
				DynamicMachineChartActivity.this);

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
				Toast.makeText(DynamicMachineChartActivity.this,
						getResources().getString(R.string.net_failed),
						Toast.LENGTH_LONG).show();
				progressDialog.dismiss();
				return;
			}
			if (progressDialog.isShowing())
				progressDialog.dismiss();			
			if (result.size() != 1 + 24 * 60 / nDefaultTimeSpace) {
				Toast.makeText(DynamicMachineChartActivity.this,
						"������������,��������������!", Toast.LENGTH_LONG).show();
				return;

			}
			ITimeSpace timeSpace = new DynamicMachineChartTimeSpace();
			timeSpace.setType(nDefaultTimeSpace);
			SensorValuesChart chart = new SensorValuesChart();
			chart.setDataset(result);
			View view = chart.execute(DynamicMachineChartActivity.this,
					timeSpace);
			chartLayout.removeAllViewsInLayout();
			chartLayout.addView(view);
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
				list = HistoryData
						.getActiveMachineHistory(strMachineId, nDefaultTime,
								nDefaultTimeSpace + "",
								"http://183.220.109.72:8080/workshop/GetActiveMachineHistoryDataServlet");
				System.out.println(list.size());
			} catch (IOException e) {
				e.printStackTrace();
			}
			return list;
		}
	}

}
