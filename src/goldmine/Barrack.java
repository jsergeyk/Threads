package goldmine;

import java.util.*;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/**
 * @author SergeyK
 */
public class Barrack {
	
	private static GoldMine gold = new GoldMine();
	private static List<Worker> workers = new ArrayList<>();
	private static Semaphore s;
	private static Semaphore s2;	//для синхронизации workers (создания и логирования)
	
	public static void main(String[] args) {
		
		Barrack bar = new Barrack();
		s = new Semaphore(1);
		s2 = new Semaphore(1);
		workers.add(new Worker("1", gold, s));
		workers.add(new Worker("2", gold, s));
		workers.add(new Worker("3", gold, s));
		workers.add(new Worker("4", gold, s));
		workers.add(new Worker("5", gold, s));
		
		for (Worker worker : workers) {	//изначально 5 рабочих
			new Thread(worker).start();
		}
		
		new Thread(() -> bar.logging()).start();	//вот и лямбда
		bar.createWorkers();
	}

	/**
	 * Логировать 1 в сек
	 */
	private void logging() {
		do {
			try {
				TimeUnit.SECONDS.sleep(1);
				s2.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
			for (Worker worker : workers) {
				System.out.println("Worker №" + worker.getName() + " уже владеет " + worker.getOwnGold() + " gold.");
			}
			s2.release();
			System.out.println("В шахте осталось: " + gold.getGold());
		} while (gold.getGold() > 0);
		synchronized (this) {
			this.notify();	//можно раньше остановить создание рабочих
		}
	}

	//барак создают рабочего раз в 10 сек
	private synchronized void createWorkers() {
		int i = 6;
		while(gold.getGold() > 0) {
			try {
				TimeUnit.SECONDS.sleep(10);
				s2.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Worker worker = new Worker(String.valueOf(i++), gold, s);
			workers.add(worker);
			s2.release();
			new Thread(worker).start();
		}		
	}
}