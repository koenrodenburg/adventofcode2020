package day2.part2;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Password {
    private char character;
    private int pos1;
    private int pos2;
    private String password;

    public boolean isValid() {
        boolean pos1Valid = password.charAt(pos1-1) == character;
        boolean pos2Valid = password.charAt(pos2-1) == character;

        return (pos1Valid && !pos2Valid) || (!pos1Valid && pos2Valid);
    }
}
