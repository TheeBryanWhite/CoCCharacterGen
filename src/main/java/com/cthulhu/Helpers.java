package com.cthulhu;

import java.util.Random;

public class Helpers {

    // Depending on age, 2 or more stats may need to be modified by a single value divided among them
    public static int[] sumDivider(int parts, int howMuch) {

        int[] nums = new int[parts];
        int total = howMuch;

        Random rand = new Random();

        for (int i = 0; i < nums.length-1; i++) {

            nums[i] = rand.nextInt(total);
            total -= nums[i];

        }
        nums[nums.length - 1] = total;

        return nums;

    }

    public static int timesFive(int score) {

        int timesFive = score * 5;

        return timesFive;

    }
}
