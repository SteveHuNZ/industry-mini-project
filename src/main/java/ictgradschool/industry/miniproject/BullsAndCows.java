package ictgradschool.industry.miniproject;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BullsAndCows {

    public static void start() {
        List<String> gameHistory = new ArrayList<>();  // used for output record into a txt file.
        int turnNumber = 0;

        Scanner input = new Scanner(System.in);
        System.out.println("Enter 1 to guess manually, or 2 to use a file for guesses:");
        int choice = input.nextInt();
        input.nextLine(); // Clear buffer

        List<String> fileGuesses = new ArrayList<>();
        if (choice == 2) {
            System.out.println("Enter the filename:");
            String filename = input.nextLine();
            try {
                Scanner fileScanner = new Scanner(new File(filename));
                while (fileScanner.hasNextLine()) {
                    fileGuesses.add(fileScanner.nextLine().trim());
                }
                fileScanner.close();
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
                return;
            }
        }


        // trying to get the possible list which contains unique digit numbers from 1000 to 9999;
        Set<String> existSysGuess = new HashSet<>();
        Set<Integer> uniqueNumberSet = generateUniqueDigitNumbers();  // Here we use HashSet is because hashSet can only add unique elements.
        ArrayList<Integer> uniqueNumberList = new ArrayList<>(uniqueNumberSet); // Here we use ArrayList is because it supports us to sort the elements in ascending order.
        uniqueNumberList.sort(Integer::compareTo);
        // create system secret code,
        Random random = new Random();
        int[] sysSecret = new int[4];
        Set<Integer> uniqueNum = new HashSet<>();
        // HashSet can only add unique elements;
        for(int i = 0; i < sysSecret.length; i++){
            int ran;
            do{
                ran = random.nextInt(10);
            }while(!uniqueNum.add(ran));  // when uniqueNum.add() is true, that means this random number is unique
            // so we need to break the do-while loop; when it is not unique,
            // continue the loop and keep passing random generated number to variable "ran";
            sysSecret[i] = ran;
        }
        String mode;
        while(true) {
            System.out.println("Please select your opponent. **Select letters to switch the difficulty**");
            System.out.print("(A.easy AI / B.medium AI / C. hard AI ): ");
            mode = input.nextLine().trim();
            if (!(mode.equalsIgnoreCase("A") || mode.equalsIgnoreCase("B") || mode.equalsIgnoreCase("C"))) {
                System.out.println("Invalid selection!");
            }else{
                break;
            }
        }
        int userInputSecret = getUserInput(input, "Please enter your secret code: ");
        int[] userSecret = new int[4];
        getDigit(userSecret, userInputSecret);
        System.out.println("-".repeat(3));
        int guessIndex = 0;


        if(mode.equalsIgnoreCase("A")) {
            // verifying sanity for input.
            int chance = 7;
            Out:
            while (chance > 0) {
                int userInputGuess;
                if(choice == 1){
                    userInputGuess = getUserInput(input, "You guess(" + chance + " chances remaining): ");
                }else{
                    if (guessIndex < fileGuesses.size()) {
                        userInputGuess = Integer.parseInt(fileGuesses.get(guessIndex));
                        System.out.println("You guess(" + chance + " chances remaining): " + userInputGuess);
                        guessIndex++;

                    } else {
                        System.out.println("No more guesses in file. Switching to manual mode. " + (7- fileGuesses.size()) + " attempts required.");
                        for (int i = fileGuesses.size(); i < 7; i++) {
                            userInputGuess = getUserInput(input, "You guess(" + (7 - i) + " chances remaining): ");
                            int[] userGuess = new int[4];
                            getDigit(userGuess, userInputGuess);
                            int[] sysGuess = generateUniqueGuess(existSysGuess, random);
                            int[] result = check(userGuess, sysSecret);
                            System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                            if (result[0] == 4) {
                                System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
                                gameHistory.add("You win!!! :) ");
                                break;  // Break loop. Game over.
                            }
                            System.out.println(" ");
                            System.out.print("Computer guess: ");
                            for (int j : sysGuess) {
                                System.out.print(j);
                            }
                            System.out.println();
                            int[] sysResult = check(userSecret, sysGuess);
                            System.out.println("Computer's Result: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                            if (sysResult[0] == 4) {
                                System.out.println("Computer wins! Secret code: " + Arrays.toString(sysSecret));
                                gameHistory.add("Computer win!!! :) ");
                                break Out;
                            }
                            System.out.println("-".repeat(3));
                        }
                        break;
                    }
                }
                int[] userGuess = new int[4];
                getDigit(userGuess, userInputGuess);
                int[] sysGuess = generateUniqueGuess(existSysGuess, random);
                int[] result = check(userGuess, sysSecret);
                System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                if (result[0] == 4) {
                    System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
                    gameHistory.add("You win!!! :) ");
                    break;  // Break loop. Game over.
                }
                System.out.println(" ");
                System.out.print("Computer guess: ");
                for (int i : sysGuess) {
                    System.out.print(i);
                }
                System.out.println();
                int[] sysResult = check(userSecret, sysGuess);
                System.out.println("Computer's Result: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                gameHistory.add("You guessed " + userInputGuess + ", scoring: " + result[0] + " bulls and " + result[1] + " cows\n" +
                    "Computer guessed " + Arrays.toString(sysGuess) + ", scoring: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                if (sysResult[0] == 4) {
                    System.out.println("Computer wins! Secret code: " + Arrays.toString(sysSecret));
                    gameHistory.add("Computer win!!! :) ");
                    break;
                }


                System.out.println("-".repeat(3));
                chance--;
                if (chance == 0) {
                    System.out.println("Game over. Draw! Secret code: " + Arrays.toString(sysSecret));
                }
            }

        }else if(mode.equalsIgnoreCase("B")){
            int chance = 7;
            Out:
            while (chance > 0) {
                int userInputGuess;
                if(choice == 1){
                    userInputGuess = getUserInput(input, "You guess(" + chance + " chances remaining): ");
                }else{
                    if (guessIndex < fileGuesses.size()) {
                        userInputGuess = Integer.parseInt(fileGuesses.get(guessIndex));
                        System.out.println("You guess(" + chance + " chances remaining): " + userInputGuess);
                        guessIndex++;
                    } else {
                        System.out.println("No more guesses in file. Switching to manual mode. " + (7- fileGuesses.size()) + " attempts required.");
                        for (int i = fileGuesses.size(); i < 7; i++) {
                            userInputGuess = getUserInput(input, "You guess(" + (7- i) + " chances remaining): ");
                            int[] userGuess = new int[4];
                            getDigit(userGuess, userInputGuess);
                            int[] sysGuess = generateUniqueGuess(existSysGuess, random);
                            int[] result = check(userGuess, sysSecret);
                            System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                            if (result[0] == 4) {
                                System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
                                gameHistory.add("you win!!! :) ");
                                break;  // Break loop. Game over.
                            }
                            System.out.println(" ");
                            System.out.print("Computer guess: ");
                            for (int j : sysGuess) {
                                System.out.print(j);
                            }
                            System.out.println();
                            int[] sysResult = check(userSecret, sysGuess);
                            System.out.println("Computer's Result: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                            if (sysResult[0] == 4) {
                                System.out.println("Computer wins! Secret code: " + Arrays.toString(sysSecret));
                                gameHistory.add("Computer win!!! :) ");
                                break Out;
                            }
                            System.out.println("-".repeat(3));
                        }
                        break;
                    }
                }
                int[] userGuess = new int[4];
                getDigit(userGuess, userInputGuess);
                int[] sysGuess = generateUniqueGuess(existSysGuess, random);
                int[] result = check(userGuess, sysSecret);
                System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                if (result[0] == 4) {
                    System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
                    gameHistory.add("You win!!! :) ");
                    break;  // Break loop. Game over.
                }
                System.out.println(" ");
                System.out.print("Computer guess: ");
                for (int i : sysGuess) {
                    System.out.print(i);
                }
                System.out.println();
                int[] sysResult = check(userSecret, sysGuess);
                System.out.println("Computer's Result: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                gameHistory.add("You guessed " + userInputGuess + ", scoring: " + result[0] + " bulls and " + result[1] + " cows\n" +
                    "Computer guessed " + Arrays.toString(sysGuess) + ", scoring: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                if (sysResult[0] == 4) {
                    System.out.println("Computer wins! Secret code: " + Arrays.toString(sysSecret));
                    gameHistory.add("Computer win!!! :) ");
                    break;
                }
                System.out.println("-".repeat(3));
                chance--;
                if (chance == 0) {
                    System.out.println("Game over. Draw! System randomly generated secret code: " + Arrays.toString(sysSecret));
                }
            }

        }else if(mode.equalsIgnoreCase("C")){
            // randomly generate a number in uniqueNumberList as the first sysGuess;
            int randomIndex;
            // possibleGuess will change dynamically according to each iteration.
            List<Integer> possibleGuess = new ArrayList<>(uniqueNumberList);
            int chance = 7;
            Out:
            while(chance > 0){
                int userInputGuess;
                if(choice == 1){
                    userInputGuess = getUserInput(input, "You guess(" + chance + " chances remaining): ");
                }else{
                    if (guessIndex < fileGuesses.size()) {
                        userInputGuess = Integer.parseInt(fileGuesses.get(guessIndex));
                        System.out.println("You guess(" + chance + " chances remaining): " + userInputGuess);
                        guessIndex++;
                    } else {
                        System.out.println("No more guesses in file. Switching to manual mode. " + (7- fileGuesses.size()) + " attempts required.");
                        for (int i = fileGuesses.size(); i < 7; i++) {
                            userInputGuess = getUserInput(input, "You guess(" + (7- i) + " chances remaining): ");
                            int[] userGuess = new int[4];
                            getDigit(userGuess, userInputGuess);
                            int[] result = check(userGuess, sysSecret);
                            System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                            if (result[0] == 4) {
                                System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
                                gameHistory.add("You win!!! :) ");
                                break Out;  // Break loop. Game over.
                            }
                            System.out.println(" ");

                            randomIndex = random.nextInt(possibleGuess.size());  // generate new randomIndex
                            int sysGuessNumber = possibleGuess.get(randomIndex);
                            System.out.print("Computer guess: " + possibleGuess.get(randomIndex));
                            System.out.println();
                            int[] sysGuess = new int[4];
                            getDigit(sysGuess, sysGuessNumber);
                            int[] sysResult = check(userSecret, sysGuess);
                            System.out.println("Computer's Result: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                            gameHistory.add("You guessed " + userInputGuess + ", scoring: " + result[0] + " bulls and " + result[1] + " cows\n" +
                                "Computer guessed " + Arrays.toString(sysGuess) + ", scoring: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                            if (sysResult[0] == 4) {
                                System.out.println("Computer wins! secret code: " + Arrays.toString(sysSecret));
                                gameHistory.add("Computer win!!! :) ");
                                break Out;
                            }
                            Iterator<Integer> iterator = possibleGuess.iterator();
                            // hasNext() is true means there still are elements in this list.
                            while (iterator.hasNext()) {
                                int nextGuess = iterator.next();
                                int[] nextGuessArray = new int[4];
                                getDigit(nextGuessArray, nextGuess);
                                int[] nextResult = check(sysGuess, nextGuessArray);

                                if (!(nextResult[0] == sysResult[0] && nextResult[1] == sysResult[1])) {
                                    iterator.remove();
                                }
                            }
                        }
                        break;
                    }

                }
                int[] userGuess = new int[4];
                getDigit(userGuess, userInputGuess);
                int[] result = check(userGuess, sysSecret);
                System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                if (result[0] == 4) {
                    System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
                    gameHistory.add("You win!!! :) ");
                    break;  // Break loop. Game over.
                }
                System.out.println(" ");

                randomIndex = random.nextInt(possibleGuess.size());  // generate new randomIndex
                int sysGuessNumber = possibleGuess.get(randomIndex);
                System.out.print("Computer guess: " + possibleGuess.get(randomIndex));
                System.out.println();
                int[] sysGuess = new int[4];
                getDigit(sysGuess, sysGuessNumber);
                int[] sysResult = check(userSecret, sysGuess);
                System.out.println("Computer's Result: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                gameHistory.add("You guessed " + userInputGuess + ", scoring: " + result[0] + " bulls and " + result[1] + " cows\n" +
                    "Computer guessed " + Arrays.toString(sysGuess) + ", scoring: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                if (sysResult[0] == 4) {
                    System.out.println("Computer wins! secret code: " + Arrays.toString(sysSecret));
                    gameHistory.add("Computer win!!! :) ");
                    break;
                }
                // Pruning possibleGuess
                Iterator<Integer> iterator = possibleGuess.iterator();
                // hasNext() is true means there still are elements in this list.
                while (iterator.hasNext()) {
                    int nextGuess = iterator.next();
                    int[] nextGuessArray = new int[4];
                    getDigit(nextGuessArray, nextGuess);
                    int[] nextResult = check(sysGuess, nextGuessArray);

                    if (!(nextResult[0] == sysResult[0] && nextResult[1] == sysResult[1])) {
                        iterator.remove();
                    }
                }
                System.out.println("-".repeat(3));
                chance--;
                if (chance == 0) {
                    System.out.println("Game over. Draw! System randomly generated secret code: " + Arrays.toString(sysSecret));
                }
            }

        }else{
            System.out.println("Invalid selection!");
        }
        saveGameResults(input, Arrays.toString(userSecret), Arrays.toString(sysSecret), gameHistory);
    }

    public static void saveGameResults(Scanner scanner, String playerCode, String computerCode, List<String> gameHistory) {
        System.out.print("Do you want to save the game results? (yes/no): ");
        String saveResponse = scanner.nextLine();

        if (saveResponse.equalsIgnoreCase("yes")) {
            System.out.println("Enter the filename to save results:");
            String saveFileName = scanner.nextLine();

            try (PrintWriter writer = new PrintWriter(saveFileName, StandardCharsets.UTF_8)) {
                writer.println("Bulls & Cows game result.");
                writer.println("Your code: " + playerCode);
                writer.println("Computer code: " + computerCode);
                writer.println("---");
                for (int i = 0; i < gameHistory.size(); i++) {
                    String historyEntry = gameHistory.get(i);
                    writer.println("Turn " + (i + 1) + ":");
                    writer.println(historyEntry);
                    // Check if this history entry indicates the computer won
                    if (historyEntry.contains("scoring: 4 bulls")) {
                        // If the computer wins, print the win message
                        writer.println("Computer win!!! :)");
                        // Check if this is the last turn; if not, add the separator
                        if (i < gameHistory.size() - 1) {
                            writer.println("---");
                        }
                        break; // No more turns need to be printed, exit the loop
                    }
                    // If the game is continuing, print the separator
                    writer.println("-".repeat(3));
                }
            } catch (IOException e) {
                System.out.println("An error occurred while saving: " + e.getMessage());
            }
        }
    }



    public static int[] generateUniqueGuess(Set<String> existSysGuess, Random random){
        Set<Integer> uniqueNum = new HashSet<>();
        int[] sysGuess;
        String sysGuessStr;
        // Two nested do-while loops are used to avoid duplicated sysGuess.
        // The first do-while loop can eliminate duplicated single digit in the 4-digit-number.
        // The second do-while loop can avoid duplicated randomly spawned 4-digit-number.
        do{
            sysGuess = new int[4];
            for (int i = 0; i < sysGuess.length; i++) {
                int ran;
                do {
                    ran = random.nextInt(10);
                } while (!uniqueNum.add(ran));
                sysGuess[i] = ran;
            }
            sysGuessStr = arrayToString(sysGuess);
        }while(existSysGuess.contains(sysGuessStr));
        existSysGuess.add(sysGuessStr);
        return sysGuess;
    }
    public static int getUserInput(Scanner input, String prompt){
        int userInput;
        while (true) {
            System.out.print(prompt);
            String str2 = input.nextLine().trim();
            if (str2.length() == 4) {
                try {
                    userInput = Integer.parseInt(str2);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input!(4 digit numbers)Try again!");
                }
            } else {
                System.out.println("Invalid length!(4 digit numbers)Try again!");
            }
        }
        return userInput;
    }
    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int value : array) {
            sb.append(value);
        }
        return sb.toString();
    }

    public static int[] check(int[]arr1, int[]arr2){
        int bulls = 0;
        int cows = 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                if (arr1[i] == arr2[j]) {
                    cows++;
                }
                if (arr1[i] == arr2[j] && i == j) {
                    bulls++;
                    cows--;
                }
            }
        }
        return new int[] { bulls, cows } ;
    }
    public static void getDigit(int[] array, int num){
        array[0] = num / 1000;
        array[1] = num / 100 % 10;
        array[2] = num / 10 % 10;
        array[3] = num % 10;
    }
    public static boolean isUniqueDigit(int number){
        boolean[] digitSeen = new boolean[10];  // check the comments in order.
        // 1. Creating 10 elements aims to use the index(from 0 - 9) to check all the 4 digits of the passed number is unique.
        // 2. When we create a boolean array, each of its elements is "false" by default.
        int currentNum = number; // 3. Here we create a new integer variable is trying to process the number without change the original parameter.
        while(currentNum > 0){   // 4. later on, we need to divide currentNum with 10 to access its last digit through 4 iteration.
            // So we have to set a boundary that when the currentNum is bigger than 0, we keep processing it. otherwise, break the loop.
            int digit = currentNum % 10;    // To get the last digit;
            if(digitSeen[digit]){  // 5. if the digit has never showed up, then it was "false" by default, which means we won't get into this "if" branch.
                return false;    // 7. return false means the number that passed into this method does not contain unique digit.
            }
            digitSeen[digit] = true;  //6. In the first iteration, the digit will be "true", and if it showed up again, it will get into the "if" branch.
            currentNum /= 10;  // 8. To eliminate the last digit which has been processed.
        }
        return true;
    }
    public static Set<Integer> generateUniqueDigitNumbers(){
        HashSet<Integer> numbers = new HashSet<>();
        for (int i = 1000; i < 9999; i++) {
            if(isUniqueDigit(i)){
                numbers.add(i);
            }
        }
        return numbers;
    }
    public static void main(String[] argus){
        start();
    }
}
