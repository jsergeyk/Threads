package mackassa;

/**
 *
 * @author sergeyk
 */
public class Customer implements Runnable {

	private String name;
	private Kassa kassa;

	public Customer(String name, Kassa kassa) {
		this.name = name;
		this.kassa = kassa;
	}

	public String getName() {
		return name;
	}

	@Override
	public void run() {
		kassa.service(this);
	}
}