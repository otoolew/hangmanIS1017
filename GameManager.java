package hangmanIS1017;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * University of Pittsburgh
 * IS1017 Java Development
 * Fall 2015
 * @author William O'Toole
 */

public class GameManager {
	// Added Feature to Save Player Progress on a .txt file
	private ArrayList<Player> playerAList = new ArrayList<>(); // How I handle the retrieval of the players from the 
	private boolean stillPlaying;
	private int wordQueue;
	private Player player;
	private Hangman hangman;
	private String words;
	private String currentWord;
	private int playerIndex = 0;
	private Scanner scan = new Scanner(new FileInputStream("hangmanplayers1.txt"));
	
	// Addded the FileNotFoundException to handle File Errors Since I am saving Player Stats in players.txt
	public GameManager(String words) throws FileNotFoundException {
		this.words = words.toUpperCase();
		getListofPlayers();
		createPlayer();
	}

	public void createPlayer() {
		boolean found = false;
		int gamesWon;
		int gamesCount;
		// Take input for name
		String currentPlayer = JOptionPane.showInputDialog("Please enter your name!");
		// Making sure it is not null
		if(currentPlayer==null){
			currentPlayer = "Mystery Man";
		}
		// Traverse the ArrayList to see if there is arecord of previous play
		for (int i = 0; i < playerAList.size(); i++) {
			Player storedPlayer = playerAList.get(i);
			// if found case
			if (storedPlayer.getName().equalsIgnoreCase(currentPlayer)) {
				found = true;
				playerIndex =i;
				gamesWon = storedPlayer.getGamesWon();
				gamesCount = storedPlayer.getGamesCount();
				player = new Player(currentPlayer, gamesWon, gamesCount);
				break;
			}
		}
		// If player is not found adding them
		if (!found) {
			player = new Player(currentPlayer);
			playerAList.add(player);
			playerIndex++;
		}		
	}

	public void setupANewGame() {
		// complete this. At the end it has to initialize the currentWord and the hangman:
		// hangman = new Hangman(currentWord);
		showSummary();// Showing stats Prior to game Play
		// Used a make shift Queue to loop the words since I split them up into a String Array
		if(wordQueue>words.length()-1){
			wordQueue=0;
		}
		String[] split = words.split(" ");// Using .split to split the words at " " and send them into an array.
		currentWord = split[wordQueue];// The word choosen from the Array backed on the queue
		hangman = new Hangman(currentWord);
		hangman.maskWord();
		wordQueue++;
	}

	public boolean playTurn() {
		// Input Prompt
		String guess = " ";
		guess = JOptionPane.showInputDialog(hangman.mainDisplay()).toUpperCase();
		// Setting the Input Quit Command		
		if ("QUIT".equals(guess)) {
			return false;
			// Input Control through the else if Statement
		} else if (guess.length() > 1||guess.length()<1||guess==" "||guess==null) {
			JOptionPane.showMessageDialog(null, "Please enter ONE letter.");
			playTurn();// recursive loop
		}
		char letterGuessed = guess.charAt(0);
		hangman.playLetter(letterGuessed);
		return !(hangman.finished() || hangman.completed());// Returning booleans that determine if the game ends or not
	}
	// Had to Add throws FileNotFoundException due to the way I choose to save Player Stats
	public boolean playGame() throws FileNotFoundException { 
		boolean stillPlaying = true;
		while (!hangman.finished() && stillPlaying) {
			stillPlaying = playTurn();
		}
		player.incrementGames(hangman.completed());
		String message = "Game ended";
		if (hangman.finished()) {
			message += ".";
		} else {
			message += " incompleted!";
		}
		if (hangman.completed()) {
			message += "\nYou won!!!";		
		} else {
			message += " \nYou lose :(";
		}	
		message += "\n" + hangman.getWordStatus() + "\nPlay again? (y/n)";
		String input = JOptionPane.showInputDialog(message);
		if (input == null || input.length() == 0) {
			return false;
		} else if (input.toLowerCase().charAt(0) == 'y') {
			return true;
		} else {
			updateListofPlayers(); // I had to slip this in to save the Players Stats to the Text File
			return false;
		}
	}
	
	// Saving the Players Stats to the players.txt file.
	public void updateListofPlayers() throws FileNotFoundException {
		showSummary();
		//Using set with Player Index to track which
		playerAList.set(playerIndex, new Player(player.getName(),player.getGamesWon(),player.getGamesCount()));
		// Open File to write into 
		PrintWriter fileOut = new PrintWriter(new FileOutputStream("players.txt", false));
		// forloop to iterate through
		for (int i = 0; i < playerAList.size(); i++) {
            Player player = playerAList.get(i);
            fileOut.println(player.getName());
            fileOut.println(player.getGamesWon());
            fileOut.println(player.getGamesCount());
        }
		// Closing all Open sources to stop memory leak.
        fileOut.close();
		scan.close();		
	}
	// Reading in the file players.txt Then creating them as a Player object to store into an ArrayList
	public void getListofPlayers() throws FileNotFoundException {
		// Open Stream to read File and load players into list
		scan = new Scanner(new FileInputStream("hangmanplayers1.txt"));
		while (scan.hasNextLine()) {
			String plyrName = scan.nextLine();
			int plyrWins = Integer.parseInt(scan.nextLine());
			int plyrLosses = Integer.parseInt(scan.nextLine());
			Player nextPlayer = new Player(plyrName, plyrWins, plyrLosses);
			playerAList.add(nextPlayer);
		}
		
	}
	// I used this method to display Character Properties
	public void showSummary() {
		JOptionPane.showMessageDialog(null, player.toString());
	}
}
