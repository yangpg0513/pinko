package com.pinko.leecode;

public class Lee_9_isPalindrome {
    public static void main(String[] args) {
        boolean is = isPalindrome(121);
        System.out.println(is);
    }

    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        int tmp = x;
        int n = 0;
        while (x > 0) {
            n = n * 10 + x % 10;
            x = x / 10;
        }
        return tmp == n;
    }
}
