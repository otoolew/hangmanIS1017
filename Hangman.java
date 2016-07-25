package hangmanIS1017;
import java.util.Arrays;
import javax.swing.JOptionPane;
/**
 * University of Pittsburgh
 * IS1017 Java Development
 * Fall 2015
 * @author William O'Toole
 */
public class Hangman {
	// Declaring instance variables
	private String secretWord;
	private String playedLetters;
	private int failedAttempts;
	public final int MAX_ATTEMPTS = 5;
	private char[] copyWord;

	public Hangman(String word) {
		secretWord = word.toUpperCase();
		playedLetters = "";
		failedAttempts = 0;
	}

	public void playLetter(char letter) {
		String secWord;
		boolean found = false;
		boolean isGuessed = false;
		// Searching for a char that matched letter
		for (int j = 0; j < playedLetters.length(); j++) {
			char played = playedLetters.charAt(j);
			if (letter == played) {
				isGuessed = true;
				JOptionPane.showMessageDialog(null, "Already Played that Letter!");
			}
		}
		if (!isGuessed) {
			for (int i = 0; i < secretWord.length(); i++) {
				char secretLetter = secretWord.charAt(i);
				if (letter == secretLetter) {
					secWord = playedLetters.concat(Character.toString(secretLetter));
					copyWord[i] = letter;
					found = true;
				}
			}

			playedLetters = playedLetters.concat(Character.toString(letter));
		}
		if (found == false) {
			failedAttempts++;
		}
	}

	public boolean matchWord(String word) {
		if (word.equals(secretWord)) {
			playedLetters = playedLetters.concat(word);
			
			return true;
		}
		failedAttempts++;
		return false;
	}

	public boolean finished() {
		return failedAttempts >= MAX_ATTEMPTS;
	}

	public boolean completed() {
		return getWordStatus().equals(secretWord);
	}

	public int getFailedAttempts() {
		return failedAttempts;
	}

	public String getPlayedLetters() {
		return playedLetters;
	}

	public String getSecretWord() {
		return secretWord;
	}

	public void maskWord() {
		copyWord = secretWord.toCharArray();
		char mask = '_';
		Arrays.fill(copyWord, mask);
	}
	// Preset message
	public String mainDisplay() {
		String main = "HANGMAN"				
				+ "\n" + getWordStatus()
				+ "\n"
				+ "\nGuessed Letters"
				+ "\n" + getPlayedLetters()
				+ "\nFailed Attempts: " + getFailedAttempts()
				+ "\nPlease Guess a Letter!";

		return main;
	}
	// returns a "masked" string 
	String getWordStatus() {
		String result;
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < copyWord.length; i++) {
			b.append(copyWord[i]);
		}
		result = b.toString();
		return result;
	}
}
