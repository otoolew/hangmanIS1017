package hangmanIS1017;

import java.io.FileNotFoundException;
/**
 * University of Pittsburgh
 * IS1017 Java Development
 * Fall 2015
 * @author William O'Toole
 */
public class HangmanGameTest {
	
	public static void main(String[] args) throws FileNotFoundException {
		
		GameManager game = new GameManager("hello wednesday festival electronics");
		
		boolean playing = false;
		
		do {
			
			game.setupANewGame();
			
			playing = game.playGame();
			
		} while (playing);
		
	}

}
