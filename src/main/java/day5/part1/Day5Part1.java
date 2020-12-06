package day5.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day5Part1 {
    private static final String INPUT_PATH = "src/main/resources/day5/input.txt";

    public static void main(String[] args) throws IOException {
        int highestSeatID = -1;

        for (String boardingPass : Files.readAllLines(Path.of(INPUT_PATH))) {
            String rowString = boardingPass.substring(0,7);
            int rowNumber = extractRowNumber(rowString);

            String seatString = boardingPass.substring(7);
            int seatNumber = extractSeatNumber(seatString);

            int seatID = (rowNumber * 8) + seatNumber;

            if(seatID > highestSeatID) {
                highestSeatID = seatID;
            }
        }

        System.out.println(highestSeatID);

    }

    private static int extractRowNumber(String rowString) {
        rowString = rowString.replace('F', '0');
        rowString = rowString.replace('B', '1');

        return binaryStringToInt(rowString);
    }

    private static int extractSeatNumber(String seatString) {
        seatString = seatString.replace('R', '1');
        seatString = seatString.replace('L', '0');

        return binaryStringToInt(seatString);
    }

    private static int binaryStringToInt(String binary) {
        return Integer.parseInt(binary, 2);
    }
}
