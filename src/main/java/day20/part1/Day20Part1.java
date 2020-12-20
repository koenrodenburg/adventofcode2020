package day20.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day20Part1 {
    private static final String INPUT_PATH = "src/main/resources/day20/input.txt";
    private static final String TILE_PREFIX = "Tile ";

    private static final List<Tile> tiles = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        readInput();

        List<Tile> corners = findCorners();

        long result = corners.stream()
                .map(Tile::getId)
                .mapToLong(Long::parseLong)
                .reduce(1, (l1, l2) -> l1 * l2);

        System.out.println(result);
    }

    private static List<Tile> findCorners() {
        return tiles.stream()
                .filter(tile -> tile.isCorner(tiles))
                .collect(Collectors.toList());
    }

    private static void readInput() throws IOException {
        Tile currentTile = new Tile();
        for (String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            if (line.startsWith(TILE_PREFIX)) {
                currentTile = new Tile();
                currentTile.setId(line.substring(TILE_PREFIX.length(), line.indexOf(':')));
                tiles.add(currentTile);
            } else if (!line.isEmpty()) {
                currentTile.addRow(line);
            }
        }
    }
}
