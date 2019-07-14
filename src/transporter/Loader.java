package transporter;

import java.util.concurrent.*;

/**
 * @author SergeyK
 */
public class Loader implements Runnable {
	private SandMine mine;
	
	/**
	 * @param mine
	 */
	public Loader(SandMine mine) {
		this.mine = mine;
	}

	@Override
	public void run() {
		while(mine.getSand() > 0) {
			try {
				mine.getSLoader().acquire();
				System.out.println("������� ���� ������ ");
				int factMining = 0;
				for (int i = 0; i < 3 && mine.getSand() > 0; i++) {
					TimeUnit.SECONDS.sleep(1);
					//��������� 2 �� � ���(� ����� ����� �������� ������ 2 ��)
					factMining = Math.min(mine.getSand(), 2);
					mine.setSand(mine.getSand() - factMining);
					mine.setCart(mine.getCart() + factMining);
					System.out.println("������� �������� " + factMining + " ��, � ������ " + mine.getCart() + " ��");
				}
				System.out.println("---����� � ����� �������� " + mine.getSand() + " ��---");
				mine.getSTransp().release();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
