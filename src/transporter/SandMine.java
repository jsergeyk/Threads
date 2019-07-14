package transporter;

import java.util.concurrent.Semaphore;

/**
 * @author SergeyK
 */
public class SandMine {
	private int sand = 100;			//100 кг песка
	private int cart;				//кг в телеге с песком
	private static Semaphore sLoader;
	private static Semaphore sTransp;
	private static Semaphore sUnloader;	

	public static void main(String[] args) {
		
		sLoader = new Semaphore(1);
		sTransp = new Semaphore(0);
		sUnloader = new Semaphore(0);
		SandMine mine = new SandMine();
		new Thread(new Loader(mine)).start();
		new Thread(new Transporter(mine)).start();
		new Thread(new Unloader(mine)).start();		
	}

	/**
	 * 
	 * @return cart колво кг песка в телеге
	 */
	public int getCart() {
		return cart;
	}

	public void setCart(int cart) {
		this.cart = cart;
	}
	
	public Semaphore getSLoader() {
		return sLoader;
	}

	public Semaphore getSTransp() {
		return sTransp;
	}

	public Semaphore getSUnloader() {
		return sUnloader;
	}
	
	public int getSand() {
		return sand;
	}
	
	public int setSand(int coal) {
		return this.sand = coal;
	}
}