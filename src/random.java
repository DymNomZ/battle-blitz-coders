package src;
import javax.swing.JOptionPane;

public class random {
    public static void main(String[] args) {
        int generated = 1 + (int) (Math.random() * 9);
        String input;
        int guess;
        int exit = 0;

        do {
            input = JOptionPane.showInputDialog(null, "Enter a number between 1 and 10", "Random game", JOptionPane.PLAIN_MESSAGE);
            guess = Integer.parseInt(input);

            if (guess == generated) {
                JOptionPane.showMessageDialog(null, "Congratulations! You guessed the number!", "Random game", JOptionPane.PLAIN_MESSAGE);
                exit = 1;
            }
            else if (guess > generated) {
                JOptionPane.showMessageDialog(null, "No banana!\nThe number I'm thinking of is less than " + guess);
            }
            else {
                JOptionPane.showMessageDialog(null, "No banana!\nThe number I'm thinking of is greater than " + guess);
            }

        } while (exit != 1);
    }
}
        