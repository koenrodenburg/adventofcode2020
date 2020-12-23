package day23.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Day23Part2 {
    private static final String INPUT = "389125467";

    private static final int noOfMoves = 10_000_000;

    private static List<Integer> cups;
    private static int currentCup;

    public static void main(String[] args) {
        parseInput();

        for (int i = 1; i <= noOfMoves; i++) {
            if (i % 1000 == 0) {
                System.out.println("Move " + i);
            }
            move();
        }

        int indexOfOne = cups.indexOf(1);

        int firstValue = cups.get(indexOfOne + 1);
        int secondValue = cups.get(indexOfOne + 2);

        long result = firstValue * secondValue;

        System.out.println(result);
    }

    private static void parseInput() {
        cups = new LinkedList<>();
        INPUT.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .map(Integer::parseInt)
                .forEach(cups::add);

        int highestCup = cups.stream()
                .mapToInt(i -> i)
                .max().orElse(0);

        for (int i = highestCup + 1; i <= 1_000_000; i++) {
            cups.add(i);
        }

        currentCup = cups.get(0);
    }

    private static void move() {
        // Pick up cups
        List<Integer> pickedUpCups = new ArrayList<>();
        pickedUpCups.add(cups.remove((cups.indexOf(currentCup) + 1) % cups.size()));
        pickedUpCups.add(cups.remove((cups.indexOf(currentCup) + 1) % cups.size()));
        pickedUpCups.add(cups.remove((cups.indexOf(currentCup) + 1) % cups.size()));

        // Determine destination cup
        int destinationCupIndex = -1;
        int destinationCupValue = currentCup;
        while (destinationCupIndex == -1) {
            destinationCupValue--;
            if (destinationCupValue == 0) {
                destinationCupValue = 9;
            }
            destinationCupIndex = cups.indexOf(destinationCupValue);
        }

        // Put the picked up cups back
        Collections.reverse(pickedUpCups);
        for (int cup : pickedUpCups) {
            cups.add(destinationCupIndex + 1, cup);
        }

        int currentCupIndex = (cups.indexOf(currentCup) + 1) % cups.size();
        currentCup = cups.get(currentCupIndex);
    }
}
