package com.itdog.activity;

import java.util.List;

import com.itdog.acticity.R;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class PageListener implements OnPageChangeListener {
	private boolean initialized = false;
	private List<View> tabArray = null;
	private ActionBar actionBar = null;
	private Activity context = null;
	public PageListener(List<View> tab, ActionBar bar,
			Activity context){
		this.tabArray = tab;
		actionBar = bar;
		this.context = context;
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		Log.i("TAG", "onPageScrollStateChanged");		
	}

	//第一个参数为当前页面的下标
	//第二个参数为滑动距离占屏幕宽度的百分比
	//第三个参数为实际滑动的像素。
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		Log.i("TAG", "onPageScrolled");
		actionBar.Translate(arg1, arg0);
		if(initialized == false){
			initialized = true;
			onPageSelected(0);
		}
	}

	@Override
	public void onPageSelected(int arg0) {
		Log.i("TAG", "onPageSelected");
		TextView tv = null;
		for(View item:tabArray)
		{
			item.setBackgroundColor(context.getResources().getColor(R.color.tab_background));
			tv = (TextView) item.findViewById(R.id.tab_text);
			tv.setTextColor(Color.rgb(20, 20, 20));
		}
		tabArray.get(arg0).setBackgroundColor(context.getResources().getColor(R.color.tab_background_selected));
		tv = (TextView) (tabArray.get(arg0).findViewById(R.id.tab_text));
		tv.setTextColor(Color.WHITE);
	}

	

}
