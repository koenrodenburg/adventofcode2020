package day8.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Day8Part2 {
    private static final String INPUT_PATH = "src/main/resources/day8/input.txt";

    private static int accumulator = 0;
    private static List<String> lines;
    private static List<Integer> visitedLines = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        lines = Files.readAllLines(Path.of(INPUT_PATH));

        for (int flipLine = 0; flipLine < lines.size(); flipLine++) {
            accumulator = 0;
            visitedLines = new ArrayList<>();
            if( visitLine(0, flipLine) ) {
                System.out.println("Terminated! Accumulator: " + accumulator);
                break;
            } else {
                System.out.println("Tried " + flipLine + ". Looping..");
            }
        }

    }

    private static boolean visitLine(int lineNumber, int flipLine) {
        if(visitedLines.contains(lineNumber)) {
            return false;
        }
        if(lineNumber == lines.size()) {
            return true;
        }

        String line = lines.get(lineNumber);

        if(lineNumber == flipLine) {
            if(line.startsWith("nop")) {
                line = line.replace("nop", "jmp");
            } else if(line.startsWith("jmp")) {
                line = line.replace("jmp", "nop");
            }
        }

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
        return visitLine(nextLineNumber, flipLine);
    }
}
