package com.itdog.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itdog.acticity.R;
import com.itdog.activity.MainActivity;
import com.itdog.constraints.BasicInfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class DynamicWorkshopFragment extends MyFragment {

	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainLayout = inflater.inflate(R.layout.dynamic_workshop_layout, null);
		

		return mainLayout;
	}

	

}
