package day14.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class Day14Part1 {
    private static final String INPUT_PATH = "src/main/resources/day14/input.txt";

    private static final String MASK_PREFIX = "mask = ";
    private static final String MEM_PREFIX = "mem[";

    private static String mask = "";

    private static final Map<Integer, Long> mem = new HashMap<>();

    public static void main(String[] args) throws IOException {
        for (String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            if (line.startsWith(MASK_PREFIX)) {
                mask = line.substring(MASK_PREFIX.length());
            } else if (line.startsWith(MEM_PREFIX)) {
                handleMemLine(line);
            }
        }

        long sum = mem.values().stream()
                .mapToLong(i -> i)
                .sum();

        System.out.println(sum);
    }

    private static void handleMemLine(String line) {
        int index = Integer.parseInt(line.substring(MEM_PREFIX.length(), line.indexOf("]")));
        int value = Integer.parseInt(line.substring(line.indexOf(" = ") + 3));

        String valueAsBinaryString = String.format("%36s", Integer.toBinaryString(value)).replace(' ', '0');

        String valueWithMaskApplied = applyMask(valueAsBinaryString, mask);

        mem.put(index, Long.parseLong(valueWithMaskApplied, 2));
    }

    private static String applyMask(String binaryString, String mask) {
        char[] binaryChars = binaryString.toCharArray();
        for (int i = 0; i < mask.length(); i++) {
            char maskBit = mask.charAt(i);
            if (maskBit != 'X') {
                binaryChars[i] = maskBit;
            }
        }
        return new String(binaryChars);
    }
}
