package day9.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day9Part1 {
    private static final String INPUT_PATH = "src/main/resources/day9/input.txt";

    private static List<Long> inputs;
    private static final int PREAMBLE = 25;

    public static void main(String[] args) throws IOException {
        inputs = Files.readAllLines(Path.of(INPUT_PATH)).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        for (int i = PREAMBLE; i < inputs.size(); i++) {
            if (!isValid(i)) {
                System.out.println(inputs.get(i));
                break;
            }
        }
    }

    private static boolean isValid(int index) {
        long number = inputs.get(index);

        List<Long> previousNumbers = inputs.subList(index - PREAMBLE, index);

        return preambleSumsUpToNumber(previousNumbers, number);
    }

    private static boolean preambleSumsUpToNumber(List<Long> previousNumbers, long number) {
        for (long previousNumber : previousNumbers) {
            long remainder = number - previousNumber;
            if (remainder != previousNumber && previousNumbers.contains(remainder)) {
                return true;
            }
        }
        return false;
    }
}
