import java.util.Random;
import javax.swing.JOptionPane;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;

        int attempts = 0;
        int score = 100; 

    
        while (true) {
           
            String guessStr = JOptionPane.showInputDialog(null, "Guess the number (between 1 and 100):");
            
            int guess;
            try {
                guess = Integer.parseInt(guessStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid input. Please enter a number.");
                continue;
            }

            attempts++;
            if (guess == randomNumber) {
                JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number in " + attempts + " attempts.\nYour score: " + score);
                break; 
            } else if (guess < randomNumber) {
                JOptionPane.showMessageDialog(null, "Try a higher number.");
            } else {
                JOptionPane.showMessageDialog(null, "Try a lower number.");
            }

            score -= 10;

            if (attempts >= 10) {
                JOptionPane.showMessageDialog(null, "You've reached the maximum number of attempts.\nThe number was " + randomNumber + "\nGame Over!");
                break; 
            }
        }
    }
}
