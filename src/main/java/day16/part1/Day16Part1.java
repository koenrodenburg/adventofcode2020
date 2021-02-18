package day16.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day16Part1 {
    private static final String INPUT_PATH = "src/main/resources/day16/input.txt";

    private static final List<Field> fields = new ArrayList<>();
    private static final List<List<Integer>> nearbyTickets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        parseInput();

        int sum = 0;
        for(List<Integer> nearbyTicket : nearbyTickets) {
            for(int number : nearbyTicket) {
                boolean isValidForAnyField = fields.stream()
                        .anyMatch(f -> f.isValidValue(number));
                if(!isValidForAnyField) {
                    sum += number;
                }
            }
        }

        System.out.println(sum);
    }

    private static void parseInput() throws IOException {
        boolean readingFields = true;
        boolean readingMyTicket = false;
        for(String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            if(readingFields) {
                if(line.equals("")) {
                    readingFields = false;
                    readingMyTicket = true;
                } else {
                    int indexOfColon = line.indexOf(": ");
                    int indexOfFirstNumber = line.indexOf(": ") + 2;
                    int indexOfFirstDash = line.indexOf("-");
                    int indexOfOr = line.indexOf(" or ");
                    int indexOfSecondDash = line.indexOf("-", indexOfFirstDash + 1);

                    String name = line.substring(0, indexOfColon);
                    int rangeOneStart = Integer.parseInt(line.substring(indexOfFirstNumber, indexOfFirstDash));
                    int rangeOneEnd = Integer.parseInt(line.substring(indexOfFirstDash + 1, indexOfOr));
                    int rangeTwoStart = Integer.parseInt(line.substring(indexOfOr + 4, indexOfSecondDash));
                    int rangeTwoEnd = Integer.parseInt(line.substring(indexOfSecondDash + 1));

                    fields.add(new Field(name, rangeOneStart, rangeOneEnd, rangeTwoStart, rangeTwoEnd));
                }
            } else if(readingMyTicket) {
                if(line.equals("")) {
                    readingMyTicket = false;
                }
            } else {
                if(line.equals("nearby tickets:")) {
                    continue;
                }
                List<Integer> nearbyTicket = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .collect(Collectors.toList());

                nearbyTickets.add(nearbyTicket);
            }
        }
    }
}
