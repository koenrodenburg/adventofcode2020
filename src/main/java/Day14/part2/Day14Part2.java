package Day14.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14Part2 {
    private static final String INPUT_PATH = "src/main/resources/day14/input.txt";

    private static final String MASK_PREFIX = "mask = ";
    private static final String MEM_PREFIX = "mem[";

    private static String mask = "";

    private static final Map<Long, Long> mem = new HashMap<>();

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
        long value = Integer.parseInt(line.substring(line.indexOf(" = ") + 3));

        String indexAsBinaryString = String.format("%36s", Integer.toBinaryString(index)).replace(' ', '0');
        applyMask(indexAsBinaryString, mask).stream()
                .map(maskedIndex -> String.join("", maskedIndex))
                .map(maskedIndex -> Long.parseLong(maskedIndex, 2))
                .forEach(maskedIndex -> mem.put(maskedIndex, value));
    }

    private static Set<String[]> applyMask(String binaryString, String mask) {
        Set<Integer> xIndices = new HashSet<>();
        char[] binaryChars = binaryString.toCharArray();
        for (int i = 0; i < mask.length(); i++) {
            char maskBit = mask.charAt(i);
            if (maskBit != '0') {
                binaryChars[i] = maskBit;
                if (maskBit == 'X') {
                    xIndices.add(i);
                }
            }
        }

        Set<String[]> permutations = new HashSet<>();
        permutations.add(new String(binaryChars).split(""));
        for (int xIndex : xIndices) {
            permutations = permutations.stream()
                    .flatMap(a -> {
                        String[] scenario0 = Arrays.stream(a).toArray(String[]::new);
                        scenario0[xIndex] = "0";
                        String[] scenario1 = a;
                        scenario1[xIndex] = "1";
                        return Stream.of(scenario0, scenario1);
                    })
                    .collect(Collectors.toSet());
        }

        return permutations;
    }
}
