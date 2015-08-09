package com.hoy.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Bus {
	private float speed;//����
	private int maxLoad;//����ؿ���
	private Station startStation;//���վ
	private Station endStation;//�յ�վ
	private BusType busType;//��������
	private Location currentLocation;//������ǰ���ڵص�
	private float mileAge;//�����Ѿ���ʻ��·��
	private List<Passenger> passengers;//�����������ĳ˿�
	private String busId;//�������
	private boolean pauseFlag = false;//�����Ƿ�ͣ�µı�־
	private int pauseCount = 0;//����ͣ�µ�ʱ�������
	/**
	 * ��ȡ�������
	 * @return
	 */
	public String getBusId() {
		return busId;
	}
	/**
	 * �����������
	 * @param busId
	 */
	public void setBusId(String busId) {
		this.busId = busId;
	}
	/**
	 * ��ȡ��������
	 * @return
	 */
	public BusType getBusType() {
		return busType;
	}
	/**
	 * ������������
	 * @param busType
	 */
	public void setBusType(BusType busType) {
		this.busType = busType;
	}
	/**
	 * �������ͣ��ֶ��ֺ���ά��
	 * @author lenovo
	 *
	 */
	public enum BusType{
		YWK,WEW
	}
	/**
	 * ��ȡʼ��վ
	 * @return
	 */
	public Station getStartStation() {
		return startStation;
	}
	/**
	 * �õ��н���·��
	 * @return
	 */
	public float getMileAge() {
		return mileAge;
	}
	/**
	 * ��ȡ������״̬ pause��ʾͣ����running��ʾ������ʻ
	 * @return
	 */
	public String getState(){
		if(pauseFlag){
			return "pause";
		}
		return "running";
	}
	/**
	 * ����վ�ĳ˿Ͷ��н�������
	 * @param pg
	 */
	public void passengerIn(Queue<Passenger> pg){
		//�����λ
		passengers.clear();
		Iterator<Passenger> iter = pg.iterator();
		int count = 0;
		//ѭ�����ﵽ�ó�������ؿ�����˿��������û�г˿���
		while(iter.hasNext() && count++<maxLoad){
			passengers.add(iter.next());
			iter.remove();
		}
	}
	/**
	 * ��ȡ������ǰ�ĳ˿�����
	 * @return
	 */
	public int getPassengerNumber(){
		return this.passengers.size();
	}
	/**
	 * ��ȡ�������յ㳵վ
	 * @return
	 */
	public Station getEndStation() {
		return endStation;
	}

	/**
	 * �����Ƿ���Ա
	 * 
	 * @return
	 */
	public boolean isFull() {
		return passengers.size() >= maxLoad;
	}
	public Bus() {
		passengers = new ArrayList<Passenger>();
	}
	/**
	 * ������ʼվ
	 * @param startStation
	 */
	public void setStartStation(Station startStation) {
		this.startStation = startStation;
		this.currentLocation = this.startStation.getLocation();
	}
	/**
	 * �����յ�վ
	 * @param endStation
	 */
	public void setEndStation(Station endStation) {
		this.endStation = endStation;
	}
	/**
	 * �Ƿ񵽴��յ㳵վ
	 */
	public boolean isArrive(){
		return this.currentLocation.equal(this.endStation.getLocation());
	}
	/**
	 * ��ȡ�����ٶ�
	 * @return
	 */
	public float getSpeed() {
		return speed;
	}
	/**
	 * ���������ٶ�
	 * @param speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	/**
	 * ������������ؿ���
	 * @return
	 */
	public int getMaxLoad() {
		return maxLoad;
	}
	/**
	 * ������������ؿ���
	 * @param maxLoad
	 */
	public void setMaxLoad(int maxLoad) {
		this.maxLoad = maxLoad;
	}
	/**
	 * ���������ص㳵վ
	 */
	public void intoStation(){
		//�����λ
		this.passengers.clear();
		//��������
		this.mileAge = 0f;
		//���������β
		this.endStation.addBus(this);
		//�任���վ���յ�վ
		Station temp = this.startStation;
		this.startStation = this.endStation;
		this.endStation = temp;
	}
	/**
	 * ��ʻ
	 */
	public void run(){
		//�ж��Ƿ�ͣ��
		if(pauseFlag){//����ͣ��
			if(pauseCount++>=2){//ͣ��ʱ�䵽��������ʻ
				pauseCount = 0;
				pauseFlag = false;
			}
		}else{//��������ʻ
			Location local = this.getLocation(this.mileAge);
			//����ͬ���Ѿ�������һ���ص���ͣ��
			if(!local.equal(this.currentLocation)){
				//ͣ����־
				pauseFlag = true;
				//�����µĵط�
				this.currentLocation = local;
				//�˿��³�
				passengerGetOff();
				
			}else{
				//�н�
				this.mileAge += this.speed;
			}
		}
	}
	/**
	 * �˿��³�
	 */
	private void passengerGetOff(){
		Iterator<Passenger> iter = passengers.iterator();
		while(iter.hasNext()){  
			Passenger p = iter.next();  
		    if(p.getDestination().equal(currentLocation)){
		    	iter.remove();//�˿͵���Ŀ�ĵأ��˿��³�
		    }
		}
	}
	/**
	 * ��ȡ������ǰ�Ѿ�����ĵص�
	 * @return
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}
	private Location getLocation(float dist){
		Location[] locals = LocationScale.locals.values().toArray(new Location[7]);
		//����������
		if(this.startStation instanceof XnStation){
			//������
			for(int i=locals.length-1;i>=0;i--){
				if(dist >= locals[i].getDistance()){
					return locals[i];
				}
			}
		}else{//��������������
			float m = LocationScale.MileAge-dist;
			for (Location local : locals) {
				if(m <= local.getDistance()){
					return local;
				}
			}
		}
		return null;
	}
}
