package com.hoy.util;

import com.hoy.entity.LocationScale;
import com.hoy.entity.Passenger;
import com.hoy.entity.Location;

public class PassengerFactory {
	private static float[] p = {0.1f,0.12f,0.14f,0.18f,0.22f,0.24f};//���ʺ�Ϊ1
	private static String[] names = {"XN","XY","XP","WG","CP","GZ","BJ"};
	/**
	 * �����˿�
	 * @param loca
	 * @return
	 */
	public static Passenger generatePassenger(Location loca){
		Passenger pa = new Passenger();
		int index = getIndex();//ȷ��Ŀ�ĵ�
		if(loca.equal(LocationScale.get("XN"))){//������վ
			pa.setDestination(LocationScale.get(names[index]));
		}
		else if(loca.equal(LocationScale.get("BJ"))){//������վ
			pa.setDestination(LocationScale.get(names[p.length - 1 - index]));
		}
		return pa;
	}
	/**
	 * ���߸��ʻ�ó˿�Ŀ�ĵص�����λ��
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
