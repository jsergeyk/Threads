package mackassa;

/**
 *
 * @author sergeyk
 */
public class MacDonalds {

	public MacDonalds() {
	}

	public static void main(String[] args) {
		Kassa kassa = new Kassa();
		new Thread(new Customer("1", kassa)).start();
		new Thread(new Customer("2", kassa)).start();
		new Thread(new Customer("3", kassa)).start();
		new Thread(new Customer("4", kassa)).start();
		new Thread(new Customer("5", kassa)).start();
	}
}