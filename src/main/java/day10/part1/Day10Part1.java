package day10.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day10Part1 {
    private static final String INPUT_PATH = "src/main/resources/day10/input.txt";

    public static void main(String[] args) throws IOException {
        List<Integer> inputs = Files.readAllLines(Path.of(INPUT_PATH))
                .stream()
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());

        inputs.add(0, 0); // outlet
        inputs.add(inputs.get(inputs.size() - 1) + 3); // adapter

        int ones = 0;
        int threes = 0;

        for (int i = 0; i < inputs.size() - 1; i++) {
            int diff = inputs.get(i + 1) - inputs.get(i);
            if (diff == 1) {
                ones++;
            } else if (diff == 3) {
                threes++;
            } else if (diff > 3) {
                System.out.println("Error, diff is " + diff);
            }
        }

        System.out.println("Ones: " + ones + ", threes: " + threes);

        int result = ones * threes;

        System.out.println("Result: " + result);
    }
}
