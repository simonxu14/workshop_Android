package com.itdog.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

	private List<Fragment> fragmentList = null;
	
	public FragmentAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.fragmentList = list;
	}

	@Override
	public Fragment getItem(int arg0) {		
		return fragmentList.get(arg0);
	}

	@Override
	public int getCount() {
		
		return fragmentList.size();
	}	
}
