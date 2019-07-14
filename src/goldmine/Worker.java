package goldmine;

import java.util.concurrent.*;

/**
 * @author SergeyK
 */
public class Worker implements Runnable {
	private String name;
	Semaphore s;

	private GoldMine mine;
	private int ownGold = 0; 

	public Worker(String name, GoldMine mine, Semaphore s) {
		this.name = name;
		this.mine = mine;
		this.s = s;
	}

	@Override
	public void run() {
		while(mine.getGold() > 0) {
			ownGold = ownGold + miningGoldPerSec(3);	//������ 3 ���� � ���(� ����� ����� �������� ������ 3 ����)
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ������� �� �����
	 * @param countGold ���������� ���� ������ 1 �������
	 * @return factMining, ���������� ����������� �� �����(����� ���� ������ countGold)
	 */
	private int miningGoldPerSec(int countGold) {
		try {
			s.acquire();
			//System.out.println("Worker � " + name + " ������");
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		int factMining = (countGold > mine.getGold() ? mine.getGold() : countGold);
		mine.setGold(mine.getGold() - factMining);
		//System.out.println("Worker � " + name + " released");
		s.release();
		return factMining;
	}
	
	public String getName() {
		return name;
	}

	public int getOwnGold() {
		return ownGold;
	}
}
