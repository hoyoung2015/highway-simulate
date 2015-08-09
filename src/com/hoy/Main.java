package com.hoy;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JFrame;

import com.hoy.entity.BjStation;
import com.hoy.entity.Bus;
import com.hoy.entity.Bus.BusType;
import com.hoy.entity.HighWay;
import com.hoy.entity.Location;
import com.hoy.entity.LocationScale;
import com.hoy.entity.Passenger;
import com.hoy.entity.Station;
import com.hoy.entity.XnStation;
/**
 * 
 * @author 胡洋 西安电子科技大学经济与管理学院
 *
 */
public class Main {

	public static void main(String[] args) throws ParseException {
		Calendar timer = Calendar.getInstance();
		timer.set(Calendar.HOUR_OF_DAY, 7);
		timer.set(Calendar.MINUTE, 30);
		timer.set(Calendar.SECOND, 0);
		timer.set(Calendar.MILLISECOND, 0);
		
		Calendar openTime = Calendar.getInstance();
		openTime.set(Calendar.HOUR_OF_DAY, 7);
		openTime.set(Calendar.MINUTE, 30);
		openTime.set(Calendar.SECOND, 0);
		openTime.set(Calendar.MILLISECOND, 0);
		
		Calendar closeTime = Calendar.getInstance();
		closeTime.set(Calendar.HOUR_OF_DAY, 18);
		closeTime.set(Calendar.MINUTE, 0);
		closeTime.set(Calendar.SECOND, 0);
		closeTime.set(Calendar.MILLISECOND, 0);
		
		Station xnStation = XnStation.getInstance("西安汽车站",LocationScale.get("XN"),timer);
		xnStation.setOpenTime(openTime);
		xnStation.setCloseTime(closeTime);
		Station bjStation = BjStation.getInstance("宝鸡汽车站",LocationScale.get("BJ"),timer);
		bjStation.setOpenTime(openTime);
		bjStation.setCloseTime(closeTime);
		//初始化西安车站车队
		Queue<Bus> wQueueXn = new LinkedList<Bus>();//沃尔沃车队
		Queue<Bus> yQueueXn = new LinkedList<Bus>();//依维柯车队
		for(int i=0;i<5;i++){
			Bus bus = new Bus();
			bus.setStartStation(xnStation);
			bus.setEndStation(bjStation);
			bus.setMaxLoad(48);
			bus.setSpeed(1.9f);
			bus.setBusType(BusType.WEW);
			bus.setBusId("W"+(i+1));
			wQueueXn.offer(bus);
		}
		for(int i=0;i<12;i++){
			Bus bus = new Bus();
			bus.setBusType(BusType.YWK);
			bus.setStartStation(xnStation);
			bus.setEndStation(bjStation);
			bus.setMaxLoad(21);
			bus.setSpeed(1.4f);
			bus.setBusId("Y"+(i+1));
			yQueueXn.offer(bus);
		}
		xnStation.setwQueue(wQueueXn);
		xnStation.setyQueue(yQueueXn);
		//初始化宝鸡车站车队
		Queue<Bus> wQueueBj = new LinkedList<Bus>();//沃尔沃车队
		Queue<Bus> yQueueBj = new LinkedList<Bus>();//依维柯车队
		for(int i=0;i<4;i++){
			Bus bus = new Bus();
			bus.setBusType(BusType.WEW);
			bus.setStartStation(bjStation);
			bus.setEndStation(xnStation);
			bus.setMaxLoad(48);
			bus.setSpeed(1.9f);
			bus.setBusId("W"+(i+6));
			wQueueBj.offer(bus);
		}
		for(int i=0;i<15;i++){
			Bus bus = new Bus();
			bus.setBusType(BusType.YWK);
			bus.setStartStation(bjStation);
			bus.setEndStation(xnStation);
			bus.setMaxLoad(21);
			bus.setSpeed(1.4f);
			bus.setBusId("Y"+(i+13));
			yQueueBj.offer(bus);
		}
		bjStation.setwQueue(wQueueBj);
		bjStation.setyQueue(yQueueBj);
		
		HighWay highWay = new HighWay(xnStation,bjStation);
		
		HighWayGUI hwg = new HighWayGUI(xnStation,bjStation,timer,highWay);
		
		while(true){
			try {
				//更新
				xnStation.update();
				bjStation.update();
				highWay.update();
				hwg.update();
				//时间加一分钟
				timer.set(Calendar.MINUTE, timer.get(Calendar.MINUTE)+1);
				Thread.sleep(100);//定时1秒
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
