package day6.part1;

import java.util.ArrayList;
import java.util.List;

public class Group {
    List<Character> chars = new ArrayList<>();

    public void addPerson(String person) {
        person.chars().forEach(ch -> {
            if(!chars.contains((char) ch)) {
                chars.add((char) ch);
            }
        });

    }

    public int getCount() {
        return chars.size();
    }
}
