package com.itdog.gifview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.itdog.acticity.R;
import com.itdog.activity.MainActivity;
import com.itdog.activity.SplashActivity;

public class MyGifView extends View {
	private long movieStart;
	private Movie movie;
	private GifHouse gifHouse = null;
	private String gifTag = null;

	// 此处必须重写该构造方法
	public MyGifView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		// 以文件流（InputStream）读取进gif图片资源
		movie = Movie.decodeStream(getResources().openRawResource(
				R.drawable.machine_left));
		gifTag = GifHouse.MACHINE_LEFT;
		/*
		 * ViewGroup.LayoutParams layoutParams= getLayoutParams();
		 * layoutParams.width = movie.width(); layoutParams.height =
		 * movie.height(); setLayoutParams(layoutParams);
		 */
	}

	public void setGifHouse(GifHouse gifHouse) {
		this.gifHouse = gifHouse;
	}

	public void setGifTag(String tag) {
		Movie newMovie = null;
		if (gifTag.equals(tag))
			return;
		if ((newMovie = gifHouse.getMovieByType(tag)) != null) {
			movie = newMovie;
			gifTag = tag;
			movieStart = 0;
			invalidate();
		}
	}


	@Override
	protected void onDraw(Canvas canvas) {
		long curTime = android.os.SystemClock.uptimeMillis();
		// 第一次播放
		if (movieStart == 0) {
			movieStart = curTime;
		}
		if (movie != null) {
			int duraction = movie.duration();
			if (duraction == 0) {
				duraction = 1000;
			}
			int relTime = (int) ((curTime - movieStart) % duraction);
			movie.setTime(relTime);
			movie.draw(canvas, 0, 0);
			// 强制重绘
			new Handler().postDelayed(new Runnable() {  
	            public void run() {  
	            	invalidate();
	            }  	  
	        }, 3000);
		}
		super.onDraw(canvas);
	}
}