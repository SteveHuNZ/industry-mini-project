package ictgradschool.industry.miniproject;
import java.util.*;

public class BullsAndCows {
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
            System.out.println(prompt);
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
    public static void start(){
        Random random = new Random();
        int[] sysSecret = new int[4];
        Set<Integer> uniqueNum = new HashSet<>();
        // HashSet can only add unique data;
        for(int i = 0; i < sysSecret.length; i++){
            int ran;
            do{
                ran = random.nextInt(10);
            }while(!uniqueNum.add(ran));  // when uniqueNum.add() is true, that means this random number is unique
            // so we need to break the do-while loop; when it is not unique,
            // continue the loop and keep passing random generated number to variable "ran";
            sysSecret[i] = ran;
        }
        Scanner input = new Scanner(System.in);
        System.out.print("Please select your opponent(a.easy AI / b.medium AI): ");
        String mode = input.nextLine().trim();
        int userInputSecret = getUserInput(input, "Please enter your secret code: ");
        int[] userSecret = new int[4];
        getDigit(userSecret, userInputSecret);
        System.out.println("-".repeat(3));


        if(mode.equalsIgnoreCase("a")) {
            Set<String> existSysGuess = new HashSet<>();
            // verifying sanity for input.
            int chance = 7;
            while (chance > 0) {
                int userInputGuess = getUserInput(input, "You guess(" + chance + " chances remaining): ");
                int[] userGuess = new int[4];
                getDigit(userGuess, userInputGuess);
                int[] sysGuess = generateUniqueGuess(existSysGuess, random);
                System.out.println(check(userGuess, sysSecret));
                System.out.println(" ");
                System.out.print("Computer guess: ");
                for (int i : sysGuess) {
                    System.out.print(i);
                }
                System.out.println();
                System.out.println(check(userSecret, sysGuess));
                System.out.println("-".repeat(3));
                chance--;
                if (chance == 0) {
                    System.out.println("Game over. Draw! System randomly generated secret code: " + Arrays.toString(sysSecret));
                }
            }
        }else if(mode.equalsIgnoreCase("b")){
            Set<String> existSysGuess = new HashSet<>();
            int chance = 7;
            while (chance > 0) {
                int userInputGuess = getUserInput(input, "You guess(" + chance + " chances remaining: ");
                int[] userGuess = new int[4];
                getDigit(userGuess, userInputGuess);
                int[] sysGuess = generateUniqueGuess(existSysGuess, random);
                System.out.println(check(userGuess, sysSecret));
                System.out.println(" ");
                System.out.print("Computer guess: ");
                for (int i : sysGuess) {
                    System.out.print(i);
                }
                System.out.println();
                System.out.println(check(userSecret, sysGuess));
                System.out.println("-".repeat(3));
                chance--;
                if (chance == 0) {
                    System.out.println("Game over. Draw! System randomly generated secret code: " + Arrays.toString(sysSecret));
                }
            }
        }
    }

    private static String arrayToString(int[] array) {
        StringBuilder sb = new StringBuilder();
        for (int value : array) {
            sb.append(value);
        }
        return sb.toString();
    }

    public static String check(int[]arr1, int[]arr2){
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
        if(bulls == 4) {
            return "Result: 4 bulls and 0 cows\n" + "You win! :)";
        }
        return "Result: " + bulls + "bulls and " + cows + "cows";
    }
    public static void getDigit(int[] array, int num){
        array[0] = num / 1000;
        array[1] = num / 100 % 10;
        array[2] = num / 10 % 10;
        array[3] = num % 10;
    }
    public static void main(String[] argus){
        start();
    }
}
