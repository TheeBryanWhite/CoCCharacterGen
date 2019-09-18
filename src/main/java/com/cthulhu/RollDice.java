package com.cthulhu;

import java.util.Random;

public class RollDice {

    public static int rollThemBones(int numDice, int sides, Boolean addSix) {

        Random random = new Random();

        int i;
        int randomInteger = 0;
        int totalInt = 0;

        // Roll them bones! No zeroes, though
        for (i = 0; i < numDice; i++) {
            randomInteger = random.nextInt(sides) + 1;

            totalInt = totalInt + randomInteger;
        }

        if (addSix == true) {
            totalInt += 6;
        }

        return totalInt;
    }

}
