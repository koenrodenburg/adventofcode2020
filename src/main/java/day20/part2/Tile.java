package day20.part2;

import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class Tile {
    private String id;

    // Indexed by column (x), then row (y)
    // . = false, # = true
    private List<List<Boolean>> pixels = new ArrayList<>();

    public void orientateAsTopLeft(List<Tile> edges) {
        for (int i = 0; i < 8; i++) {
            if (getOrientatedMatchingTileToTheRight(edges).isPresent() && getOrientatedMatchingTileToTheBottom(edges).isPresent()) {
                return;
            }
            rotate90DegreesToTheRight();
            if (i == 3) {
                flip();
            }
        }
    }

    public Optional<Tile> getOrientatedMatchingTileToTheRight(List<Tile> options) {
        for (Tile option : options) {
            for (int i = 0; i < 4; i++) {
                if (this.getRightSide().equals(option.getLeftSide())) {
                    return Optional.of(option);
                }
                option.rotate90DegreesToTheRight();
            }
            option.flip();
            for (int i = 0; i < 4; i++) {
                if (this.getRightSide().equals(option.getLeftSide())) {
                    return Optional.of(option);
                }
                option.rotate90DegreesToTheRight();
            }
        }
        return Optional.empty();
    }

    public Optional<Tile> getOrientatedMatchingTileToTheBottom(List<Tile> options) {
        for (Tile option : options) {
            for (int i = 0; i < 4; i++) {
                if (this.getBottomSide().equals(option.getTopSide())) {
                    return Optional.of(option);
                }
                option.rotate90DegreesToTheRight();
            }
            option.flip();
            for (int i = 0; i < 4; i++) {
                if (this.getBottomSide().equals(option.getTopSide())) {
                    return Optional.of(option);
                }
                option.rotate90DegreesToTheRight();
            }
        }
        return Optional.empty();
    }

    public Optional<Tile> getOrientatedMatchingTileToTheLeft(List<Tile> options) {
        for (Tile option : options) {
            for (int i = 0; i < 4; i++) {
                if (this.getLeftSide().equals(option.getRightSide())) {
                    return Optional.of(option);
                }
                option.rotate90DegreesToTheRight();
            }
            option.flip();
            for (int i = 0; i < 4; i++) {
                if (this.getLeftSide().equals(option.getRightSide())) {
                    return Optional.of(option);
                }
                option.rotate90DegreesToTheRight();
            }
        }
        return Optional.empty();
    }

    private void rotate90DegreesToTheRight() {
        List<List<Boolean>> rotatedPixels = new ArrayList<>();

        for (int i = 0; i < pixels.size(); i++) {
            int finalI = i;
            List<Boolean> rotatedRow = pixels.stream()
                    .map(row -> row.get(finalI))
                    .collect(Collectors.toList());
            Collections.reverse(rotatedRow);
            rotatedPixels.add(rotatedRow);
        }

        pixels = rotatedPixels;
    }

    private void flip() {
        Collections.reverse(pixels);
    }

    public boolean isCorner(List<Tile> tiles) {
        return getMatchingTilesInAnyOrientation(tiles).size() == 2;
    }

    public boolean isEdge(List<Tile> tiles) {
        return getMatchingTilesInAnyOrientation(tiles).size() == 3;
    }

    private List<Tile> getMatchingTilesInAnyOrientation(List<Tile> tiles) {
        return tiles.stream()
                .filter(tile -> !tile.equals(this))
                .filter(tile -> tile.matchesInAnyOrientation(this))
                .collect(Collectors.toList());
    }

    private boolean matchesInAnyOrientation(Tile other) {
        return matchesUprightOrReversed(this.getLeftSide(), other.getLeftSide())
                || matchesUprightOrReversed(this.getLeftSide(), other.getRightSide())
                || matchesUprightOrReversed(this.getLeftSide(), other.getTopSide())
                || matchesUprightOrReversed(this.getLeftSide(), other.getBottomSide())
                || matchesUprightOrReversed(this.getRightSide(), other.getLeftSide())
                || matchesUprightOrReversed(this.getRightSide(), other.getRightSide())
                || matchesUprightOrReversed(this.getRightSide(), other.getTopSide())
                || matchesUprightOrReversed(this.getRightSide(), other.getBottomSide())
                || matchesUprightOrReversed(this.getTopSide(), other.getLeftSide())
                || matchesUprightOrReversed(this.getTopSide(), other.getRightSide())
                || matchesUprightOrReversed(this.getTopSide(), other.getTopSide())
                || matchesUprightOrReversed(this.getTopSide(), other.getBottomSide())
                || matchesUprightOrReversed(this.getBottomSide(), other.getLeftSide())
                || matchesUprightOrReversed(this.getBottomSide(), other.getRightSide())
                || matchesUprightOrReversed(this.getBottomSide(), other.getTopSide())
                || matchesUprightOrReversed(this.getBottomSide(), other.getBottomSide());
    }

    private boolean matchesUprightOrReversed(List<Boolean> sideA, List<Boolean> sideB) {
        ArrayList<Boolean> reversedSideA = new ArrayList<>(sideA);
        Collections.reverse(reversedSideA);
        return sideA.equals(sideB) || reversedSideA.equals(sideB);
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
        return new ArrayList<>(this.pixels.get(0));
    }

    private List<Boolean> getBottomSide() {
        return new ArrayList<>(this.pixels.get(pixels.size() - 1));
    }

    public void addRow(String rowString) {
        List<Boolean> row = new ArrayList<>();
        for (char currentChar : rowString.toCharArray()) {
            row.add(currentChar == '#');
        }
        pixels.add(row);
    }
}
