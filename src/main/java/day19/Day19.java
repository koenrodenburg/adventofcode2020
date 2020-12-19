package day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day19 {
    private static final String INPUT_PATH_PART1 = "src/main/resources/day19/inputPart1.txt";
    private static final String INPUT_PATH_PART2 = "src/main/resources/day19/inputPart2.txt";

    private static Map<String, String> rules;
    private static List<String> messages;

    public static void main(String[] args) throws IOException {
        long part1 = solve(INPUT_PATH_PART1);
        System.out.println("Part 1: " + part1);

        long part2 = solve(INPUT_PATH_PART2);
        System.out.println("Part 2: " + part2);
    }

    private static long solve(String path) throws IOException {
        rules = new HashMap<>();
        messages = new ArrayList<>();

        readInput(path);

        expandRules();

        String rule0 = rules.get("0").replaceAll(" ", "");
        return messages.stream()
                .filter(message -> message.matches(rule0))
                .count();
    }

    private static void expandRules() {
        for (Map.Entry<String, String> rule : rules.entrySet()) {
            rule.setValue(expandRule(rule.getValue(), 0));
        }
    }

    private static String expandRule(String rule, int depth) {
        // To prevent infinite expansion, insert a hardcoded stop at some point
        if (depth > 12) {
            return "b";
        }

        String[] parts = rule.split(" ");
        for (int i = 0; i < parts.length; ++i) {
            if (parts[i].matches("\\d+")) {
                parts[i] = "(" + expandRule(rules.get(parts[i]), depth + 1) + ")";
            }
        }
        return String.join(" ", parts).replaceAll("\"", "");
    }

    private static void readInput(String path) throws IOException {
        for (String line : Files.readAllLines(Path.of(path))) {
            if (!line.isEmpty()) {
                if (line.contains(":")) { // It is a rule
                    String ruleId = line.substring(0, line.indexOf(": "));
                    String expansion = line.substring(line.indexOf(": ") + 2);
                    rules.put(ruleId, expansion);
                } else { // It is a message
                    messages.add(line);
                }
            }
        }
    }
}
