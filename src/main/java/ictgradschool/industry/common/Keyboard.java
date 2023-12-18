package ictgradschool.industry.common;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * <h1>CompSci 101 - Keyboard Class</h1>

 * <p>This class is used for input from the keyboard. <strong>YOU DO NOT NEED
 * TO UNDERSTAND THE DETAILS OF THIS CLASS.</strong></p>
 * <p>To use this class, put it in the same directory as the source file for your program.</p>
 *
 * <p><strong>Example usage:</strong></p>
 *
 * <code>String input = Keyboard.readInput();</code>
 *
 * <p>This will assign the line of text entered at the keyboard (as a {@link String}) to the input variable.</p>
 *
 * @version 4th March, 2011
 */
public class Keyboard {
    private static final Scanner in = new Scanner(System.in);
    private static boolean redirected = false;

    /**
     * Read a line of text from the STDIN console, returning the result as a {@link String}
     * @return A line of text from the console
     */
    public static String readInput() {
        try {
            if (!redirected) {
                redirected = System.in.available() != 0;
            }
        } catch (IOException e) {
            System.err.println("An error has occurred in the Keyboard constructor.");
            e.printStackTrace();
            System.exit(-1);
        }

        try {
            String input = in.nextLine();
            if (redirected) {
                System.out.println(input);
            }
            return input;
        } catch (NoSuchElementException e) {
            return null; // End of file
        } catch (IllegalStateException e) {
            System.err.println("An error has occurred in the Keyboard.readInput() method.");
            e.printStackTrace();
            System.exit(-1);
        }
        return null;
    }
}
