package adventofcode;

import java.io.IOException;
import java.util.List;

public class Day25 {

    private static int currentPart = 0;

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //282
        boolean runPart2 = true; //3445
        if (runPart1) {
            currentPart = 1;
            TimeUtil.startClock(1);
            partOne();
            TimeUtil.time();
        }
        if (runPart2) {
            currentPart = 2;
            TimeUtil.startClock(2);
            partTwo();
            TimeUtil.time();
        }
    }


    public static void partOne() throws IOException {
        List<Integer> input = InputUtil.readFileAsIntList("day25/input.txt");

        long cardPublic = input.get(0);
        long doorPublic = input.get(1);

        //start with 1
        long valCard = 1L;
        long valDoor = 1L;
        int subjectNumber = 7;
        int divisor = 20201227;

        int loopsCard = -1;
        int loopsDoor = -1;
        int i = 1;
        while (loopsCard == -1 || loopsDoor == -1) {
            //Set the value to itself multiplied by the subject number.
            //Set the value to the remainder after dividing the value by 20201227.
            valCard = valCard * subjectNumber;
            valDoor = valDoor * subjectNumber;

            valCard = valCard % divisor;
            valDoor = valDoor % divisor;

            if (valCard == cardPublic) {
                loopsCard = i;
            }
            if (valDoor == doorPublic) {
                loopsDoor = i;
            }
            i++;
        }

        //Transforming the subject number of (the door's public key)
        // with a loop size of (the card's loop size) produces the encryption key

        long privKey = 1;
        for (int j = 1; j <= loopsCard; j++) {
            //Set the value to itself multiplied by the subject number.
            privKey = privKey * doorPublic;
            //Set the value to the remainder after dividing the value by 20201227.
            privKey = privKey % divisor;
        }


        System.out.println("Part 1 Answer: " + privKey);
    }


    public static void partTwo() throws IOException {
    }

}
