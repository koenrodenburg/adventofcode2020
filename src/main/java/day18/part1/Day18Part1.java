package day18.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Stack;

public class Day18Part1 {
    private static final String INPUT_PATH = "src/main/resources/day18/input.txt";

    public static void main(String[] args) throws IOException {
        long sum = Files.readAllLines(Path.of(INPUT_PATH)).stream()
                .mapToLong(Day18Part1::solve)
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
            } else {
                currentClause.append(character);
            }
        }
        return calc(currentClause.toString());
    }

    public static Long calc(String clause){
        String[] c = clause.split(" ");
        long leftHand = Long.parseLong(c[0]);
        for (int i = 2; i < c.length; i = i+2){
            long rightHand = Long.parseLong(c[i]);
            switch (c[i - 1]) {
                case "+" -> leftHand += rightHand;
                case "*" -> leftHand *= rightHand;
            }
        }
        return leftHand;
    }
}