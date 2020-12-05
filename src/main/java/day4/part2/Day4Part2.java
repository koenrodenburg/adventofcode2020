package day4.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day4Part2 {
    private static final String INPUT_PATH = "src/main/resources/day4/input.txt";

    private static final List<Passport> PASSPORTS = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Passport passport = new Passport();
        PASSPORTS.add(passport);
        for(String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            if(line.equals("")) { // We're done with the current passport
                passport = new Passport();
                PASSPORTS.add(passport);
            } else { // We're not done yet, keep adding info
                handleInfoLine(line, passport);
            }
        }

        long count = PASSPORTS.stream()
                .filter(Passport::isValid)
                .count();

        System.out.println(count);
    }

    private static void handleInfoLine(String line, Passport passport) {
        for(String info : line.split("\\s")) {
            int colonIndex = info.indexOf(':');
            String key = info.substring(0, colonIndex);
            String value = info.substring(colonIndex+1);
            passport.addInfo(key,value);
        }
    }
}
