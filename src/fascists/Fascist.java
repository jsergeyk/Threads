package fascists;

/**
 * @author SergeyK
 */
public class Fascist implements Runnable {

	private String name;
	private boolean inGame = true;
	private static Game game;
	
	public Fascist(String name) {
		this.name = name;
	}


	@Override
	public void run() {

		while(inGame) {
			synchronized (game) {
				try {					
					game.wait();					
					if(inGame) {
						System.out.println("����� " + name + " ���������");						
					} else {
						System.out.println("---������ " + name + " �� ��������� ����� � �� �����---");
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void setGame(Game g) {
		game = g;
	}	

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}	

	public String getName() {
		return name;
	}
}