package com.hoy.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hoy.entity.Bus.BusType;

/**
 * 高速公路
 * @author lenovo
 *
 */
public class HighWay {
	private List<Bus> busList = new ArrayList<Bus>();
	private Station xnStation;
	private Station bjStation;
	/**
	 * 每分钟更新
	 */
	public void update(){
		Iterator<Bus> iter = busList.iterator();
		System.out.println("高速公路车辆："+busList.size());
		while(iter.hasNext()){
			Bus bus = iter.next();
			bus.run();
			if(bus.isArrive()){
				//汽车进站
				bus.intoStation();
				//汽车出高速公路
				iter.remove();
			}
		}
	}
	public int getBusNumber(){
		return busList.size();
	}
	/**
	 * 获得开往宝鸡的汽车的数目
	 * @return
	 */
	public int getBusNumberToBj(BusType busType){
		int count = 0;
		for (Bus bus : busList) {
			if(bus.getEndStation() instanceof BjStation && bus.getBusType()==busType){
				count++;
			}
		}
		return count;
	}
	/**
	 * 获得开往西安的汽车的数目
	 * @return
	 */
	public int getBusNumberToXn(BusType busType){
		int count = 0;
		for (Bus bus : busList) {
			if(bus.getEndStation() instanceof XnStation && bus.getBusType()==busType){
				count++;
			}
		}
		return count;
	}
	public HighWay(Station xnStation, Station bjStation) {
		super();
		this.xnStation = xnStation;
		this.bjStation = bjStation;
		
		this.xnStation.setHighWay(this);
		this.bjStation.setHighWay(this);
	}
	public void addBus(Bus bus){
		busList.add(bus);
	}
	public List<Bus> getBusList(){
		return busList;
	}
}
