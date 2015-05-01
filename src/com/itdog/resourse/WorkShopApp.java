package com.itdog.resourse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.itdog.gifview.GifHouse;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

public class WorkShopApp extends Application {
	private List<Activity> globalActivity = new ArrayList<Activity>();	
	
	public void RegisterActivityToApp(Activity activity){
		globalActivity.add(activity);
	}
	
	public void ClearAllActivities(){
		Log.i("TAG","ClearAllActivities");
		for(Activity item: globalActivity)
			if(item.isFinishing() == false)
				item.finish();
	}	
	
	
	
	@Override
	public void onTerminate() {		
		super.onTerminate();
		ClearAllActivities();
	}
}
