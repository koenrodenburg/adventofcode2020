package day13.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13Part1 {
    private static final String INPUT_PATH = "src/main/resources/day13/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(INPUT_PATH));

        int earliestDeparture = Integer.parseInt(lines.get(0));
        String[] busStrings = lines.get(1).split(",");

        List<Integer> busInts = Arrays.stream(busStrings)
                .filter(bus -> !bus.equals("x"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        int bestBusID = -1;
        int shortestWaitTime = Integer.MAX_VALUE;
        for (int busID : busInts) {
            int loops = (int) Math.ceil((earliestDeparture * 1.0) / busID);
            int waitTime = (loops * busID) - earliestDeparture;
            if (waitTime < shortestWaitTime) {
                shortestWaitTime = waitTime;
                bestBusID = busID;
            }
        }

        int result = bestBusID * shortestWaitTime;

        System.out.println(result);
    }
}
