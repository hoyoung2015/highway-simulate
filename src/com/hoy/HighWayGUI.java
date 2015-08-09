package com.hoy;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Stroke;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.hoy.entity.BjStation;
import com.hoy.entity.Bus;
import com.hoy.entity.HighWay;
import com.hoy.entity.Location;
import com.hoy.entity.LocationScale;
import com.hoy.entity.Station;
import com.hoy.entity.Bus.BusType;
import com.hoy.entity.XnStation;

public class HighWayGUI {
	private Station xnStation;
	private Station bjStation;
	private HighWay highWay;
	private Calendar timer;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private DecimalFormat df = new DecimalFormat("#.0km");
	private JFrame jFrame;
	private JPanel timerPanel;
	private JLabel timeDisp;
	private JPanel xnPanel;
	private JLabel passNumXn;
	private JLabel wewNumXn;
	private JLabel ywkNumXn;
	
	private JPanel highWayPanel;
	private JPanel bjPanel;
	private JLabel passNumBj;
	private JLabel wewNumBj;
	private JLabel ywkNumBj;
	
	private JLabel toBjLabel;
	private JLabel toXnLabel;
	
	private int pwidth = 264;
	public HighWayGUI(Station xnStation,Station bjStation,Calendar timer,HighWay highWay) {
		super();
		this.xnStation = xnStation;
		this.bjStation = bjStation;
		this.timer = timer;
		this.highWay = highWay;
		
		jFrame = new JFrame("���ٹ�·ģ��");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLayout(null);
		jFrame.setSize(564, 600);
		
		timerPanel = new JPanel();
		timerPanel.setBounds(0, 0, 564, 30);
		JLabel labtime = new JLabel("��ǰʱ��:");
		labtime.setFont(new Font("����", Font.PLAIN, 20));
		timerPanel.add(labtime);
		timeDisp = new JLabel("");
		timeDisp.setFont(new Font("����", Font.PLAIN, 20));
		timerPanel.add(timeDisp);
		
		jFrame.add(timerPanel);
		
		xnPanel = new JPanel();
		xnPanel.setBounds(10, 50, pwidth, 130);
		xnPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"��������վ"));
		xnPanel.setLayout(null);
		jFrame.add(xnPanel);
		
		bjPanel = new JPanel();
		bjPanel.setBounds(10+pwidth, 50, pwidth, 130);
		bjPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"��������վ"));
		bjPanel.setLayout(null);
		jFrame.add(bjPanel);
		
		
		JLabel label1 = new JLabel("  �ȴ��˿���:");
		label1.setFont(new Font("����", Font.PLAIN, 16));
		label1.setBounds(10, 30, 150, 30);
		
		passNumXn = new JLabel("0");
		passNumXn.setFont(new Font("����", Font.PLAIN, 16));
		passNumXn.setBounds(160, 30, 150, 30);
		
		
		wewNumXn = new JLabel("0");
		wewNumXn.setFont(new Font("����", Font.PLAIN, 16));
		wewNumXn.setBounds(160, 60, 150, 30);
		
		JLabel label2 = new JLabel("�ֶ��ֿͳ���:");
		label2.setFont(new Font("����", Font.PLAIN, 16));
		label2.setBounds(10, 60, 150, 30);
		
		JLabel label3 = new JLabel("��ά�¿ͳ���:");
		label3.setFont(new Font("����", Font.PLAIN, 16));
		label3.setBounds(10, 90, 150, 30);
		ywkNumXn = new JLabel("0");
		ywkNumXn.setFont(new Font("����", Font.PLAIN, 16));
		ywkNumXn.setBounds(160, 90, 150, 30);
		xnPanel.add(label1);
		xnPanel.add(passNumXn);
		xnPanel.add(label2);
		xnPanel.add(wewNumXn);
		xnPanel.add(label3);
		xnPanel.add(ywkNumXn);
		
		JLabel label4 = new JLabel("  �ȴ��˿���:");
		label4.setFont(new Font("����", Font.PLAIN, 16));
		label4.setBounds(10, 30, 150, 30);
		passNumBj = new JLabel("0");
		passNumBj.setFont(new Font("����", Font.PLAIN, 16));
		passNumBj.setBounds(160, 30, 150, 30);
		bjPanel.add(passNumBj);
		
		JLabel label5 = new JLabel("�ֶ��ֿͳ���:");
		label5.setFont(new Font("����", Font.PLAIN, 16));
		label5.setBounds(10, 60, 150, 30);
		wewNumBj = new JLabel("0");
		wewNumBj.setFont(new Font("����", Font.PLAIN, 16));
		wewNumBj.setBounds(160, 60, 150, 30);
		bjPanel.add(wewNumBj);
		
		JLabel label6 = new JLabel("��ά�¿ͳ���:");
		label6.setFont(new Font("����", Font.PLAIN, 16));
		label6.setBounds(10, 90, 150, 30);
		ywkNumBj = new JLabel("0");
		ywkNumBj.setFont(new Font("����", Font.PLAIN, 16));
		ywkNumBj.setBounds(160, 90, 150, 30);
		bjPanel.add(ywkNumBj);
		
		bjPanel.add(label4);
		bjPanel.add(label5);
		bjPanel.add(label6);
		
		highWayPanel = new JPanel();
		highWayPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"���ٹ�·"));
		highWayPanel.setBounds(10, 185, 528, 350);
		
		busOnTheWayToBjModel = new DefaultListModel<String>();
		busOnTheWayToXnModel = new DefaultListModel<String>();
		
		JPanel p1 = new JPanel();
		p1.setBounds(5, 40, highWayPanel.getWidth()-10, 200);
		p1.setLayout(new GridLayout(1, 2,5,5));
		busOnTheWayToBj = new JList<String>(busOnTheWayToBjModel);
		JScrollPane sp1 = new JScrollPane(busOnTheWayToBj);
		p1.add(sp1);
		
		busOnTheWayToXn = new JList<String>(busOnTheWayToXnModel);
		JScrollPane sp2 = new JScrollPane(busOnTheWayToXn);
		p1.add(sp2);
		
		JPanel p2 = new JPanel();
		p2.setBounds(5, 20, highWayPanel.getWidth()-10, 20);
		p2.setLayout(new GridLayout(1, 2,5,5));
		toBjLabel = new JLabel("��������������");
		p2.add(toBjLabel);
		toXnLabel = new JLabel("��������������");
		p2.add(toXnLabel);
		highWayPanel.add(p2);
		highWayPanel.setLayout(null);
		highWayPanel.add(p1);
		
		
		JPanel roadPanel = new JPanel();
//		roadPanel.setBackground(new Color(0, 0, 0));
		roadPanel.setBounds(5, 250, highWayPanel.getWidth()-10, 90);
		roadPanel.setLayout(new BorderLayout());
		roadPanel.add(new JLabel("����վ"),BorderLayout.WEST);
		roadPanel.add(new JLabel("����վ"),BorderLayout.EAST);
		highWayPanel.add(roadPanel);
		
		road = new Road();
		road.setBackground(Color.white);
		roadPanel.add(road,BorderLayout.CENTER);
		
		jFrame.add(highWayPanel);
		
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
	}
	private Road road;
	private JList<String> busOnTheWayToBj;
	private DefaultListModel<String> busOnTheWayToBjModel;
	private JList<String> busOnTheWayToXn;
	private DefaultListModel<String> busOnTheWayToXnModel;
	public void update(){
		this.timeDisp.setText(sdf.format(timer.getTime()));
		
		this.passNumXn.setText(this.xnStation.getPassengerNumber()+"");
		this.wewNumXn.setText(this.xnStation.getWewNumber()+"");
		this.ywkNumXn.setText(this.xnStation.getYwkNumber()+"");
		
		this.passNumBj.setText(this.bjStation.getPassengerNumber()+"");
		this.wewNumBj.setText(this.bjStation.getWewNumber()+"");
		this.ywkNumBj.setText(this.bjStation.getYwkNumber()+"");
		List<Bus> busList = this.highWay.getBusList();
		toBjLabel.setText("��������������(�ֶ���:"+this.highWay.getBusNumberToBj(BusType.WEW)+"����ά��:"+this.highWay.getBusNumberToBj(BusType.YWK)+")");
		toXnLabel.setText("��������������(�ֶ���:"+this.highWay.getBusNumberToXn(BusType.WEW)+"����ά��:"+this.highWay.getBusNumberToXn(BusType.YWK)+")");
		busOnTheWayToXnModel.removeAllElements();
		busOnTheWayToBjModel.removeAllElements();
		for (Bus bus : busList) {
			Station station = bus.getEndStation();
			StringBuffer sb = new StringBuffer("");
			sb.append(bus.getBusId()+"-");
			sb.append(df.format(bus.getMileAge()));
			sb.append("-"+bus.getState());
			sb.append("-"+bus.getPassengerNumber()+"p");
			sb.append("-"+bus.getCurrentLocation().getName());
			if(station instanceof BjStation){//����������
				busOnTheWayToBjModel.addElement(sb.toString());
			}else{//����������
				busOnTheWayToXnModel.addElement(sb.toString());
			}
		}
		road.repaint();
	}
	class Road extends Canvas{
		Collection<Location> locals = LocationScale.locals.values();
		float max = LocationScale.MileAge;
		@Override
		public void paint(Graphics g) {
			Image img = this.createImage(this.getWidth(), this.getHeight());
			Graphics g2 = img.getGraphics();
			//����·
			g2.setColor(new Color(100, 100, 100));
			g2.fillRect(0, 0, this.getWidth(), 2);
			g2.fillRect(0, 60, this.getWidth(), 2);
			
			g2.setColor(new Color(200, 200, 200));
			g2.drawLine(0, 30, this.getWidth(), 30);
			//���Ƶر�
			g2.setColor(new Color(0, 0, 0));
			for (Location location : locals) {
				if(location.getName().equals("XN")) continue;
				int pos = (int) (location.getDistance()/max*this.getWidth());
				g2.drawString(location.getName()+"("+location.getDistance()+")", pos, 74);
			}
			//����˵��
			g2.setColor(Color.RED);
			g2.fillRect(0, 78, 18, 10);
			g2.setColor(new Color(90, 70, 200));
			g2.fillRect(80, 79, 14, 8);
			g2.setColor(Color.BLUE);
			g2.drawString("�ֶ���", 26, 88);
			g2.drawString("��ά��", 100, 88);
			//���Ƴ�
			List<Bus> busList = highWay.getBusList();
			for (Bus bus : busList) {
				int x=0,y=0,width=0,height=0;
				Station station = bus.getEndStation();
				//�жϳ���
				if(bus.getBusType()==BusType.WEW){
					g2.setColor(Color.RED);
					width = 18;
					height = 10;
				}else{
					g2.setColor(Color.BLUE);
					width = 14;
					height = 8;
				}
				if(station instanceof BjStation){//����������
					x = (int) (bus.getMileAge()/max*this.getWidth());
					y = 6;
					if(bus.getBusType()==BusType.WEW){
						y = 18;
					}
				}else{//����������
					x = (int) ((max-bus.getMileAge())/max*this.getWidth());
					y = 48;
					if(bus.getBusType()==BusType.WEW){
						y = 34;
					}
				}
				g2.fillRect(x, y, width, height);
			}
			g.drawImage(img, 0, 0, null);
		}
		
	}
}
