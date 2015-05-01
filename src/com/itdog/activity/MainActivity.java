package com.itdog.activity;

import java.util.ArrayList;
import java.util.List;

import com.itdog.acticity.R;
import com.itdog.acticity.R.layout;
import com.itdog.acticity.R.menu;
import com.itdog.adapter.FragmentAdapter;
import com.itdog.fragment.AboutUsFragment;
import com.itdog.fragment.BasicInfoFragment;
import com.itdog.fragment.DynamicMachineFragment;
import com.itdog.fragment.DynamicWorkshopFragment;
import com.itdog.gifview.GifHouse;
import com.itdog.gifview.MyGifView;
import com.itdog.resourse.WorkShopApp;

import android.os.Build;
import android.os.Bundle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity {

	private ViewPager viewPager = null;
	private List<View> tabArray = null;
	private ActionBar actionBar = null;
	private MyGifView companyLogo = null;

	private GifHouse gifHouse = null;

	private void InitGifHouse() {
		gifHouse = new GifHouse();
		gifHouse.addtoHouse(this, GifHouse.MACHINE_LEFT,
				R.drawable.machine_left);
		gifHouse.addtoHouse(this, GifHouse.MACHINE_RIGHT,
				R.drawable.machine_right);
		gifHouse.addtoHouse(this, GifHouse.MACHINE_PERSON,
				R.drawable.machine_person);
		gifHouse.addtoHouse(this, GifHouse.MACHINE_STILL, R.drawable.machine);
		gifHouse.addtoHouse(this, GifHouse.COMPANY_LOGO,
				R.drawable.company_logo);
		gifHouse.addtoHouse(this, GifHouse.COMPANY_TITLE, R.drawable.wanming);
	}
	
	public GifHouse getGifHouse(){
		return gifHouse;
	}
	

	protected void initLogo() {
		companyLogo = (MyGifView) findViewById(R.id.company_logo);
		companyLogo.setGifHouse(gifHouse);
		companyLogo.setGifTag(GifHouse.COMPANY_LOGO);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		InitGifHouse();
		((WorkShopApp) getApplication()).RegisterActivityToApp(this);
		initLogo();
		SetActionBar();
		SetFragment();
		SetTabMenu();
		SetListener();
	}

	// ������Fragment��
	private void SetFragment() {
		this.viewPager = (ViewPager) findViewById(R.id.viewpager);
		List<Fragment> fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new BasicInfoFragment());
		fragmentList.add(new DynamicMachineFragment());
		fragmentList.add(new DynamicWorkshopFragment());
		fragmentList.add(new AboutUsFragment());

		FragmentPagerAdapter adapter = new FragmentAdapter(
				getSupportFragmentManager(), fragmentList);
		this.viewPager.setAdapter(adapter);
		this.viewPager.setCurrentItem(0);
	}

	// ������Tab����
	private void SetTabMenu() {
		View view = null;
		tabArray = new ArrayList<View>();
		view = this.findViewById(R.id.tab1);
		ImageView iv = (ImageView) view.findViewById(R.id.tab_icon);
		iv.setImageResource(R.drawable.tab_info);
		TextView tv = (TextView) view.findViewById(R.id.tab_text);
		tv.setText(getResources().getText(R.string.tab_text1));
		tabArray.add(view);
		view = this.findViewById(R.id.tab2);
		iv = (ImageView) view.findViewById(R.id.tab_icon);
		iv.setImageResource(R.drawable.tab_machine);
		tv = (TextView) view.findViewById(R.id.tab_text);
		tv.setText(getResources().getText(R.string.tab_text2));
		tabArray.add(view);
		view = this.findViewById(R.id.tab3);
		iv = (ImageView) view.findViewById(R.id.tab_icon);
		iv.setImageResource(R.drawable.tab_factory);
		tv = (TextView) view.findViewById(R.id.tab_text);
		tv.setText(getResources().getText(R.string.tab_text3));
		tabArray.add(view);
		view = this.findViewById(R.id.tab4);
		iv = (ImageView) view.findViewById(R.id.tab_icon);
		iv.setImageResource(R.drawable.tab_about_us);
		tv = (TextView) view.findViewById(R.id.tab_text);
		tv.setText(getResources().getText(R.string.tab_text4));
		tabArray.add(view);
	}

	// ��������
	private void SetActionBar() {
		ImageView iv = (ImageView) findViewById(R.id.actionbar);
		actionBar = new ActionBar(iv, R.drawable.tab_bar, this);
	}

	// ����������
	private void SetListener() {
		OnClickListener tabListener = new TabListener(viewPager);
		for (View item : tabArray)
			item.setOnClickListener(tabListener);
		// ����������ViewPage��������
		viewPager.setOnPageChangeListener(new PageListener(tabArray, actionBar,
				MainActivity.this));
		viewPager.setCurrentItem(0);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onBackPressed() {
		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
		builder.setTitle("����");
		builder.setMessage("������������������?");
		builder.setPositiveButton("��", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				((WorkShopApp) getApplication()).ClearAllActivities();
				Log.i("TAG", "onBackPressed");
				System.exit(0);

			}
		});
		builder.setNegativeButton("��", null);
		builder.create().show();

	}

}
