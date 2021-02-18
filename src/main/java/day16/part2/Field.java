package day16.part2;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Field {
    final String name;
    final int rangeOneStart;
    final int rangeOneEnd;
    final int rangeTwoStart;
    final int rangeTwoEnd;
    int index = -1;

    public boolean isValidValue(int value) {
        return (value >= rangeOneStart && value <= rangeOneEnd)
                || (value >= rangeTwoStart && value <= rangeTwoEnd);
    }
}
