package com.pinko.leecode;

import lombok.val;

public class Lee_2_twoSum {
    public static void main(String[] args) {
        int[] nums = {1,2,3};
        int taget = 5;
        int[] ints = twoSum(nums, taget);
        System.out.println(ints);
    }
    public static int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    res[0] = i;
                    res[1] = j;
                    break;
                }
            }
        }
        return res;
    }
}
