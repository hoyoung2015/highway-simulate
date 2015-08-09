package com.hoy.entity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.hoy.entity.Bus.BusType;

/**
 * ���ٹ�·
 * @author lenovo
 *
 */
public class HighWay {
	private List<Bus> busList = new ArrayList<Bus>();
	private Station xnStation;
	private Station bjStation;
	/**
	 * ÿ���Ӹ���
	 */
	public void update(){
		Iterator<Bus> iter = busList.iterator();
		System.out.println("���ٹ�·������"+busList.size());
		while(iter.hasNext()){
			Bus bus = iter.next();
			bus.run();
			if(bus.isArrive()){
				//������վ
				bus.intoStation();
				//���������ٹ�·
				iter.remove();
			}
		}
	}
	public int getBusNumber(){
		return busList.size();
	}
	/**
	 * ��ÿ�����������������Ŀ
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
	 * ��ÿ�����������������Ŀ
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
