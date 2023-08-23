package Leetcode;

public class Test{
    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.myAtoi("  -42"));
    }
}
class Solution {
    public int myAtoi(String s) {
        if(s.isEmpty()) {
            return 0;
        }
        int index = 0;
        while (index < s.length() && s.charAt(index) == ' ') {
            index ++;
        }

        int positive = 1;
        boolean getNum = false;
        int res = 0;
        if (index < s.length() && (s.charAt(index) == '-' ||s.charAt(index) == '+')) {
            if (s.charAt(index) == '-') {
                positive = -1;
            }
            index ++;
        }

        while (index < s.length() && s.charAt(index) <= '9' && s.charAt(index) >= '0') {
            int digit = (int)s.charAt(index) - 48;
            if (positive == -1) {
                if (res < (Integer.MIN_VALUE + digit) / 10){
                    return Integer.MIN_VALUE;
                }
                res = res * 10 - digit;
            }
            if (positive == 1) {
                if (res > (Integer.MAX_VALUE - digit) / 10) {
                    return Integer.MAX_VALUE;
                }
                res = res * 10 + digit;
            }
            index ++;
            System.out.print(positive +  " ");
            System.out.print(res + " ");

        }
        return res;
    }
}