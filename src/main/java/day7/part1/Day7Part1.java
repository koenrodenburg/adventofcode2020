package day7.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Day7Part1 {
    private static final String INPUT_PATH = "src/main/resources/day7/input.txt";

    private static final Map<String, Bag> BAGS = new HashMap<>();

    private static final String wantedColor = "shiny gold";

    public static void main(String[] args) throws IOException {
        for(String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            readBag(line);
        }

        long count = BAGS.values().stream()
                .filter(bag -> bag.canContain(wantedColor))
                .count();

        System.out.println(count);
    }

    private static void readBag(String line) {
        int colorSeparator = line.indexOf(" bags ");
        String color = line.substring(0, colorSeparator);

        Bag bag = BAGS.computeIfAbsent(color, Bag::new);

        String separator = " contain ";
        int separatorIndex = line.indexOf(separator);

        String containsStr = line.substring(separatorIndex + separator.length());
        if(!containsStr.contains("no other bags")) {
            for (String containedBagStr : containsStr.split(", ")) {
                int indexOfFirstSpace = containedBagStr.indexOf(" ");
                int indexOfBag = containedBagStr.indexOf(" bag");
                String containedColor = containedBagStr.substring(indexOfFirstSpace + 1, indexOfBag);
                Bag containedBag = BAGS.computeIfAbsent(containedColor, Bag::new);
                bag.add(containedBag);
            }
        }
    }
}
