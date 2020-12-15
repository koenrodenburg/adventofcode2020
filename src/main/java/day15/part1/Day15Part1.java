package day15.part1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day15Part1 {
    private static final String input = "5,2,8,16,18,0,1";

    public static void main(String[] args) {
        List<Integer> list = Arrays.stream(input.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int currentNumber = list.remove(list.size() - 1);
        for (int i = list.size(); i < 2020; i++) {
            int lastIndexOf = list.lastIndexOf(currentNumber);
            list.add(currentNumber);
            if (lastIndexOf == -1) {
                currentNumber = 0;
            } else {
                currentNumber = i - lastIndexOf;
            }
        }

        System.out.println(list.get(2020 - 1));
    }
}
