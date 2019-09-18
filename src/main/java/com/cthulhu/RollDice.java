package com.cthulhu;

import java.util.Random;

public class RollDice {

    public static int rollDSix(int numDice) {

        Random random = new Random();

        int i;
        int randomInteger = 0;
        int totalInt = 0;

        // Roll them bones! No zeroes, though
        for (i = 0; i < numDice; i++) {
            randomInteger = random.nextInt(6) + 1;

            totalInt = totalInt + randomInteger;
        }

        return totalInt;
    }
}
