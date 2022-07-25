package com.ericsender.lotto;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Simulator {

    private static int[] copyRangeAndSort(final int[] arr, final int from, final int to) {
        final var output = Arrays.copyOfRange(arr, from, to);
        Arrays.sort(output);
        return output;
    }

    public long megaMillionsSim() {
        final var left = IntStream.rangeClosed(1, 70).toArray();
        final var right = IntStream.rangeClosed(1, 25).toArray();
        Stream.of(left, right).forEach(ArrayUtils::shuffle);
        // My quick picked numbers:
        final var leftWinner = new int[] {3, 29, 36, 50, 53}; //copyRangeAndSort(left, 0, 5);
        final var rightWinner = 13; //right[0];

        var leftTry = new int[0];
        var rightTry = 0;
        var tries = 0L;
        final var sw = StopWatch.createStarted();
        while (!Arrays.equals(leftWinner, leftTry) || rightTry != rightWinner) {
            Stream.of(left, right).forEach(ArrayUtils::shuffle);
            leftTry = copyRangeAndSort(left, 0, 5);
            rightTry = right[0];
            if (tries++ % 100_000_000L == 0) System.out.printf("So far tries: %,d (trying for %s:%d - time %s)%n",
                    tries - (tries > 1 ? 1 : 0), Arrays.toString(leftWinner), rightWinner, sw);
        }

        System.out.printf("Get a winner matching %s:%d (%s:%d) in %,d tries in time %s.%n",
                Arrays.toString(leftWinner), rightWinner, Arrays.toString(leftTry), rightTry, tries, sw);
        return tries;
    }

    public static void main(String[] args) {
        final var sw = StopWatch.createStarted();
        final var tries = new Simulator().megaMillionsSim();
        System.out.printf("Program completed in time %s (total draws till winner: %,d) %n", sw, tries);
    }
}
