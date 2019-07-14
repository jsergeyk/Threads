package transporter;

import java.util.concurrent.*;

/**
 * @author SergeyK
 */
public class Transporter implements Runnable {

	private SandMine mine;

	public Transporter(SandMine mine) {
		this.mine = mine;
	}

	@Override
	public void run() {
		while(mine.getSand() > 0) {
			try {				
				mine.getSTransp().acquire();
				System.out.println("Транспортер телегу принял с " + mine.getCart() + " кг ");
				for (int i = 0; i < 5; i++) {
					System.out.println("Транспортер перевозит " + mine.getCart() + " кг " + (i + 1)+ " сек");
					TimeUnit.SECONDS.sleep(1);		
				}
				mine.getSUnloader().release();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}