/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.itdog.chartengine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.achartengine.util.MathHelper;

import com.itdog.constraints.BasicInfo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.View;

/**
 * Temperature sensor demo chart.
 */
public class SensorValuesChart extends AbstractDemoChart {
	/*
	 * SPACE1
	 * SPACE2
	 * SPACE3
	 * SPACE4
	 */
	

	
	private List<double[]> values = new ArrayList<double[]>();
	/**
	 * Returns the chart name.
	 * 
	 * @return the chart name
	 */
	public String getName() {
		return "机床-位移变化图";
	}

	/**
	 * Returns the chart description.
	 * 
	 * @return the chart description
	 */
	public String getDesc() {
		return "机床随时间位移变化图";
	}

	/**
	 * Executes the chart demo.
	 * 
	 * @param context
	 *            the context
	 * @return the view
	 */
	public View execute(Context context, ITimeSpace timeSpace) {
		String[] titles = new String[] { "Inside" };

		List<Date[]> x = new ArrayList<Date[]>();
		x.add(timeSpace.getDateByTimeSpace());

		List<double[]> values = getArrayList();
		int[] colors = new int[] { Color.RED };
		PointStyle[] styles = new PointStyle[] { PointStyle.DIAMOND };

		XYMultipleSeriesRenderer renderer = buildRenderer(colors, styles);
		int length = renderer.getSeriesRendererCount();
		for (int i = 0; i < length; i++) {
			((XYSeriesRenderer) renderer.getSeriesRendererAt(i))
					.setFillPoints(true);
		}
		
		Date[] date = x.get(0);
		Log.i("TAG", x.get(0).length+"");
		long end = x.get(0)[date.length-1].getTime();
		long begin = x.get(0)[0].getTime();
		
		setChartSettings(renderer, "时间-机床位移变化图", "时间", "机床位移",
				begin, end, -5, 30,
				Color.DKGRAY, Color.DKGRAY);
		renderer.setXLabels(10);
		renderer.setYLabels(10);
		renderer.setShowGrid(true);		
		
		renderer.setXLabelsAlign(Align.CENTER);
		renderer.setYLabelsAlign(Align.RIGHT);
		/*
		 * Intent intent = ChartFactory.getTimeChartIntent(context,
		 * buildDateDataset(titles, x, values), renderer, "h:mm a");
		 */
		View view = ChartFactory.getTimeChartView(context,
				buildDateDataset(titles, x, values), renderer, "h:mm a");
		return view;
	}

	public List<double[]> getArrayList() {			
		return values;
	}

	public void setDataset(List<Map<String, Object>> data) {
		int nIndex = data.size() - 1;
		double[] dArray = new double[nIndex+1] ;
		int k = 0;
		for (; nIndex > 0; nIndex--) {
			String displacement = (String)(data.get(nIndex).get(BasicInfo.DYNAMIC_MACHINE_DIST));
			try {
				double dDist = Double.parseDouble(displacement);
				dArray[k++] = dDist;
			} catch (NumberFormatException e) {
			}
		}
		values.add(dArray);
	}
}
