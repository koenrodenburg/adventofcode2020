package day18.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Stack;

public class Day18Part2 {
    private static final String INPUT_PATH = "src/main/resources/day18/input.txt";

    public static void main(String[] args) throws IOException {
        long sum = Files.readAllLines(Path.of(INPUT_PATH)).stream()
                .mapToLong(Day18Part2::solve)
                .sum();

        System.out.println(sum);
    }

    private static long solve(String line) {
        Stack<StringBuilder> outerClauses = new Stack<>();

        StringBuilder currentClause = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            char character = line.charAt(i);
            if (character == '(') {
                outerClauses.push(currentClause);
                currentClause = new StringBuilder();
            } else if (character == ')') {
                String innerClause = currentClause.toString();
                String innerResult = calc(innerClause).toString();
                currentClause = outerClauses.pop();
                currentClause.append(innerResult);
            } else if (character != ' ') {
                currentClause.append(character);
            }
        }
        return calc(currentClause.toString());
    }

    public static Long calc(String clause) {
        return Arrays.stream(clause.split("\\*"))
                .mapToLong(Day18Part2::sum)
                .reduce(1, (a, b) -> a * b);
    }

    private static long sum(String clause) {
        return Arrays.stream(clause.split("\\+"))
                .mapToLong(Long::parseLong)
                .sum();
    }
}