package day1.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Day1Part2 {
    private static final String INPUT_PATH = "src/main/resources/day1/input.txt";

    public static void main(String[] args) throws IOException {

        List<Integer> numbers = Files.readAllLines(Path.of(INPUT_PATH)).stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        long result = findResult(numbers);

        System.out.println(result);
    }

    private static long findResult(List<Integer> numbers) {
        for(int number1 : numbers) {
            int remainder = 2020 - number1;
            for(int number2 : numbers) {
                if(number2 != number1) {
                    int number3 = remainder - number2;
                    if(number3 != number2 && number3 != number1 && numbers.contains(number3)) {
                        return (long) number1 * number2 * number3;
                    }
                }
            }
        }
        return 0;
    }

}
