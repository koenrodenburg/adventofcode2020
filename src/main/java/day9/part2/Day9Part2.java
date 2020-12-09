package day9.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day9Part2 {
    private static final String INPUT_PATH = "src/main/resources/day9/input.txt";

    private static List<Long> inputs;
    private static final int PREAMBLE = 25;

    public static void main(String[] args) throws IOException {
        inputs = Files.readAllLines(Path.of(INPUT_PATH)).stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        // Find invalid number
        long invalidNumber = 0;
        for (int i = PREAMBLE; i < inputs.size(); i++) {
            if (!isValid(i)) {
                invalidNumber = inputs.get(i);
                break;
            }
        }

        List<Long> contiguousNumbers = findContiguousNumbers(invalidNumber);

        long smallest = contiguousNumbers.stream()
                .mapToLong(n -> n)
                .min().orElse(-1);
        long largest = contiguousNumbers.stream()
                .mapToLong(n -> n)
                .max().orElse(-1);

        long sum = smallest + largest;
        System.out.println(sum);

    }

    private static List<Long> findContiguousNumbers(final long sum) {
        for (int i = 0; i < inputs.size(); i++) {
            long remainder = sum - inputs.get(i);
            int currentIndex = i;
            while (remainder > 0) {
                remainder -= inputs.get(++currentIndex);
            }
            // Found solution
            if (remainder == 0) {
                return inputs.subList(i, currentIndex);
            }
        }
        return List.of();
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
