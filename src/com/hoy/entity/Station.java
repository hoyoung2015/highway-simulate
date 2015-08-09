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
	protected Location location;// ��վ���ڳ���
	private int busIntervalW;// ����ʱ����
	private int busIntervalY;// ����ʱ����
	protected Queue<Bus> wQueue;// �ֶ��ֿͳ�����
	protected Queue<Bus> yQueue;// ��ά�¿ͳ�����
	private Calendar timer;// ��վ��ʱ��
	private Calendar openTime;// ��վ����Ӫҵ��ʱ���
	private Calendar closeTime;// ��վ���ŵ�ʱ���
	private String name;// ��վ����
	private Queue<Passenger> passengers;// �˿Ͷ���
	private HighWay highWay;//��վ���ӵĸ��ٹ�·
	/**
	 * ���ù��ŵ�ʱ���
	 * @param closeTime
	 */
	public void setCloseTime(Calendar closeTime) {
		this.closeTime = closeTime;
	}
	/**
	 * ���ÿ���Ӫҵ��ʱ���
	 * @param openTime
	 */
	public void setOpenTime(Calendar openTime) {
		this.openTime = openTime;
	}
	/**
	 * ��ȡ��վ���ڵĵص�
	 * @return
	 */
	public Location getLocation() {
		return location;
	}
	/**
	 * ���ó�վ���ӵĸ��߹�·
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
	 * ���캯������Ϊʹ�õ���ģʽ������Ϊprotected
	 * @param name ��վ����
	 * @param location ��վ���ڵص�
	 * @param timer ��վʱ��
	 */
	protected Station(final String name, final Location location, Calendar timer) {
		super();
		this.location = location;
		this.name = name;
		this.timer = timer;
		passengers = new LinkedList<Passenger>();
	}
	/**
	 * ���ó�վ�ֶ��ֳ���
	 * @param wQueue
	 */
	public void setwQueue(Queue<Bus> wQueue) {
		this.wQueue = wQueue;
	}
	/**
	 * ���ó�վ��ά�³���
	 * @param yQueue
	 */
	public void setyQueue(Queue<Bus> yQueue) {
		this.yQueue = yQueue;
	}
	/**
	 * ��ȡ��վ�ȴ��˿͵�����
	 * @return
	 */
	public int getPassengerNumber() {
		return this.passengers.size();
	}
	/**
	 * ��ó�վĿǰ�ֶ��ֵ�����
	 * @return
	 */
	public int getWewNumber() {
		return this.wQueue.size();
	}
	/**
	 * ��ó�վĿǰ��ά�µ�����
	 * @return
	 */
	public int getYwkNumber() {
		return this.yQueue.size();
	}
	/**
	 * ���������복վ�Ķ�����
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
	 * �����������ڵص�
	 * @param location
	 */
	public void setLocation(Location location) {
		this.location = location;
	}
	/**
	 * ����վ�Ƿ���Ӫҵʱ���
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
	 * ���³�վ״̬
	 */
	public void update() {
		if (!isOpen()) {// ��վ����Ӫҵʱ����
			// �˿͵ȴ��������
			passengers.clear();
			return;
		}
		int h = timer.get(Calendar.HOUR_OF_DAY);
		int m = timer.get(Calendar.MINUTE);
		int k = h * 60 + m;

		// ����˿�
		int num = (int) Math.floor(Math.random() * 3);
		System.out.println(name + "��վ����" + num + "���˿�");
		for (int i = 0; i < num; i++) {
			passengers.offer(PassengerFactory.generatePassenger(location));
		}
		// �ж��Ƿ�Ҫ����
		int t1 = 8 * 60 + 30;
		int t2 = 17 * 60 + 30;
		if (k >= t1 && k <= t2) {
			if (busIntervalW++ == 60 || k == t1) {// �ֶ���һСʱ�������
				System.out.println(name + "��վ�ֶ��ַ���ʱ�䵽");
				busIntervalW = 0;
				// ������վ
				Bus bus = wQueue.poll();
				// �˿��ϳ�
				bus.passengerIn(passengers);
				// ������ٹ�·
				this.highWay.addBus(bus);
			}
		}
		int t11 = 8 * 60;
		int t22 = 18 * 60;
		if (k >= t11 && k <= t22) {
			if (busIntervalY++ == 20 || k == t11) {// ��ά��20���Ӽ������
				System.out.println(name + "��վ��ά�·���ʱ�䵽");
				busIntervalY = 0;
				// ������վ
				Bus bus = yQueue.poll();
				// �˿��ϳ�
				bus.passengerIn(passengers);
				// ������ٹ�·
				this.highWay.addBus(bus);
			}
		}
	}
}
