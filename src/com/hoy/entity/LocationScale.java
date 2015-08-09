package com.hoy.entity;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class LocationScale {
	//�ص�����
	private static String[] names = {"XN","XY","XP","WG","CP","GZ","BJ"};
	//�������ľ��룬�൱�ڽ�������Ϊԭ���һά����
	private static float[] distance = {0f,22f,46f,67f,129f,150f,174f};
	public static float MileAge = 174f;
	//����ʹ��LinkedHashMap����֤�������
	public static Map<String, Location> locals = new LinkedHashMap<String, Location>();
	static{
		for(int i=0;i<names.length;i++){
			Location local = new Location();
			local.setName(names[i]);
			local.setDistance(distance[i]);
			locals.put(names[i], local);
		}
	}
	public static Location get(String name){
		return locals.get(name);
	}
}
