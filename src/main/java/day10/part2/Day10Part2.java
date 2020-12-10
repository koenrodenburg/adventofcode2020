package day10.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day10Part2 {
    private static final String INPUT_PATH = "src/main/resources/day10/input.txt";

    private static List<Integer> inputs;
    private static final Map<Integer, Long> permutations = new HashMap<>();

    public static void main(String[] args) throws IOException {
        inputs = Files.readAllLines(Path.of(INPUT_PATH))
                .stream()
                .map(Integer::parseInt)
                .sorted()
                .collect(Collectors.toList());

        inputs.add(0, 0); // outlet
        inputs.add(inputs.get(inputs.size() - 1) + 3); // adapter

        long permutations = countPermutations(0);

        System.out.println(permutations);

    }

    private static long countPermutations(int currentPosition) {
        if (currentPosition == inputs.size() - 1) {
            return 1;
        }
        if (permutations.containsKey(currentPosition)) {
            return permutations.get(currentPosition);
        }

        long noOfPermutations = 0;
        for (int i = currentPosition + 1; i < inputs.size(); i++) {
            if (inputs.get(i) - inputs.get(currentPosition) <= 3) {
                noOfPermutations += countPermutations(i);
            }
        }

        permutations.put(currentPosition, noOfPermutations);

        return noOfPermutations;
    }
}
