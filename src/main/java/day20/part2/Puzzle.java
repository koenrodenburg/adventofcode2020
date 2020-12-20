package day20.part2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@AllArgsConstructor
public class Puzzle {
    private final Map<Integer, Map<Integer, Tile>> puzzle = new HashMap<>();

    private final List<Tile> corners;
    private final List<Tile> edges;
    private final List<Tile> rest;

    public Puzzle solve() {
        startWithTopLeftCorner();

        int tilesPerEdge = edges.size() / 4;

        fillInTopRow(tilesPerEdge);
        fillInRightColumn(tilesPerEdge);
        fillInBottomRow(tilesPerEdge);
        fillInLeftColumn(tilesPerEdge);
        for (int i = 1; i <= tilesPerEdge; i++) {
            fillInColumn(tilesPerEdge, i);
        }

        return this;
    }

    private void fillInColumn(int tilesPerEdge, int column) {
        Tile previousTile = puzzle.get(0).get(column);
        for (int i = 1; i <= tilesPerEdge; i++) {
            Optional<Tile> nextTile = previousTile.getOrientatedMatchingTileToTheBottom(rest);
            if (nextTile.isPresent()) {
                rest.remove(nextTile.get());
                addTile(i, column, nextTile.get());
                previousTile = nextTile.get();
            } else {
                throw new IllegalStateException("Couldn't find the next tile on column " + column + "?!");
            }
        }
    }

    private void fillInLeftColumn(int tilesPerEdge) {
        Tile previousTile = puzzle.get(0).get(0);

        // Find edge pieces
        for (int i = 1; i <= tilesPerEdge; i++) {
            Optional<Tile> nextTile = previousTile.getOrientatedMatchingTileToTheBottom(edges);
            if (nextTile.isPresent()) {
                edges.remove(nextTile.get());
                addTile(i, 0, nextTile.get());
                previousTile = nextTile.get();
            } else {
                throw new IllegalStateException("Couldn't find the next edge on the left column?!");
            }
        }
    }

    private void fillInBottomRow(int tilesPerEdge) {
        Tile previousTile = puzzle.get(tilesPerEdge + 1).get(tilesPerEdge + 1);

        // Find edge pieces
        for (int i = tilesPerEdge; i >= 1; i--) {
            Optional<Tile> nextTile = previousTile.getOrientatedMatchingTileToTheLeft(edges);
            if (nextTile.isPresent()) {
                edges.remove(nextTile.get());
                addTile(tilesPerEdge + 1, i, nextTile.get());
                previousTile = nextTile.get();
            } else {
                throw new IllegalStateException("Couldn't find the next edge on the right column?!");
            }
        }

        // Find top right corner
        Optional<Tile> bottomLeftCorner = previousTile.getOrientatedMatchingTileToTheLeft(corners);
        if (bottomLeftCorner.isPresent()) {
            corners.remove(bottomLeftCorner.get());
            addTile(tilesPerEdge + 1, 0, bottomLeftCorner.get());
        } else {
            throw new IllegalStateException("Couldn't find the bottom left corner?!");
        }
    }

    private void fillInRightColumn(int tilesPerEdge) {
        Tile previousTile = puzzle.get(0).get(tilesPerEdge + 1);

        // Find edge pieces
        for (int i = 1; i <= tilesPerEdge; i++) {
            Optional<Tile> nextTile = previousTile.getOrientatedMatchingTileToTheBottom(edges);
            if (nextTile.isPresent()) {
                edges.remove(nextTile.get());
                addTile(i, tilesPerEdge + 1, nextTile.get());
                previousTile = nextTile.get();
            } else {
                throw new IllegalStateException("Couldn't find the next edge on the right column?!");
            }
        }

        // Find top right corner
        Optional<Tile> bottomRightCorner = previousTile.getOrientatedMatchingTileToTheBottom(corners);
        if (bottomRightCorner.isPresent()) {
            corners.remove(bottomRightCorner.get());
            addTile(tilesPerEdge + 1, tilesPerEdge + 1, bottomRightCorner.get());
        } else {
            throw new IllegalStateException("Couldn't find the bottom right corner?!");
        }
    }

    private void fillInTopRow(int tilesPerEdge) {
        Tile previousTile = puzzle.get(0).get(0);

        // Find edge pieces
        for (int i = 1; i <= tilesPerEdge; i++) {
            Optional<Tile> nextTile = previousTile.getOrientatedMatchingTileToTheRight(edges);
            if (nextTile.isPresent()) {
                edges.remove(nextTile.get());
                addTile(0, i, nextTile.get());
                previousTile = nextTile.get();
            } else {
                throw new IllegalStateException("Couldn't find the next edge on the top row?!");
            }
        }

        // Find top right corner
        Optional<Tile> topRightCorner = previousTile.getOrientatedMatchingTileToTheRight(corners);
        if (topRightCorner.isPresent()) {
            corners.remove(topRightCorner.get());
            addTile(0, tilesPerEdge + 1, topRightCorner.get());
        } else {
            throw new IllegalStateException("Couldn't find the top right corner?!");
        }
    }

    private void startWithTopLeftCorner() {
        Tile topLeftTile = corners.remove(0);
        topLeftTile.orientateAsTopLeft(edges);
        addTile(0, 0, topLeftTile);
    }

    private void addTile(int row, int column, Tile tile) {
        puzzle.computeIfAbsent(row, _x -> new HashMap<>()).put(column, tile);
    }
}
