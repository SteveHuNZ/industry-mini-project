package ictgradschool.industry.miniproject;
import java.util.*;

public class BullsAndCows {
    public static void start(){
        Random random = new Random();
        int[] sysSecret = new int[4];
        int[] sysGuess = new int[4];
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
//        set the guessing words  for  our opponent :computer
        for(int i = 0; i < sysGuess.length; i++){
            int ran;
            do{
                ran = random.nextInt(10);
            }while(!uniqueNum.add(ran));
            sysGuess[i] = ran;
        }

//get user's input number '
        Scanner input = new Scanner(System.in);
        System.out.println("Please enter your secret code: ");
        String userSecret = input.nextLine();
        System.out.println("-".repeat(3));
        int chance = 7;
        while(chance > 0){
            System.out.print("You guess: ");
            int fourDigit = input.nextInt();
            int[] userGuess = new int[4];
            userGuess[0] = fourDigit / 1000;
            userGuess[1] = fourDigit / 100 % 10;
            userGuess[2] = fourDigit / 10 % 10;
            userGuess[3] = fourDigit % 10;
            int bulls = 0;
            int cows = 0;
//            using for loop to check how many cows or bulls
            for (int i = 0; i < userGuess.length; i++) {
                for (int j = 0; j < sysSecret.length; j++) {
                    if (userGuess[i] == sysSecret[j]) {
                        cows++;
                    }
                    if (userGuess[i] == sysSecret[j] && i == j) {
                        bulls++;
                        cows--;
                    }
                }
            }
            System.out.printf("Result: %d bull(s) and %d cow(s)%n\n", bulls, cows);
            System.out.println("-".repeat(3));








            public static void main(String[] argus){
        start();
    }
}
