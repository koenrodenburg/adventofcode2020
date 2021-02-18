package day16.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day16Part2 {
    private static final String INPUT_PATH = "src/main/resources/day16/input.txt";

    private static final List<Field> fields = new ArrayList<>();
    private static List<Integer> myTicket;
    private static List<List<Integer>> nearbyTickets = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        parseInput();

        nearbyTickets = nearbyTickets.stream()
                .filter(Day16Part2::isValidTicket)
                .collect(Collectors.toList());

        determineFieldIndices();

        long result = fields.stream()
                .filter(field -> field.getName().startsWith("departure"))
                .map(Field::getIndex)
                .mapToLong(index -> myTicket.get(index))
                .reduce(1L, (a, b) -> a * b);

        System.out.println(result);
    }

    private static boolean isValidTicket(List<Integer> ticket) {
        for(int number : ticket) {
            if(fields.stream().noneMatch(f -> f.isValidValue(number))) {
                return false;
            }
        }
        return true;
    }

    private static void determineFieldIndices() {
        while(fields.stream().anyMatch(field -> field.getIndex() == -1)) {
            fields.stream()
                    .filter(field -> field.getIndex() == -1)
                    .forEach(Day16Part2::tryToDetermineFieldIndex);
        }
    }

    private static void tryToDetermineFieldIndex(Field field) {
        List<Integer> takenIndices = fields.stream()
                .filter(doneField -> doneField.getIndex() != -1)
                .map(Field::getIndex)
                .collect(Collectors.toList());

        List<Integer> candidates = IntStream.range(0, nearbyTickets.get(0).size())
                .filter(index -> !takenIndices.contains(index))
                .filter(index -> isFieldValidAtIndex(field, index))
                .boxed().collect(Collectors.toList());

        if(candidates.size() == 1) {
            field.setIndex(candidates.get(0));
        }
    }

    private static boolean isFieldValidAtIndex(Field field, int index) {
        for(List<Integer> nearbyTicket : nearbyTickets) {
            if(!field.isValidValue(nearbyTicket.get(index))) {
                return false;
            }
        }
        return true;
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
                } else {
                    if(!line.equals("your ticket:")) {
                        myTicket = Arrays.stream(line.split(","))
                                .map(Integer::parseInt)
                                .collect(Collectors.toList());
                    }
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
