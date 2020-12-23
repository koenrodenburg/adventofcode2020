package day23.part1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Day23Part1 {
    private static final String INPUT = "364289715";

    private static final int noOfMoves = 100;

    private static List<Integer> cups;
    private static int currentCup;

    public static void main(String[] args) {
        cups = INPUT.chars()
                .mapToObj(c -> (char) c)
                .map(String::valueOf)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        currentCup = cups.get(0);

        for (int i = 1; i <= noOfMoves; i++) {
            System.out.println("Before move " + i + ": " + cups.stream().map(String::valueOf).collect(Collectors.joining(", ")) + ". CurrentCup: " + currentCup );
            move();
        }
        System.out.println("Final: " + cups.stream().map(String::valueOf).collect(Collectors.joining(", ")) + ". CurrentCupIndex " + currentCup );


        String cupsString = cups.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());

        int indexOfOne = cupsString.indexOf('1');

        String firstHalf = cupsString.substring(indexOfOne + 1);
        String secondHalf = cupsString.substring(0, indexOfOne);

        String result = firstHalf + secondHalf;

        System.out.println(result);
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
