package day6.part2;

import java.util.ArrayList;
import java.util.List;

public class Group {
    List<Character> chars;

    public void addPerson(String person) {
        List<Character> newChars = new ArrayList<>();

        person.chars()
                .forEach(ch -> {
                    if(chars == null || chars.contains((char) ch)) {
                        newChars.add((char) ch);
                    }
                });

        chars = newChars;
    }

    public int getCount() {
        return chars.size();
    }
}
