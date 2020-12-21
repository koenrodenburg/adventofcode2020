package day21.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day21Part1 {
    private static final String INPUT_PATH = "src/main/resources/day21/input.txt";
    private static final String INGREDIENT_ALLERGEN_SPLITTER = " \\(contains ";

    private static List<String> lines;

    private static final Set<String> allAllergens = new HashSet<>();
    private static final Map<String, Integer> allIngredients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        parseInput();

        Map<String, Set<String>> allergenIngredientCombinations = eliminateIngredientAllergenCombinations();

        long count = allIngredients.keySet().stream()
                .filter(ingredient -> allergenIngredientCombinations.values().stream()
                        .noneMatch(allergenIngredients -> allergenIngredients.contains(ingredient)))
                .mapToInt(allIngredients::get)
                .sum();

        System.out.println(count);
    }

    private static Map<String, Set<String>> eliminateIngredientAllergenCombinations() {
        Map<String, Set<String>> potentialAllergenIngredientCombinations = new HashMap<>();
        for (String allergen : allAllergens) {
            potentialAllergenIngredientCombinations.put(allergen, new HashSet<>(allIngredients.keySet()));
        }
        for (String line : lines) {
            String[] parts = line.split(INGREDIENT_ALLERGEN_SPLITTER);
            List<String> ingredientsOnThisLine = Arrays.asList(parts[0].split(" "));
            String[] allergensOnThisLine = parts[1].replace(')', ' ').trim().split(", ");
            for (String allergen : allergensOnThisLine) {
                for (String ingredient : allIngredients.keySet()) {
                    if (!ingredientsOnThisLine.contains(ingredient)) {
                        potentialAllergenIngredientCombinations.get(allergen).remove(ingredient);
                    }
                }
            }
        }
        return potentialAllergenIngredientCombinations;
    }

    private static void parseInput() throws IOException {
        lines = Files.readAllLines(Path.of(INPUT_PATH));
        for (String line : lines) {
            String[] parts = line.split(INGREDIENT_ALLERGEN_SPLITTER);
            for (String ingredient : parts[0].split(" ")) {
                allIngredients.merge(ingredient, 1, Integer::sum);
            }
            for (String allergen : parts[1].replace(')', ' ').trim().split(", ")) {
                allAllergens.add(allergen.trim());
            }
        }
    }
}
