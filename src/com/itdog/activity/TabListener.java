package com.itdog.activity;

import com.itdog.acticity.R;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;

public class TabListener implements OnClickListener {

	private ViewPager viewPager = null;
	public TabListener(ViewPager viewPager) {
		this.viewPager = viewPager;
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tab1:
			viewPager.setCurrentItem(0);
			break;
		case R.id.tab2:
			viewPager.setCurrentItem(1);
			break;
		case R.id.tab3:
			viewPager.setCurrentItem(2);
			break;
		case R.id.tab4:
			viewPager.setCurrentItem(3);
			break;
		default:
			break;
		}

	}

}
