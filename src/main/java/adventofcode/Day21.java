package adventofcode;

import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day21 {

    private static int currentPart = 0;

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //
        boolean runPart2 = true; //
        if (runPart1) {
            currentPart = 1;
            TimeUtil.startClock(1);
            partOne();
            TimeUtil.time();
        }
        if (runPart2) {
            currentPart = 2;
            TimeUtil.startClock(2);
            partTwo();
            TimeUtil.time();
        }
    }


    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day21/input.txt");

        //pair<list of allergens, list of ingredients>
        List<ImmutablePair<List<String>, List<String>>> ingredientsWithAllergens = input.stream().map(line -> {
            String[] parts = line.split(" \\(contains ");
            return new ImmutablePair<>(
                    Arrays.asList(parts[1].replaceAll("[()]", "").split(", ")),
                    Arrays.asList(parts[0].split(" ")));
        }).collect(Collectors.toList());

        List<String> allIngredientsList = ingredientsWithAllergens.stream().flatMap(p -> p.getRight().stream()).collect(Collectors.toList());
        Set<String> allIngredients = new HashSet<>(allIngredientsList);
        Set<String> allAllergens = ingredientsWithAllergens.stream().flatMap(p -> p.getLeft().stream()).collect(Collectors.toSet());
        Map<String, Set<String>> allergenToPossibleCauses = allAllergens.stream()
                .collect(Collectors.toMap(allergen -> allergen,
                        allergen -> ingredientsWithAllergens.stream()
                                .filter(p -> p.getLeft().contains(allergen))
                                .flatMap(p -> p.getRight().stream())
                                .collect(Collectors.toSet())));
        //lets count how many times each ingredient shows up as a possible allergen
        Map<String, Map<String, Integer>> allergenCountMap = new HashMap<>();
        //allergen -> cause
        Map<String, String> solvedList = new HashMap<>();
        Set<String> unsolvedAllergens = new HashSet<>(allAllergens);
//        while (solvedList.keySet().size() < allAllergens.size()) {
//            for (String a : unsolvedAllergens) {
//                Set<String> possibleCauses = new HashSet<>(allergenToPossibleCauses.get(a));
//                for (String otherAllergen : unsolvedAllergens) {
//                    if (otherAllergen.equals(a)) {
//                        continue;
//                    }
//
//                    possibleCauses.removeAll(allergenToPossibleCauses.get(otherAllergen));
//                }
//                int debug = 0;
//                if (possibleCauses.size() == 1) {
//                    int hj = 0;
//                }
//            }
//        }

        allAllergens.forEach(allergen -> {
            Set<List<String>> listsItAppearsIn = ingredientsWithAllergens.stream().filter(p -> p.getLeft().contains(allergen)).map(ImmutablePair::getRight).collect(Collectors.toSet());
            Map<String, Integer> countMap = new HashMap<>();
            listsItAppearsIn.forEach(list -> {
                list.forEach(ingredient -> {
                    countMap.putIfAbsent(ingredient, 0);
                    countMap.put(ingredient, countMap.get(ingredient) + 1);
                });
            });
            allergenCountMap.put(allergen, countMap);
        });

        List<String> possibleAllergens = new ArrayList<>();
        allergenCountMap.entrySet().forEach(ac -> {
            String allerg = ac.getKey();
            Map<String, Integer> ingred = ac.getValue();
            int max = ingred.values().stream().mapToInt(Integer::intValue).max().getAsInt();
            possibleAllergens.addAll(ingred.entrySet().stream().filter(e -> e.getValue() == max).map(Map.Entry::getKey).collect(Collectors.toSet()));
        });

        Set<String> nonAllergens = new HashSet<>(allIngredients);
        nonAllergens.removeAll(possibleAllergens);
        long result = allIngredientsList.stream().filter(nonAllergens::contains).count();

        System.out.println("Part 1 Answer: " + result);
    }


    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day21/input.txt");

        //pair<list of allergens, list of ingredients>
        List<ImmutablePair<List<String>, List<String>>> ingredientsWithAllergens = input.stream().map(line -> {
            String[] parts = line.split(" \\(contains ");
            return new ImmutablePair<>(
                    Arrays.asList(parts[1].replaceAll("[()]", "").split(", ")),
                    Arrays.asList(parts[0].split(" ")));
        }).collect(Collectors.toList());

        List<String> allIngredientsList = ingredientsWithAllergens.stream().flatMap(p -> p.getRight().stream()).collect(Collectors.toList());
        Set<String> allIngredients = new HashSet<>(allIngredientsList);
        Set<String> allAllergens = ingredientsWithAllergens.stream().flatMap(p -> p.getLeft().stream()).collect(Collectors.toSet());
        Map<String, Set<String>> allergenToPossibleCauses = allAllergens.stream()
                .collect(Collectors.toMap(allergen -> allergen,
                        allergen -> ingredientsWithAllergens.stream()
                                .filter(p -> p.getLeft().contains(allergen))
                                .flatMap(p -> p.getRight().stream())
                                .collect(Collectors.toSet())));
        //lets count how many times each ingredient shows up as a possible allergen
        Map<String, Map<String, Integer>> allergenCountMap = new HashMap<>();

        allAllergens.forEach(allergen -> {
            Set<List<String>> listsItAppearsIn = ingredientsWithAllergens.stream().filter(p -> p.getLeft().contains(allergen)).map(ImmutablePair::getRight).collect(Collectors.toSet());
            Map<String, Integer> countMap = new HashMap<>();
            listsItAppearsIn.forEach(list -> {
                list.forEach(ingredient -> {
                    countMap.putIfAbsent(ingredient, 0);
                    countMap.put(ingredient, countMap.get(ingredient) + 1);
                });
            });
            allergenCountMap.put(allergen, countMap);
        });

        allergenCountMap.entrySet().forEach(ac -> {
            String allerg = ac.getKey();
            Map<String, Integer> ingred = ac.getValue();
            int max = ingred.values().stream().mapToInt(Integer::intValue).max().getAsInt();
            Set<String> possibleAllergens = ingred.entrySet().stream().filter(e -> e.getValue() == max).map(Map.Entry::getKey).collect(Collectors.toSet());
            allergenToPossibleCauses.put(allerg, possibleAllergens);
        });

        Set<String> unsolvedAllergens = new HashSet<>(allAllergens);
        Set<String> solved = allergenToPossibleCauses.entrySet().stream().filter(e -> e.getValue().size() == 1).flatMap(e -> e.getValue().stream()).collect(Collectors.toSet());
        unsolvedAllergens.removeAll(solved);

        //allergen -> cause
        while (allergenToPossibleCauses.entrySet().stream().anyMatch(e -> e.getValue().size() != 1)) {
            Set<String> finalSolved = solved;
            allergenToPossibleCauses.values().stream().filter(e -> e.size() != 1).forEach(causes -> {
                causes.removeAll(finalSolved);
            });

            solved = allergenToPossibleCauses.entrySet().stream().filter(e -> e.getValue().size() == 1).flatMap(e -> e.getValue().stream()).collect(Collectors.toSet());
            unsolvedAllergens.removeAll(solved);
        }
        //allergenToPossibleCauses now each has 1 ingredient for each allergen


        //Arrange the ingredients alphabetically by their allergen and separate them by commas
        // to produce your canonical dangerous ingredient list.
        // (There should not be any spaces in your canonical dangerous ingredient list.)
        // In the above example, this would be mxmxvkd,sqjhc,fvjkl.
        List<String> sortedList = allergenToPossibleCauses.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(e -> e.getValue().stream().findFirst().get())
                .collect(Collectors.toList());
        String.join(",", sortedList);
//        long result = allIngredientsList.stream().filter(nonAllergens::contains).count();
        System.out.println("Part 2 Answer: " + String.join(",", sortedList));
    }

}
