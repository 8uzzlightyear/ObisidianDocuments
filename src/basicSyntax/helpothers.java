package basicSyntax;

import java.util.Scanner;
import java.util.function.DoubleToLongFunction;

public class helpothers {
    public static void main(String[] args) {
        Scanner myScanner = new Scanner(System.in);
        System.out.println("please enter the score:");
        double score = myScanner.nextDouble();
        if (score > 8){
            System.out.println("please enter the gender:");
            char gender = myScanner.next().charAt(0);
            if (gender == '男'){
                System.out.println("enter the man's final");
            }else if (gender == '女'){
                System.out.println("enter the woman's final");
            }else{
                System.out.println("输入性别有误");
            }

        }
    }
}
