package day7.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Day7Part2 {
    private static final String INPUT_PATH = "src/main/resources/day7/input.txt";

    private static final Map<String, Bag> BAGS = new HashMap<>();

    private static final String wantedColor = "shiny gold";

    public static void main(String[] args) throws IOException {
        for(String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            readBag(line);
        }

        long count = countNumberOfBags(wantedColor) - 1;

        System.out.println(count);
    }

    private static int countNumberOfBags(String wantedColor) {
        int count = 1;

        Bag bag = BAGS.get(wantedColor);

        for(Map.Entry<String, Integer> containedBag : bag.getBags().entrySet()) {
            count += containedBag.getValue() * countNumberOfBags(containedBag.getKey());
        }

        return count;
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
                int number = Integer.parseInt(containedBagStr.substring(0,1));
                int indexOfFirstSpace = containedBagStr.indexOf(" ");
                int indexOfBag = containedBagStr.indexOf(" bag");
                String containedColor = containedBagStr.substring(indexOfFirstSpace + 1, indexOfBag);
                bag.add(containedColor, number);
            }
        }
    }
}
