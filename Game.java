/* A memory game using sequential logic
Objective: Create a memory game using flip-flops to remember the sequence of colors or numbers

Each SRFlipFlop object corresponds to a number in the sequence, where setting a flipflop
represents adding a new number to the memory while resetting all flipflops represents clearing
memory after the player has seen the sequence.
In the Game class, a list of SRFlipFlop objects are used to store the state for each number in the sequence.
When a new number is added to the sequence to memorize:
    * a new SRFlipFlop object is created and set
    * the flipflop is added to the array list flipFlops which holds the memory of each number in the sequence
    * after being displayed to the player, all flipflops are reset clearing the memory and simulating the game
    forgetting the sequence until being recalled in the next round.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// This class mimics an SR flipflop as a circuit
class SRFlipFlop {
    private boolean set;   // set state
    private boolean reset; // reset state

    public SRFlipFlop() {
        this.set = false;
        this.reset = true; // Initial reset
    }

    public void set() {
        this.set = true;
        this.reset = false;
    }

    public void reset() {
        this.set = false;
        this.reset = true;
    }

    public boolean isSet() {
        return set;
    }

    public boolean isReset() {
        return reset;
    }
}

class MemoryGame {
    private List<Integer> sequence;
    private List<SRFlipFlop> flipFlops;
    private Random random;

    public MemoryGame() {
        sequence = new ArrayList<>();
        flipFlops = new ArrayList<>();
        random = new Random();
    }

    // Adds a new random number to the sequence
    public void addNumber() {
        int number = random.nextInt(10); // Random number between 0-9
        sequence.add(number);

        SRFlipFlop flipFlop = new SRFlipFlop();
        flipFlop.set(); // Set flip-flop for this number
        flipFlops.add(flipFlop);
    }

    // Resets all flip-flops to prepare for a new round
    public void resetAll() {
        for (SRFlipFlop flipFlop : flipFlops) {
            flipFlop.reset();
        }
    }

    // Displays the sequence to the player
    public void displaySequence() {
        System.out.print("Memorize this sequence: ");
        for (Integer number : sequence) {
            System.out.print(number + " ");
        }
        System.out.println();
        wait(5000);
        resetAll(); // Reset all flip-flops after showing the sequence
    }

    // Pauses for the player to memorize the sequence
    private void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch(InterruptedException e) {
            System.out.println("Waiting caused an error.");
            Thread.currentThread().interrupt();
        }
    }

    // Returns the size of the sequence
    public int getSequenceSize() {
        return sequence.size();
    }

    // Checks the player's input against the sequence
    public boolean checkSequence(List<Integer> playerInput) {
        if (playerInput.size() != sequence.size()) {
            return false;
        }
        for (int i = 0; i < sequence.size(); i++) {
            if (!playerInput.get(i).equals(sequence.get(i))) {
                return false;
            }
        }
        return true;
    }
}

public class Game {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MemoryGame game = new MemoryGame();
        int score = 0;

        System.out.println("Welcome to the Memory Game!");
        boolean playing = true;

        while (playing) {
            game.addNumber(); // Add a new number to the sequence
            game.displaySequence(); // Show the sequence to the player

            for (int i = 0; i < 100; i++){
                System.out.println(" "); // Adds blank lines to prevent players from just looking at the numbers in the console
            }
            List<Integer> playerInput = new ArrayList<>();
            for (int i = 0; i < game.getSequenceSize(); i++) { // Use the new method here
                System.out.println("What was number " + (i+1));
                playerInput.add(scanner.nextInt());
            }

            if (game.checkSequence(playerInput)) {
                System.out.println("Correct! Get ready for the next round.");
                score++;
            } else {
                System.out.println("Sorry that was incorrect. Game Over. :(");
                playing = false;
            }
        }

        System.out.println("Thanks for playing! \nFinal Score: " + score);
        scanner.close();
    }
}

