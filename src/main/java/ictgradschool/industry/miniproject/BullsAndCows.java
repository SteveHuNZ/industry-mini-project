package ictgradschool.industry.miniproject;
import java.util.*;

public class BullsAndCows {

    public static void start(){
        // trying to get the possible list which contains unique digit numbers from 1000 to 9999;
        Set<Integer> uniqueNumberSet = generateUniqueDigitNumbers();  // Here we use HashSet is because hashSet can only add unique elements.
        ArrayList<Integer> uniqueNumberList = new ArrayList<>(uniqueNumberSet); // Here we use ArrayList is because it supports us to sort the elements in ascending order.

        uniqueNumberList.sort(Integer::compareTo);
        // create system secret code,
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
        System.out.println("Please select your opponent. **Select letters to switch the difficulty**");
        System.out.print("(A.easy AI / B.medium AI / C. hard AI ): ");
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
                /*
                System.out.println(check(userGuess, sysSecret));
                    if(bulls == 4) {
                return "Result: 4 bulls and 0 cows\n" + "You win! :)";
                return "Result: " + bulls + "bulls and " + cows + "cows";
                 */
                int[] result = check(userGuess, sysSecret);
                System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                if (result[0] == 4) {
                    System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
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
                if (sysResult[0] == 4) {
                    System.out.println("Computer wins! Secret code: " + Arrays.toString(sysSecret));
                    break;
                }
                System.out.println("-".repeat(3));
                chance--;
                if (chance == 0) {
                    System.out.println("Game over. Draw! Secret code: " + Arrays.toString(sysSecret));
                }
            }
        }else if(mode.equalsIgnoreCase("b")){
            Set<String> existSysGuess = new HashSet<>();
            int chance = 7;
            while (chance > 0) {
                int userInputGuess = getUserInput(input, "You guess(" + chance + " chances remaining): ");
                int[] userGuess = new int[4];
                getDigit(userGuess, userInputGuess);
                int[] sysGuess = generateUniqueGuess(existSysGuess, random);
                int[] result = check(userGuess, sysSecret);
                System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                if (result[0] == 4) {
                    System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
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
                if (sysResult[0] == 4) {
                    System.out.println("Computer wins! Secret code: " + Arrays.toString(sysSecret));
                    break;
                }
                System.out.println("-".repeat(3));
                chance--;
                if (chance == 0) {
                    System.out.println("Game over. Draw! System randomly generated secret code: " + Arrays.toString(sysSecret));
                }
            }
        }else if(mode.equalsIgnoreCase("c")){
            // randomly generate a number in uniqueNumberList as the first sysGuess;
            int randomIndex = random.nextInt(uniqueNumberList.size());
            int chance = 7;
            Out:
            while(chance > 0){
                int userInputGuess = getUserInput(input, "You guess(" + chance + " chances remaining): ");
                int[] userGuess = new int[4];
                getDigit(userGuess, userInputGuess);
                int sysGuessNumber = uniqueNumberList.get(randomIndex);
                int[] result = check(userGuess, sysSecret);
                System.out.println("Result: " + result[0] + " bulls and " + result[1] + " cows");
                if (result[0] == 4) {
                    System.out.println("You win! :) Secret code: " + Arrays.toString(sysSecret));
                    break;  // Break loop. Game over.
                }
                System.out.println(" ");
                System.out.print("Computer guess: " + uniqueNumberList.get(randomIndex));
                System.out.println();
                int[] sysGuess = new int[4];
                getDigit(sysGuess, sysGuessNumber);
                int[] sysResult = check(userSecret, sysGuess);
                System.out.println("Computer's Result: " + sysResult[0] + " bulls and " + sysResult[1] + " cows");
                if (sysResult[0] == 4) {
                    System.out.println("Computer wins! secret code: " + Arrays.toString(sysSecret));
                    break;
                }
                Iterator<Integer> iterator = uniqueNumberList.iterator();
                // we cannot use keyword "remove" when iterating data. So we have to use "iterator".
                // iterator is an interface which is used to remove specific element when traversing the array.
                while (iterator.hasNext()) {  //"hasNext()" is used to check if there are remaining elements in the iterator.
                    int i = iterator.next();   // "next()" is used to get the next element.
                    int[] testArray = new int[4];
                    getDigit(testArray, i);
                    int[] tempResult = check(sysGuess, testArray);
                    if (!(tempResult[0] == sysResult[0] && tempResult[1] == sysResult[1])) {
                        iterator.remove(); // Only the iterator is allowed to remove elements when iterating. Otherwise, some exceptions may occur.
                    }
                }if(uniqueNumberList.size() == 1){
                    System.out.println("Your secret code is: " + uniqueNumberList.get(0));
                    System.out.println("Computer wins! secret code: " + Arrays.toString(sysSecret));
                    break Out;
                }

                System.out.println("-".repeat(3));

                chance --;
            }
        }else{
            System.out.println("Invalid selections!");
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
        int currentNum = number; // 3. Here we create a new integer variable is trying to process the data without change the original parameter.
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
