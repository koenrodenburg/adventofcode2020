package day13.part2;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day13Part2 {
    private static final String INPUT_PATH = "src/main/resources/day13/input.txt";

    public static void main(String[] args) throws IOException {
        List<String> lines = Files.readAllLines(Path.of(INPUT_PATH));

        List<Integer> busIDs = Arrays.stream(lines.get(1).split(","))
                .map(bus -> bus.equals("x") ? "-1" : bus)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        BigInteger jump = BigInteger.valueOf(busIDs.get(0));
        BigInteger timestamp = BigInteger.ZERO;
        for (int i = 1; i < busIDs.size(); i++) {
            int busID = busIDs.get(i);
            if (busID > 0) {
                while (!(timestamp.add(BigInteger.valueOf(i))).mod(BigInteger.valueOf(busID)).equals(BigInteger.ZERO)) {
                    timestamp = timestamp.add(jump);
                }
                jump = jump.multiply(BigInteger.valueOf(busID));
            }
        }

        System.out.println(timestamp);
    }
}
