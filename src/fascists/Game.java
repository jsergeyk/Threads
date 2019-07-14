package fascists;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author SergeyK
 */
public class Game {
	private static int countChair = 5; //колво стульев
	private Random r = new Random();	

	public static void main(String[] args) throws InterruptedException {
		
		Game game = new Game();
		Fascist.setGame(game);
		List<Fascist> players = new ArrayList<>();		
		for (int i = 0; i < countChair + 1; i++) {			//игроков больше чем стульев на 1
			Fascist fascist = new Fascist(String.valueOf(i));
			players.add(fascist);
			Thread t = new Thread(fascist);
			t.setDaemon(true);
			t.start();	
		}		
		game.play(players);
	}

	private  void play(List<Fascist> players) {
		while (countChair > 0) {
			try {
				Thread.sleep(1000);	//потоки запустятся (игроки приготовятся)
				System.out.println("\nНачинается раунд, играет музыка!");
				synchronized(this) {
					Thread.sleep(r.nextInt(3000) + 3000);
					
					int unluckyPlayer = r.nextInt(countChair +1);
					System.out.println("Стоп музыка, все проснулись");
					players.get(unluckyPlayer).setInGame(false);
					this.notifyAll();
					
					players.remove(unluckyPlayer);	//unluckyPlayer не имя а номер потока в списке						
					countChair--;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		players.get(0).setInGame(false);
		System.out.println("Чемпион по стульям игрок № " + players.get(0).getName());		
	}
	 
	public static int getCountChair() {
		return countChair;
	}
}