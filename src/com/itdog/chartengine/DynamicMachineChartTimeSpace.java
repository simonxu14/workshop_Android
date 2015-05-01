package com.itdog.chartengine;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DynamicMachineChartTimeSpace implements ITimeSpace {
	private static final long HOUR = 3600 * 1000;// 每小时有多少毫秒
	private static final long DAY = HOUR * 24;// 每天有多少毫秒
	private static final int HOURS = 24;
	private static final long MS_PER_MINIUTE = 1000 * 60;
	private static final long MINIUTES_PER_DAY = 24 * 60;

	public static int SPACE1 = 5;
	public static int SPACE2 = 15;
	public static int SPACE3 = 30;
	public static int SPACE4 = 60;
	
	private int type = 0;
	
	private Map<Integer, Date[]> spaceToDate =
			new HashMap<Integer, Date[]>();
		
	public DynamicMachineChartTimeSpace() {
		int index = 0;
		Date[] dateArray = null;
		int length = 0;					
		
		long zero = new Date(2013,12,16,0,0).getTime();
		
		//SPACE1
		length = (int) (MINIUTES_PER_DAY/SPACE1) + 1;
		dateArray = new Date[length];
		for(index = 0; index < length;
				index += 1){
			dateArray[index] = new Date(zero + MS_PER_MINIUTE * index * SPACE1);
		}
		spaceToDate.put(SPACE1, dateArray);
		//SPACE2
		length = (int) (MINIUTES_PER_DAY/SPACE2) + 1;
		dateArray = new Date[length];
		for(index = 0; index < length;
				index += 1){
			dateArray[index] = new Date(zero + MS_PER_MINIUTE * index * SPACE2);
		}
		spaceToDate.put(SPACE2, dateArray);
		//SPACE3
		length = (int) (MINIUTES_PER_DAY/SPACE3) + 1;
		dateArray = new Date[length];
		for(index = 0; index < length;
				index += 1){
			dateArray[index] = new Date(zero + MS_PER_MINIUTE * index * SPACE3);
		}
		spaceToDate.put(SPACE3, dateArray);
		//SPACE4
		length = (int) (MINIUTES_PER_DAY/SPACE4) + 1;
		dateArray = new Date[length];
		for(index = 0; index < length;
				index += 1){
			dateArray[index] = new Date(zero + MS_PER_MINIUTE * index * SPACE4);
		}
		spaceToDate.put(SPACE4, dateArray);
	}
		
	@Override
	public Date[] getDateByTimeSpace() {
		return spaceToDate.get(type);
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}	
	
	public int getType(){
		return this.type;
	}
}

