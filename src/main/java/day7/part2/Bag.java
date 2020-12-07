package day7.part2;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@RequiredArgsConstructor
public class Bag {
    private final String color;
    private Map<String, Integer> bags = new HashMap<>();

    public void add(String containedColor, int number) {
        bags.put(containedColor, number);
    }
}
