package adventofcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 {

    private static int currentPart = 0;

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //
        boolean runPart2 = false; //
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

    private static List<Integer> key(Integer... coordinates) {
        return Arrays.asList(coordinates);
    }

    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day19/input.txt", "\n\n");
        AtomicLong acc = new AtomicLong();

        List<String> rawRules = Arrays.asList(input.get(0).split("\n"));
        List<String> messages = Arrays.asList(input.get(1).split("\n"));


        Map<String, String> rulesMap = rawRules.stream()
                .map(str -> str.split(": "))
                .collect(Collectors.toMap(rule -> rule[0], rule -> rule[1].replaceAll("\"", "")));

//
//        Map<String, List<String>> rulesMap = rawRules.stream()
//                .map(str -> str.split(": "))
//                .map(rule -> {
//                    List<String> rulesSplt = Arrays.asList(rule[1].split("\\|"));
//                    rulesSplt.add(0, rule[0]);
//                    return rulesSplt;
//                }).collect(Collectors.toMap(rule ->
//                        rule.get(0), rule -> rule.subList(1, rawRules.size())));

        //rule -> rule[1].replaceAll("\"", "")));

        for (String rule : rulesMap.keySet()) {
            String currentRule = rulesMap.get(rule);
            String parentheticalStr = "(" + currentRule.replace("|", ")|(") + ")";
            rulesMap.put(rule, parentheticalStr);
        }

        //first pass pre-process any values of "a" and "b" we can just pass in
        rulesMap.entrySet().stream().filter(e -> !e.getValue().matches(".*\\d.*"))
                .map(Map.Entry::getKey).forEach(solved -> {
            for (String key : rulesMap.keySet()) {
                if (rulesMap.get(key).contains(solved)) {
                    rulesMap.put(key, rulesMap.get(key).replaceAll(solved, rulesMap.get(solved)));
                }
            }
        });


//        while (rulesMap.values().stream().anyMatch(r -> r.matches(".*\\d.*"))) {
        while (rulesMap.get("0").matches(".*\\d.*")) {
            for (String key : rulesMap.keySet()) {
                String currRule = rulesMap.get(key);
                if (currRule.matches(".*\\d.*")) {
                    Arrays.stream(currRule.split("\\D*(?=\\d*)\\D"))
                            .filter(string -> !string.isEmpty())
                            .map(Integer::valueOf)
                            .sorted(Collections.reverseOrder())
                            .map(ruleRef -> Arrays.asList(ruleRef.toString(), rulesMap.get(ruleRef.toString())))
                            .forEach(newRule -> {
                                String ruleRef = newRule.get(0);
                                String replacement = newRule.get(1);
                                String newRuleStr = rulesMap.get(key).replaceFirst(ruleRef, "(" + replacement + ")");
                                rulesMap.put(key, newRuleStr);
                                int debug = 0;
                            });
                }
            }
        }

        String finalRegex = rulesMap.get("0").replaceAll(" ", "");
        Pattern p = Pattern.compile(finalRegex);
        long result = messages.stream().filter(line -> p.matcher(line).matches()).count();

        System.out.println("Part 1 Answer: " + result);
    }
//
//    private static Pattern innerMostParensRegex = Pattern.compile(
//            //negative lookahead to ensure we find the last (
//            // and then lazy dot quantifier .*?) to get the first right paren after that
//            "\\((?!.*\\().*?\\)");

    public static void partTwo() throws IOException {

    }

}
