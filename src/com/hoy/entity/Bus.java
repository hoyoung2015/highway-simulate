package com.hoy.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Bus {
	private float speed;//车速
	private int maxLoad;//最大载客量
	private Station startStation;//起点站
	private Station endStation;//终点站
	private BusType busType;//汽车类型
	private Location currentLocation;//汽车当前坐在地点
	private float mileAge;//汽车已经行驶的路程
	private List<Passenger> passengers;//乘坐该汽车的乘客
	private String busId;//汽车编号
	private boolean pauseFlag = false;//汽车是否停下的标志
	private int pauseCount = 0;//汽车停下的时间计数器
	/**
	 * 获取汽车编号
	 * @return
	 */
	public String getBusId() {
		return busId;
	}
	/**
	 * 设置汽车编号
	 * @param busId
	 */
	public void setBusId(String busId) {
		this.busId = busId;
	}
	/**
	 * 获取汽车类型
	 * @return
	 */
	public BusType getBusType() {
		return busType;
	}
	/**
	 * 设置汽车类型
	 * @param busType
	 */
	public void setBusType(BusType busType) {
		this.busType = busType;
	}
	/**
	 * 汽车类型，沃尔沃和依维柯
	 * @author lenovo
	 *
	 */
	public enum BusType{
		YWK,WEW
	}
	/**
	 * 获取始发站
	 * @return
	 */
	public Station getStartStation() {
		return startStation;
	}
	/**
	 * 得到行进的路程
	 * @return
	 */
	public float getMileAge() {
		return mileAge;
	}
	/**
	 * 获取汽车的状态 pause表示停车，running表示正在行驶
	 * @return
	 */
	public String getState(){
		if(pauseFlag){
			return "pause";
		}
		return "running";
	}
	/**
	 * 汽车站的乘客队列进入汽车
	 * @param pg
	 */
	public void passengerIn(Queue<Passenger> pg){
		//清空座位
		passengers.clear();
		Iterator<Passenger> iter = pg.iterator();
		int count = 0;
		//循环到达到该车的最大载客量或乘客输入队列没有乘客了
		while(iter.hasNext() && count++<maxLoad){
			passengers.add(iter.next());
			iter.remove();
		}
	}
	/**
	 * 获取汽车当前的乘客数量
	 * @return
	 */
	public int getPassengerNumber(){
		return this.passengers.size();
	}
	/**
	 * 获取汽车的终点车站
	 * @return
	 */
	public Station getEndStation() {
		return endStation;
	}

	/**
	 * 汽车是否满员
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
	 * 设置起始站
	 * @param startStation
	 */
	public void setStartStation(Station startStation) {
		this.startStation = startStation;
		this.currentLocation = this.startStation.getLocation();
	}
	/**
	 * 设置终点站
	 * @param endStation
	 */
	public void setEndStation(Station endStation) {
		this.endStation = endStation;
	}
	/**
	 * 是否到达终点车站
	 */
	public boolean isArrive(){
		return this.currentLocation.equal(this.endStation.getLocation());
	}
	/**
	 * 获取汽车速度
	 * @return
	 */
	public float getSpeed() {
		return speed;
	}
	/**
	 * 设置汽车速度
	 * @param speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	/**
	 * 设置汽车最大载客量
	 * @return
	 */
	public int getMaxLoad() {
		return maxLoad;
	}
	/**
	 * 设置汽车最大载客量
	 * @param maxLoad
	 */
	public void setMaxLoad(int maxLoad) {
		this.maxLoad = maxLoad;
	}
	/**
	 * 汽车进入重点车站
	 */
	public void intoStation(){
		//清空座位
		this.passengers.clear();
		//清空总里程
		this.mileAge = 0f;
		//将车加入队尾
		this.endStation.addBus(this);
		//变换起点站和终点站
		Station temp = this.startStation;
		this.startStation = this.endStation;
		this.endStation = temp;
	}
	/**
	 * 行驶
	 */
	public void run(){
		//判断是否停车
		if(pauseFlag){//车正停下
			if(pauseCount++>=2){//停车时间到，继续行驶
				pauseCount = 0;
				pauseFlag = false;
			}
		}else{//车正在行驶
			Location local = this.getLocation(this.mileAge);
			//不相同，已经到了另一个地点则停车
			if(!local.equal(this.currentLocation)){
				//停车标志
				pauseFlag = true;
				//到了新的地方
				this.currentLocation = local;
				//乘客下车
				passengerGetOff();
				
			}else{
				//行进
				this.mileAge += this.speed;
			}
		}
	}
	/**
	 * 乘客下车
	 */
	private void passengerGetOff(){
		Iterator<Passenger> iter = passengers.iterator();
		while(iter.hasNext()){  
			Passenger p = iter.next();  
		    if(p.getDestination().equal(currentLocation)){
		    	iter.remove();//乘客到达目的地，乘客下车
		    }
		}
	}
	/**
	 * 获取汽车当前已经到达的地点
	 * @return
	 */
	public Location getCurrentLocation() {
		return currentLocation;
	}
	private Location getLocation(float dist){
		Location[] locals = LocationScale.locals.values().toArray(new Location[7]);
		//西安到宝鸡
		if(this.startStation instanceof XnStation){
			//逆序检查
			for(int i=locals.length-1;i>=0;i--){
				if(dist >= locals[i].getDistance()){
					return locals[i];
				}
			}
		}else{//宝鸡到西安方向
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
