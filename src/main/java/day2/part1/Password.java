package day2.part1;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Password {
    private char character;
    private int minOccurrences;
    private int maxOccurrences;
    private String password;

    public boolean isValid() {
        long occurrences = password.chars()
                .filter(ch -> ch == character)
                .count();

        return occurrences >= minOccurrences && occurrences <= maxOccurrences;
    }
}
