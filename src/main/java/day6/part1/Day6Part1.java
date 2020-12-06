package day6.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day6Part1 {
    private static final String INPUT_PATH = "src/main/resources/day6/input.txt";

    private static final List<Group> GROUPS = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        Group group = new Group();
        GROUPS.add(group);
        for(String person : Files.readAllLines(Path.of(INPUT_PATH))) {
            if(person.equals("")) { // We're done with the current group
                group = new Group();
                GROUPS.add(group);
            } else { // We're not done yet, keep adding info
                group.addPerson(person);
            }
        }

        int sum = GROUPS.stream()
                .mapToInt(Group::getCount)
                .sum();

        System.out.println(sum);
    }
}
