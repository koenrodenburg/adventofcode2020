package day7.part1;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Data
@RequiredArgsConstructor
public class Bag {
    private final String color;
    private Map<String, Bag> bags = new HashMap<>();

    public void add(Bag bag) {
        bags.put(bag.color, bag);
    }

    public boolean canContain(String wantedColor) {
        if (bags.containsKey(wantedColor)) {
            return true;
        }

        Optional<Bag> any = bags.values().stream()
                .filter(bag -> bag.canContain(wantedColor))
                .findAny();

        return any.isPresent();
    }
}
