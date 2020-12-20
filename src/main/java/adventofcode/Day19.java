package adventofcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day19 {

    private static int currentPart = 0;

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true; //176
        boolean runPart2 = true; //352
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

//        List<ImmutablePair<Integer, String>> sortedRules = rawRules.stream().map(m -> new ImmutablePair<>(Integer.valueOf(m.split(": ")[0]), m.split(": ")[1]))
//                .sorted(Comparator.comparing(ImmutablePair::getLeft)).collect(Collectors.toList());
//        StringBuilder sb = new StringBuilder();
//        sortedRules.forEach( rule ->
//                sb.append(rule.getLeft())
//                .append(": ")
//                .append(rule.getRight())
//                .append("\n")
//        );

        Map<String, String> rulesMap = rawRules.stream()
                .map(str -> str.split(": "))
                .collect(Collectors.toMap(rule -> rule[0], rule ->
                        rule[1].replaceAll("\"", "")));

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

        Pattern digits = Pattern.compile("\\d*");
        for (String rule : rulesMap.keySet()) {
            String currentRule = rulesMap.get(rule);
            final StringBuilder parentheticalStr = new StringBuilder("(");
            Arrays.stream(currentRule.split(" ")).forEach(part -> {
                if (part.matches("[ab|]")) {
                    parentheticalStr.append(part);
                } else
                    parentheticalStr.append("(" + part + ")");
            });
            parentheticalStr.append(")");
            rulesMap.put(rule, parentheticalStr.toString());
        }

        //first pass pre-process any values of "a" and "b" we can just pass in
        rulesMap.entrySet().stream().filter(e -> !e.getValue().matches(".*\\d.*"))
                .map(Map.Entry::getKey).forEach(solved -> {
            if (rulesMap.get(solved).length() == 3) {
                rulesMap.put(solved, rulesMap.get(solved).replaceAll("[()]", ""));
            }
            for (String key : rulesMap.keySet()) {
                if (rulesMap.get(key).contains(solved)) {
                    rulesMap.put(key, rulesMap.get(key).replaceAll("[(]" + solved + "[)]", rulesMap.get(solved)));
                }
            }
        });


//        while (rulesMap.values().stream().anyMatch(r -> r.matches(".*\\d.*"))) {
        while (rulesMap.get("0").matches(".*\\d.*")) {
            for (String key : rulesMap.keySet()) {
                String currRule = rulesMap.get(key);
                if (currRule.matches(".*\\d.*")) {
                    //get the number groups from the current string
                    Arrays.stream(currRule.split("\\D*(?=\\d*)\\D"))
                            .filter(string -> !string.isEmpty())
                            .forEach(ruleRef -> {
                                String replacement = rulesMap.get(ruleRef);
                                String newRuleStr = rulesMap.get(key).replaceAll("[(]" + ruleRef + "[)]", replacement);
                                String oldRule = rulesMap.get(key);
                                if ("0".equals(key)) {
                                    int debug = 0;
                                }
                                rulesMap.put(key, newRuleStr);
                            });
                }
            }
        }

        String finalRegex = rulesMap.get("0").replaceAll(" ", "");
        Pattern p = Pattern.compile(finalRegex);
        long result = messages.stream().filter(line -> p.matcher(line).matches()).count();
        List<String> result2 = messages.stream().filter(line -> p.matcher(line).matches()).collect(Collectors.toList());
        //sample 2
        //Valid message: bbabbbbaabaabba
        //Valid message: ababaaaaaabaaab
        //Valid message: ababaaaaabbbaba - this one wasn't found..

        //154 too low
        //176 is final answer...
        System.out.println("Part 1 Answer: " + result);
    }

    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day19/input2.txt", "\n\n");
        AtomicLong acc = new AtomicLong();

        List<String> rawRules = Arrays.asList(input.get(0).split("\n"));
        List<String> messages = Arrays.asList(input.get(1).split("\n"));


        Map<String, String> rulesMap = rawRules.stream()
                .map(str -> str.split(": "))
                .collect(Collectors.toMap(rule -> rule[0], rule ->
                        rule[1].replaceAll("\"", "")));

        Pattern digits = Pattern.compile("\\d*");
        for (String rule : rulesMap.keySet()) {
            String currentRule = rulesMap.get(rule);
            final StringBuilder parentheticalStr = new StringBuilder("(");
            Arrays.stream(currentRule.split(" ")).forEach(part -> {
                if (part.matches("[ab|]")) {
                    parentheticalStr.append(part);
                } else
                    parentheticalStr.append("(" + part + ")");
            });
            parentheticalStr.append(")");
            rulesMap.put(rule, parentheticalStr.toString());
        }

        //first pass pre-process any values of "a" and "b" we can just pass in
        rulesMap.entrySet().stream().filter(e -> !e.getValue().matches(".*\\d.*"))
                .map(Map.Entry::getKey).forEach(solved -> {
            if (rulesMap.get(solved).length() == 3) {
                rulesMap.put(solved, rulesMap.get(solved).replaceAll("[()]", ""));
            }
            for (String key : rulesMap.keySet()) {
                if (rulesMap.get(key).contains(solved)) {
                    rulesMap.put(key, rulesMap.get(key).replaceAll("[(]" + solved + "[)]", rulesMap.get(solved)));
                }
            }
        });


        String ruleZero = rulesMap.remove("0");
        String rule8 = rulesMap.remove("8");
        String rule11 = rulesMap.remove("11");
//        while (rulesMap.values().stream().anyMatch(r -> r.matches(".*\\d.*"))) {
        while (rulesMap.values().stream().anyMatch(val -> val.matches(".*\\d.*"))) {
            for (String key : rulesMap.keySet()) {
                String currRule = rulesMap.get(key);
                if (currRule.matches(".*\\d.*")) {
                    //get the number groups from the current string
                    Arrays.stream(currRule.split("\\D*(?=\\d*)\\D"))
                            .filter(string -> !string.isEmpty())
                            .forEach(ruleRef -> {
                                String replacement = rulesMap.get(ruleRef);
                                String newRuleStr = rulesMap.get(key).replaceAll("[(]" + ruleRef + "[)]", replacement);
                                String oldRule = rulesMap.get(key);
                                if ("0".equals(key)) {
                                    int debug = 0;
                                }
                                rulesMap.put(key, newRuleStr);
                            });
                }
            }
        }

        String rule31 = rulesMap.get("31").replaceAll(" ", "");
        String rule42 = rulesMap.get("42").replaceAll(" ", "");
        //regex now created for everything except 0, 8, 11
        //and 0 is our goal
        //0: 8 11
        //8: 42 | 42 8
        //11: 42 31 | 42 11 31

        //0: (42)+ 11
        //0: (42)+(42{x}31{x})
        //unfortunately regex doesn't have a variable quantifier like that
        //so we have to do 42+(42{1}31{1}|42{2}31{2}..)

        String finalPattern = "^" + rule42 + "+" + "(";
        //use loop to build out
        //arbitrary limit of 11 iterations since our input isn't that long
        for (int i = 1; i < 12; i++) {
            if (i != 1) {
                finalPattern = finalPattern + "|";
            }
            finalPattern = finalPattern + rule42 + "{" + i + "}";
            finalPattern = finalPattern + rule31 + "{" + i + "}";
        }
        finalPattern = finalPattern + ")";
        Pattern p = Pattern.compile(finalPattern);
        long result = messages.stream().filter(line -> p.matcher(line).matches()).count();
        List<String> result2 = messages.stream().filter(line -> p.matcher(line).matches()).collect(Collectors.toList());

        System.out.println("Part 2 Answer: " + result);
    }

}
