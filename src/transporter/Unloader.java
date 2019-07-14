package transporter;

import java.util.concurrent.*;

/**
 * @author SergeyK
 */
public class Unloader implements Runnable {
	private SandMine mine;
	
	public Unloader(SandMine mine) {
		this.mine = mine;
	}

	@Override
	public void run() {
		while(mine.getSand() > 0 || mine.getCart() > 0) {
			try {
				mine.getSUnloader().acquire();
				System.out.println("Разгрузчик телегу принял c " + mine.getCart() + " кг");
				for (int i = 0; i < 2 && mine.getCart() > 0; i++) {
					TimeUnit.SECONDS.sleep(1);
					int weight = mine.getCart();
					mine.setCart(mine.getCart() - Math.min(weight, 3));
					System.out.println("Разгрузчик разгрузил " + Math.min(weight, 3) + " кг, в телеге осталось " + mine.getCart() + " кг");				
				}
				mine.getSLoader().release();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
}
