package com.hoy.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import com.hoy.entity.Bus.BusType;
import com.hoy.util.PassengerFactory;

public class Station {
	protected Location location;// 车站所在城市
	private int busIntervalW;// 发车时间间隔
	private int busIntervalY;// 发车时间间隔
	protected Queue<Bus> wQueue;// 沃尔沃客车队列
	protected Queue<Bus> yQueue;// 依维柯客车队列
	private Calendar timer;// 车站的时钟
	private Calendar openTime;// 车站开门营业的时间点
	private Calendar closeTime;// 车站关门的时间点
	private String name;// 车站名称
	private Queue<Passenger> passengers;// 乘客队列
	private HighWay highWay;//车站连接的高速公路
	/**
	 * 设置关门的时间点
	 * @param closeTime
	 */
	public void setCloseTime(Calendar closeTime) {
		this.closeTime = closeTime;
	}
	/**
	 * 设置开门营业的时间点
	 * @param openTime
	 */
	public void setOpenTime(Calendar openTime) {
		this.openTime = openTime;
	}
	/**
	 * 获取车站坐在的地点
	 * @return
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * 设置车站连接的告诉公路
	 * @param highWay
	 */
	public void setHighWay(HighWay highWay) {
		this.highWay = highWay;
	}

	public Station(String name) {
		super();
		this.name = name;
	}
	/**
	 * 构造函数，因为使用单例模式，所以为protected
	 * @param name 车站名称
	 * @param location 车站坐在地点
	 * @param timer 车站时钟
	 */
	protected Station(final String name, final Location location, Calendar timer) {
		super();
		this.location = location;
		this.name = name;
		this.timer = timer;
		passengers = new LinkedList<Passenger>();
	}
	/**
	 * 设置车站沃尔沃车队
	 * @param wQueue
	 */
	public void setwQueue(Queue<Bus> wQueue) {
		this.wQueue = wQueue;
	}
	/**
	 * 设置车站依维柯车队
	 * @param yQueue
	 */
	public void setyQueue(Queue<Bus> yQueue) {
		this.yQueue = yQueue;
	}
	/**
	 * 获取车站等待乘客的数量
	 * @return
	 */
	public int getPassengerNumber() {
		return this.passengers.size();
	}
	/**
	 * 获得车站目前沃尔沃的数量
	 * @return
	 */
	public int getWewNumber() {
		return this.wQueue.size();
	}
	/**
	 * 获得车站目前依维柯的数量
	 * @return
	 */
	public int getYwkNumber() {
		return this.yQueue.size();
	}
	/**
	 * 将汽车加入车站的队列中
	 * @param bus
	 */
	public void addBus(Bus bus) {
		if (bus.getBusType() == BusType.WEW) {
			this.wQueue.offer(bus);
		}
		if (bus.getBusType() == BusType.YWK) {
			this.yQueue.offer(bus);
		}
	}
	/**
	 * 设置汽车坐在地点
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * 汽车站是否在营业时间点
	 * @return
	 */
	public boolean isOpen() {
		int h = timer.get(Calendar.HOUR_OF_DAY);
		int m = timer.get(Calendar.MINUTE);

		int ho = openTime.get(Calendar.HOUR_OF_DAY);
		int mo = openTime.get(Calendar.MINUTE);

		int hc = closeTime.get(Calendar.HOUR_OF_DAY);
		int mc = closeTime.get(Calendar.MINUTE);

		int k = h * 60 + m;
		return k >= ho * 60 + mo && k <= hc * 60 + mc;
	}
	/**
	 * 更新车站状态
	 */
	public void update() {
		if (!isOpen()) {// 车站不在营业时间内
			// 乘客等待队列清空
			passengers.clear();
			return;
		}
		int h = timer.get(Calendar.HOUR_OF_DAY);
		int m = timer.get(Calendar.MINUTE);
		int k = h * 60 + m;

		// 输入乘客
		int num = (int) Math.floor(Math.random() * 3);
		System.out.println(name + "车站产生" + num + "名乘客");
		for (int i = 0; i < num; i++) {
			passengers.offer(PassengerFactory.generatePassenger(location));
		}
		// 判断是否要发车
		int t1 = 8 * 60 + 30;
		int t2 = 17 * 60 + 30;
		if (k >= t1 && k <= t2) {
			if (busIntervalW++ == 60 || k == t1) {// 沃尔沃一小时间隔发车
				System.out.println(name + "车站沃尔沃发车时间到");
				busIntervalW = 0;
				// 汽车出站
				Bus bus = wQueue.poll();
				// 乘客上车
				bus.passengerIn(passengers);
				// 进入高速公路
				this.highWay.addBus(bus);
			}
		}
		int t11 = 8 * 60;
		int t22 = 18 * 60;
		if (k >= t11 && k <= t22) {
			if (busIntervalY++ == 20 || k == t11) {// 依维柯20分钟间隔发车
				System.out.println(name + "车站依维柯发车时间到");
				busIntervalY = 0;
				// 汽车出站
				Bus bus = yQueue.poll();
				// 乘客上车
				bus.passengerIn(passengers);
				// 进入高速公路
				this.highWay.addBus(bus);
			}
		}
	}
}
