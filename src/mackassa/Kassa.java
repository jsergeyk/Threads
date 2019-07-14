package mackassa;

import java.util.*;
import java.util.logging.*;

/**
 *
 * @author sergeyk
 */
class Kassa {

	private Random r = new Random();

	//Состояние работы кассы
	private boolean isWork = true;

	/**
	 * Метод обслуживания покупателя
	 * @param customer покупатель
	 */
	public synchronized void service(Customer customer) {
		if (isWork) {
			try {
				System.out.println("Покупатель " + customer.getName() + " начал обслуживаться");
				Thread.sleep(3000);
				System.out.println("Покупатель " + customer.getName() + " обслужен!");

				if (r.nextInt(2) == 0) {
					System.out.println("Касса закрылась!");
					isWork = false;
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(Kassa.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}