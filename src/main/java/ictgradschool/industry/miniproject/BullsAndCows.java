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




    public static void main(String[] argus){
        start();
    }
}
