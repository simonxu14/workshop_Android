package com.itdog.fragment;


import com.itdog.acticity.R;
import com.itdog.activity.DetailBasicInfoActivity;
import com.itdog.activity.FactoryInfoActivity;
import com.itdog.activity.MachineInfoActivity;
import com.itdog.activity.MainActivity;
import com.itdog.activity.WorkerInfoActivity;
import com.itdog.activity.WorkshopInfoActivity;
import com.itdog.gifview.GifHouse;
import com.itdog.gifview.MyGifView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class BasicInfoFragment extends MyFragment 
	implements View.OnClickListener{
	
	private MyGifView basicInfoTitle = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		mainLayout = inflater.inflate(R.layout.basic_info_layout, null);
		InitLogo();
		SetListener();
		return this.mainLayout;
	}
	
	@Override
	public void onClick(View v) {	
		Intent intent = new Intent(getActivity(), DetailBasicInfoActivity.class);
		
		switch(v.getId()){
		case R.id.worker_info_layout:
			intent = new Intent(getActivity(), WorkerInfoActivity.class);
			break;
		case R.id.maine_info_layout:
			intent = new Intent(getActivity(), MachineInfoActivity.class);
			break;
		case R.id.workshop_info_layout:
			intent = new Intent(getActivity(), WorkshopInfoActivity.class);
			break;
		case R.id.factory_info_layout:
			intent = new Intent(getActivity(), FactoryInfoActivity.class);
			break;
		}		
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);   
		startActivity(intent);		
		getActivity().overridePendingTransition(R.anim.basic_zoom_in, R.anim.basic_zoom_out); 
	}
	
	private void InitLogo()
	{
		basicInfoTitle = (MyGifView)mainLayout.findViewById(R.id.basic_info_title_gif);
		basicInfoTitle.setGifHouse(((MainActivity)getActivity()).getGifHouse());
		basicInfoTitle.setGifTag(GifHouse.COMPANY_TITLE);
	}
	
	private void SetListener(){
		View view = mainLayout.findViewById(R.id.worker_info_layout);
		view.setOnClickListener(BasicInfoFragment.this);		
		view = mainLayout.findViewById(R.id.maine_info_layout);
		view.setOnClickListener(BasicInfoFragment.this);
		view = mainLayout.findViewById(R.id.workshop_info_layout);
		view.setOnClickListener(BasicInfoFragment.this);
		view = mainLayout.findViewById(R.id.factory_info_layout);
		view.setOnClickListener(BasicInfoFragment.this);
	}
}
