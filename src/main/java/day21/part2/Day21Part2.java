package day21.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Day21Part2 {
    private static final String INPUT_PATH = "src/main/resources/day21/input.txt";
    private static final String INGREDIENT_ALLERGEN_SPLITTER = " \\(contains ";

    public static List<String> lines;

    private static final Set<String> allAllergens = new HashSet<>();
    private static final Map<String, Integer> allIngredients = new HashMap<>();

    public static void main(String[] args) throws IOException {
        parseInput();

        Map<String, Set<String>> allergenIngredientCombinations = determineIngredientAllergenCombinations();

        String answer = allAllergens.stream()
                .sorted()
                .map(a -> getSetElement(allergenIngredientCombinations.get(a)))
                .collect(Collectors.joining(","));

        System.out.println(answer);
    }

    private static Map<String, Set<String>> determineIngredientAllergenCombinations() {
        Map<String, Set<String>> allergenIngredientCombinations = eliminateImpossibleIngredientAllergenCombinations();

        Set<String> finishedAllergens = new HashSet<>();
        while (finishedAllergens.size() < allAllergens.size()) {
            for (String allergen : allAllergens) {
                if (allergenIngredientCombinations.get(allergen).size() == 1 && !finishedAllergens.contains(allergen)) {
                    // We have found our ingredient/allergen combination
                    finishedAllergens.add(allergen);
                    String ingredient = allergenIngredientCombinations.get(allergen).iterator().next();

                    // Remove the ingredient as possibility from all other allergens
                    for (String otherAllergen : allAllergens) {
                        if (!allergen.equals(otherAllergen)) {
                            allergenIngredientCombinations.get(otherAllergen).remove(ingredient);
                        }
                    }
                }
            }
        }
        return allergenIngredientCombinations;
    }

    private static String getSetElement(Set<String> strings) {
        return strings.iterator().next();
    }

    private static Map<String, Set<String>> eliminateImpossibleIngredientAllergenCombinations() {
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
