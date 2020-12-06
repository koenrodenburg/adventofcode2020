package day1.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day1Part1 {
    private static final String INPUT_PATH = "src/main/resources/day1/input.txt";

    public static void main(String[] args) throws IOException {

        List<Integer> numbers = Files.readAllLines(Path.of(INPUT_PATH)).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        long result = findResult(numbers);

        System.out.println(result);
    }

    private static long findResult(List<Integer> numbers) {
        for(int number : numbers) {
            int otherNumber = 2020 - number;
            if(numbers.contains(otherNumber)) {
                return (long) number * otherNumber;
            }
        }
        return 0;
    }
}
