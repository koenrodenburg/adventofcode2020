package day16.part1;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Field {
    String name;
    int rangeOneStart;
    int rangeOneEnd;
    int rangeTwoStart;
    int rangeTwoEnd;

    public boolean isValidValue(int value) {
        return (value >= rangeOneStart && value <= rangeOneEnd)
                || (value >= rangeTwoStart && value <= rangeTwoEnd);
    }
}
