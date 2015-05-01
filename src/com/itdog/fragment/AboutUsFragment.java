package com.itdog.fragment;


import com.itdog.acticity.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AboutUsFragment extends MyFragment {		
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		mainLayout = inflater.inflate(R.layout.about_us_layout, null);
		//initLogo();
		return mainLayout;
	}
	

}
