package day5.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Day5Part2 {
    private static final String INPUT_PATH = "src/main/resources/day5/input.txt";

    public static void main(String[] args) throws IOException {
        List<Integer> seatIds = new ArrayList<>();

        for (String boardingPass : Files.readAllLines(Path.of(INPUT_PATH))) {
            String rowString = boardingPass.substring(0,7);
            int rowNumber = extractRowNumber(rowString);

            String seatString = boardingPass.substring(7);
            int seatNumber = extractSeatNumber(seatString);

            int seatID = (rowNumber * 8) + seatNumber;

            seatIds.add(seatID);
        }

        int mySeat = findSeat(seatIds);

        System.out.println(mySeat);
    }

    private static int findSeat(List<Integer> seatIds) {
        Collections.sort(seatIds);

        for (int i = 0; i < seatIds.size(); i++) {
            if( seatIds.get(i+1) - seatIds.get(i) > 1 ) {
                return seatIds.get(i) + 1;
            }
        }

        return 0;
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
