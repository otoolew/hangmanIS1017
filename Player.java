package hangmanIS1017;
/**
 * University of Pittsburgh
 * IS1017 Java Development
 * Fall 2015
 * @author William O'Toole
 */
// Class Set up as instructed
public class Player {
	private String name;
	private int gamesCount;
	private int gamesWon;

	public Player(String n) {
		name = n;
	}
	// Constructor used to implement stored player 
	public Player(String n, int w, int c) {
		name = n;
		gamesWon = w;
		gamesCount = c;
	}

	public void incrementGames(boolean won) {
		if (won) {
			gamesWon++;
		}
		gamesCount++;
	}

	public void setName(String n) {
		name = n;
	}

	public String getName() {
		return name;
	}

	public int getGamesWon() {
		return gamesWon;
	}

	public int getGamesCount() {
		return gamesCount;
	}
	// toString override.
	// Display properties with labels
	@Override
	public String toString() {
		StringBuilder B = new StringBuilder();
		B.append(name);
		B.append("\nGames Won: ").append(gamesWon);
		B.append("\nGames Played: ").append(gamesCount);
		return B.toString();
	}
}
