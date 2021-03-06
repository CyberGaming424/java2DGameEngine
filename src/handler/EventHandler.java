package handler;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import backends.AppPage;

public class EventHandler implements Runnable, Serializable{
	
	private static final long serialVersionUID = -2477938767670058066L;
	
	private ArrayList<AppPage> events;
	private ArrayList<AppPage> destroy;
	
	private int eventNum = 0;
	
	private Thread tEventThread;
	private AppPage timedEvent;
	private int timer;
	
	public EventHandler() {
		events = new ArrayList<>();
		destroy = new ArrayList<>();
	}
	
	public void addEvent(AppPage e) {
		events.add(e);
	}
	
	public void addDestroy(AppPage e) {
		if(events.contains(e)) {
			destroy.add(e);
		}
	}
	
	public void addTimedEvent(AppPage e, int millis) {
		timedEvent = e;
		timer = millis;
		tEventThread = new Thread(this);
		tEventThread.start();
	}
	
	public void nextEvent() {
		eventNum++;
	}
	
	public void paintCurrentEvent(Graphics2D g) {
		if(events.size() > eventNum) {
			events.get(eventNum).paint(g);
		}
	}
	
	public void updateCurrentEvent(double delta) {
		if(events.size() > eventNum) {
			events.get(eventNum).update(delta);
		}
		//Destroying destroyables
		for(AppPage a : destroy) {
			events.remove(a);
		}
	}

	@Override
	public void run() {
		try {
			Thread.sleep(timer);
			events.add(0, timedEvent);
			timedEvent = null;
			tEventThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
