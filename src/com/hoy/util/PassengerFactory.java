package com.hoy.util;

import com.hoy.entity.LocationScale;
import com.hoy.entity.Passenger;
import com.hoy.entity.Location;

public class PassengerFactory {
	private static float[] p = {0.1f,0.12f,0.14f,0.18f,0.22f,0.24f};//概率和为1
	private static String[] names = {"XN","XY","XP","WG","CP","GZ","BJ"};
	/**
	 * 产生乘客
	 * @param loca
	 * @return
	 */
	public static Passenger generatePassenger(Location loca){
		Passenger pa = new Passenger();
		int index = getIndex();//确定目的地
		if(loca.equal(LocationScale.get("XN"))){//西安车站
			pa.setDestination(LocationScale.get(names[index]));
		}
		else if(loca.equal(LocationScale.get("BJ"))){//宝鸡车站
			pa.setDestination(LocationScale.get(names[p.length - 1 - index]));
		}
		return pa;
	}
	/**
	 * 更具概率获得乘客目的地的索引位置
	 * @return
	 */
	private static int getIndex(){
		float random = (float) Math.random();
		float sum = 0f;
		int i = 0;
		for(;i<p.length;i++){
			sum += p[i];
			if(random < sum){
				break;
			}
		}
		return i;
	}
}
