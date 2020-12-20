package day20.part1;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class Tile {
    private String id;

    // Indexed by column (x), then row (y)
    // . = false, # = true
    private final List<List<Boolean>> pixels = new ArrayList<>();

    public void addRow(String rowString) {
        List<Boolean> row = new ArrayList<>();
        for (char currentChar : rowString.toCharArray()) {
            row.add(currentChar == '#');
        }
        pixels.add(row);
    }

    public boolean isCorner(List<Tile> tiles) {
        return tiles.stream()
                .filter(tile -> !tile.equals(this))
                .filter(tile -> tile.matches(this))
                .count() < 3;
    }

    private boolean matches(Tile other) {
        return matches(this.getLeftSide(), other.getLeftSide()) ||
                matches(this.getLeftSide(), other.getRightSide()) ||
                matches(this.getLeftSide(), other.getTopSide()) ||
                matches(this.getLeftSide(), other.getBottomSide()) ||
                matches(this.getRightSide(), other.getLeftSide()) ||
                matches(this.getRightSide(), other.getRightSide()) ||
                matches(this.getRightSide(), other.getTopSide()) ||
                matches(this.getRightSide(), other.getBottomSide()) ||
                matches(this.getTopSide(), other.getLeftSide()) ||
                matches(this.getTopSide(), other.getRightSide()) ||
                matches(this.getTopSide(), other.getTopSide()) ||
                matches(this.getTopSide(), other.getBottomSide()) ||
                matches(this.getBottomSide(), other.getLeftSide()) ||
                matches(this.getBottomSide(), other.getRightSide()) ||
                matches(this.getBottomSide(), other.getTopSide()) ||
                matches(this.getBottomSide(), other.getBottomSide());

    }

    private List<Boolean> getLeftSide() {
        return this.pixels.stream()
                .map(row -> row.get(0))
                .collect(Collectors.toList());
    }

    private List<Boolean> getRightSide() {
        return this.pixels.stream()
                .map(row -> row.get(row.size() - 1))
                .collect(Collectors.toList());
    }

    private List<Boolean> getTopSide() {
        return this.pixels.get(0);
    }

    private List<Boolean> getBottomSide() {
        return this.pixels.get(pixels.size() - 1);
    }

    private boolean matches(List<Boolean> sideA, List<Boolean> sideB) {
        ArrayList<Boolean> reversedSideA = new ArrayList<>(sideA);
        Collections.reverse(reversedSideA);
        return sideA.equals(sideB) || reversedSideA.equals(sideB);
    }
}
