package ictgradschool.industry.miniproject;
import java.util.*;

public class BullsAndCows {
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

        int userInputSecret = 0;
        // verifying sanity for input.
        while(true){
            System.out.println("Please enter your secret code: ");
            String str1 = input.nextLine().trim();
            if(str1.length() ==4){
                try{
                    userInputSecret = Integer.parseInt(str1);
                    break;
                }catch(NumberFormatException e){
                    System.out.println("Invalid input!(4 digit numbers)Try again!");
                }
            }else{
                System.out.println("Invalid length!(4 digit numbers)Try again!");
            }
        }
        int[] userSecret = new int[4];
        getDigit(userSecret,userInputSecret);
        System.out.println("-".repeat(3));
        int chance = 7;
        while(chance > 0){
            int userInputGuess =0;
            while(true){
                System.out.print("You guess(" + chance + " chances remaining): ");
                String str2 = input.nextLine().trim();
                if(str2.length() ==4){
                    try{
                        userInputGuess = Integer.parseInt(str2);
                        break;
                    }catch(NumberFormatException e){
                        System.out.println("Invalid input!(4 digit numbers)Try again!");
                    }
                }else{
                    System.out.println("Invalid length!(4 digit numbers)Try again!");
                }
            }
            int[] userGuess = new int[4];
            getDigit(userGuess, userInputGuess);
            // reset the HashSet
            uniqueNum.clear();
            int[] sysGuess = new int[4];
            for(int i = 0; i < sysGuess.length; i++){
                int ran;
                do{
                    ran = random.nextInt(10);
                }while(!uniqueNum.add(ran));
                sysGuess[i] = ran;
            }
            System.out.println(check(userGuess, sysSecret));
            System.out.println(" ");
            System.out.print("Computer guess: " );
            for (int i : sysGuess) {
                System.out.print(i);
            }
            System.out.println("");
            System.out.println(check(userSecret, sysGuess));
            System.out.println("-".repeat(3));
            chance--;
            if (chance == 0) {
                System.out.println("Game over. Draw! Secret code: " + Arrays.toString(sysSecret));
            }
        }
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
        return "Result: " + bulls + "bull(s) and " + cows + "cow(s)";
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
