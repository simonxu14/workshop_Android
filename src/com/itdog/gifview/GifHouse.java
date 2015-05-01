package com.itdog.gifview;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Movie;

public class GifHouse {
	private Map<String, Movie> resHouse = 
			new HashMap<String, Movie>();
	

	public static String MACHINE_LEFT = "MACHINE_LEFT";
	public static String MACHINE_RIGHT = "MACHINE_RIGHT";
	public static String MACHINE_PERSON = "MACHINE_PERSON";
	public static String MACHINE_STILL = "MACHINE_STILL";
	public static String COMPANY_LOGO = "COMPANY_LOGO";
	public static String COMPANY_TITLE = "COMPANY_TITLE";

	public void addtoHouse(Context context, String type, int resId) {
		Movie movie = null;
		movie = Movie.decodeStream(context.getResources()
				.openRawResource(resId));
		resHouse.put(type, movie);
	}

	public Movie getMovieByType(String type) {
		return resHouse.get(type);
	}
}
