package day2.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day2Part2 {
    private static final String INPUT_PATH = "src/main/resources/day2/input.txt";

    public static void main(String[] args) throws IOException {
        long count = Files.readAllLines(Path.of(INPUT_PATH)).stream()
                .map(Day2Part2::passwordFromString)
                .filter(Password::isValid)
                .count();

        System.out.println(count);
    }

    public static Password passwordFromString(String passwordString) {
        int indexOfDash = passwordString.indexOf("-");
        int indexOfFirstSpace = passwordString.indexOf(" ");
        int indexOfColon = passwordString.indexOf(":");

        int pos1 = Integer.parseInt(passwordString.substring(0,indexOfDash));
        int pos2 = Integer.parseInt(passwordString.substring(indexOfDash+1, indexOfFirstSpace));
        char character = passwordString.charAt(indexOfFirstSpace+1);
        String password = passwordString.substring(indexOfColon+2);

        return new Password(character, pos1, pos2, password);
    }
}
