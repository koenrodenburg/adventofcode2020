package day15.part2;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Day15Part2 {
    private static final String input = "5,2,8,16,18,0,1";

    public static void main(String[] args) {
        String[] inputStrings = input.split(",");

        Map<Long, Long> numbers = new HashMap<>(); // key = number spoken, value = index
        IntStream.range(0, inputStrings.length-1)
                .forEach(index -> numbers.put(Long.parseLong(inputStrings[index]), (long) index));
        long currentNumber = Long.parseLong(inputStrings[inputStrings.length-1]);

        long nextNumber;
        for (long i = inputStrings.length; i < 30_000_000; i++) {
            if(i%1000 == 0) {
                System.out.println("i is " + i);
            }
            if(!numbers.containsKey(currentNumber)) {
                nextNumber = 0;
            } else {
                nextNumber = i - 1 - numbers.get(currentNumber);
            }
            numbers.put(currentNumber, i-1);
            currentNumber = nextNumber;
        }

        System.out.println(currentNumber);
    }
}
