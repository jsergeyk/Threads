package mackassa;

import java.util.*;
import java.util.logging.*;

/**
 *
 * @author sergeyk
 */
class Kassa {

	private Random r = new Random();

	//��������� ������ �����
	private boolean isWork = true;

	/**
	 * ����� ������������ ����������
	 * @param customer ����������
	 */
	public synchronized void service(Customer customer) {
		if (isWork) {
			try {
				System.out.println("���������� " + customer.getName() + " ����� �������������");
				Thread.sleep(3000);
				System.out.println("���������� " + customer.getName() + " ��������!");

				if (r.nextInt(2) == 0) {
					System.out.println("����� ���������!");
					isWork = false;
				}
			} catch (InterruptedException ex) {
				Logger.getLogger(Kassa.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
}