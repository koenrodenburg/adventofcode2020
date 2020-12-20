package day20.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day20Part2 {
    private static final String INPUT_PATH = "src/main/resources/day20/input.txt";
    private static final String NESSIE_PATH = "src/main/resources/day20/nessie.txt";
    private static final String TILE_PREFIX = "Tile ";

    private static final List<Tile> tiles = new ArrayList<>();
    private static List<List<Boolean>> nessie;

    public static void main(String[] args) throws IOException {
        System.out.println("Reading input...");
        readInput();

        System.out.println("Finding corners, edges and the rest...");
        List<Tile> corners = findCorners();
        List<Tile> edges = findEdges();
        List<Tile> rest = tiles.stream()
                .filter(tile -> !corners.contains(tile))
                .filter(tile -> !edges.contains(tile))
                .collect(Collectors.toList());

        System.out.println("Solving the puzzle...");
        Puzzle puzzle = new Puzzle(corners, edges, rest).solve();

        System.out.println("Extracting the image...");
        List<List<Boolean>> image = extractImage(puzzle);
        flip(image);
        image = rotate90DegreesToTheRight(image);

        System.out.println("Finding monsters...");
        int monsters = findMonstersInAllOrientations(image);

        System.out.println("Counting roughness...");
        int roughness = calculateRoughness(image, monsters);

        System.out.println("Roughness: " + roughness);
    }

    private static int calculateRoughness(List<List<Boolean>> image, int monsters) {
        int noOfTrues = 0;
        for (List<Boolean> imageRows : image) {
            for (Boolean pixel : imageRows) {
                if (pixel) {
                    noOfTrues++;
                }
            }
        }

        int noOfTruesInAMonster = 0;
        for (List<Boolean> nessieRows : nessie) {
            for (Boolean nessieChars : nessieRows) {
                if (nessieChars) {
                    noOfTruesInAMonster++;
                }
            }
        }

        return noOfTrues - (monsters * noOfTruesInAMonster);
    }

    private static int findMonstersInAllOrientations(List<List<Boolean>> image) {
        for (int i = 0; i < 4; i++) {
            int monsters = findMonsters(image);
            if (monsters > 0) {
                return monsters;
            }
            image = rotate90DegreesToTheRight(image);
        }
        flip(image);
        for (int i = 0; i < 4; i++) {
            int monsters = findMonsters(image);
            if (monsters > 0) {
                return monsters;
            }
            image = rotate90DegreesToTheRight(image);
        }
        return 0;
    }

    private static void flip(List<List<Boolean>> image) {
        Collections.reverse(image);
    }

    private static List<List<Boolean>> rotate90DegreesToTheRight(List<List<Boolean>> image) {
        List<List<Boolean>> rotatedImage = new ArrayList<>();

        for (int i = 0; i < image.size(); i++) {
            int finalI = i;
            List<Boolean> rotatedRow = image.stream()
                    .map(row -> row.get(finalI))
                    .collect(Collectors.toList());
            Collections.reverse(rotatedRow);
            rotatedImage.add(rotatedRow);
        }

        return rotatedImage;
    }

    private static int findMonsters(List<List<Boolean>> image) {
        int monsters = 0;
        for (int y = 0; y < image.size() - nessie.size() - 1; y++) {
            List<Boolean> row = image.get(y);
            for (int x = 0; x < row.size() - nessie.get(0).size() - 1; x++) {
                if (monsterAtPosition(image, y, x)) {
                    monsters++;
                }
            }
        }
        return monsters;
    }

    private static boolean monsterAtPosition(List<List<Boolean>> image, int y, int x) {
        for (int i = 0; i < nessie.size(); i++) {
            for (int j = 0; j < nessie.get(i).size(); j++) {
                Boolean nessieAtIJ = nessie.get(i).get(j);
                if (nessieAtIJ && !image.get(y + i).get(x + j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static List<List<Boolean>> extractImage(Puzzle puzzle) {
        List<List<Boolean>> image = new ArrayList<>();

        for (Map<Integer, Tile> puzzleRow : puzzle.getPuzzle().values()) {
            for (int pixelRowInTile = 1; pixelRowInTile < 9; pixelRowInTile++) {
                List<Boolean> imageRow = new ArrayList<>();
                for (Tile tile : puzzleRow.values()) {
                    List<Boolean> pixelRow = tile.getPixels().get(pixelRowInTile);
                    for (int i = 1; i < pixelRow.size() - 1; i++) {
                        imageRow.add(pixelRow.get(i));
                    }
                }
                image.add(imageRow);
            }
        }

        return image;
    }

    private static List<Tile> findCorners() {
        return tiles.stream()
                .filter(tile -> tile.isCorner(tiles))
                .collect(Collectors.toList());
    }

    private static List<Tile> findEdges() {
        return tiles.stream()
                .filter(tile -> tile.isEdge(tiles))
                .collect(Collectors.toList());
    }

    private static void readInput() throws IOException {
        // Read nessie
        nessie = new ArrayList<>();
        for (String line : Files.readAllLines(Path.of(NESSIE_PATH))) {
            List<Boolean> nessieRow = new ArrayList<>();
            for (char currentChar : line.toCharArray()) {
                nessieRow.add(currentChar == '#');
            }
            nessie.add(nessieRow);
        }

        // Read tiles
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
