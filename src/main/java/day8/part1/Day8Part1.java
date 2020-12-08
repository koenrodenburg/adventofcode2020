package day8.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day8Part1 {
    private static final String INPUT_PATH = "src/main/resources/day8/input.txt";

    private static int accumulator = 0;
    private static List<String> lines;
    private static final List<Integer> visitedLines = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        lines = Files.readAllLines(Path.of(INPUT_PATH));

        visitLine(0);

        System.out.println(accumulator);
    }

    private static void visitLine(int lineNumber) {
        if(visitedLines.contains(lineNumber)) {
            return;
        }

        String line = lines.get(lineNumber);

        int nextLineNumber = -1;
        int argument = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
        if(line.startsWith("nop")) {
            nextLineNumber = lineNumber + 1;
        } else if(line.startsWith("acc")) {
            accumulator += argument;
            nextLineNumber = lineNumber + 1;
        } else if(line.startsWith("jmp")) {
            nextLineNumber = lineNumber + argument;
        }

        visitedLines.add(lineNumber);
        visitLine(nextLineNumber);
    }
}
