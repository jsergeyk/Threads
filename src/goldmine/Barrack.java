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
	private static Semaphore s2;	//��� ������������� workers (�������� � �����������)
	
	public static void main(String[] args) {
		
		Barrack bar = new Barrack();
		s = new Semaphore(1);
		s2 = new Semaphore(1);
		workers.add(new Worker("1", gold, s));
		workers.add(new Worker("2", gold, s));
		workers.add(new Worker("3", gold, s));
		workers.add(new Worker("4", gold, s));
		workers.add(new Worker("5", gold, s));
		
		for (Worker worker : workers) {	//���������� 5 �������
			new Thread(worker).start();
		}
		
		new Thread(() -> bar.logging()).start();	//��� � ������
		bar.createWorkers();
	}

	/**
	 * ���������� 1 � ���
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
				System.out.println("Worker �" + worker.getName() + " ��� ������� " + worker.getOwnGold() + " gold.");
			}
			s2.release();
			System.out.println("� ����� ��������: " + gold.getGold());
		} while (gold.getGold() > 0);
		synchronized (this) {
			this.notify();	//����� ������ ���������� �������� �������
		}
	}

	//����� ������� �������� ��� � 10 ���
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