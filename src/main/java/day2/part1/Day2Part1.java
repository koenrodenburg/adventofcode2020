package day2.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day2Part1 {
    private static final String INPUT_PATH = "src/main/resources/day2/input.txt";

    public static void main(String[] args) throws IOException {
        long count = Files.readAllLines(Path.of(INPUT_PATH)).stream()
                .map(Day2Part1::passwordFromString)
                .filter(Password::isValid)
                .count();

        System.out.println(count);
    }

    public static Password passwordFromString(String passwordString) {
        int indexOfDash = passwordString.indexOf("-");
        int indexOfFirstSpace = passwordString.indexOf(" ");
        int indexOfColon = passwordString.indexOf(":");

        int minOccurrences = Integer.parseInt(passwordString.substring(0,indexOfDash));
        int maxOccurrences = Integer.parseInt(passwordString.substring(indexOfDash+1, indexOfFirstSpace));
        char character = passwordString.charAt(indexOfFirstSpace+1);
        String password = passwordString.substring(indexOfColon+2);

        return new Password(character, minOccurrences, maxOccurrences, password);
    }
}
