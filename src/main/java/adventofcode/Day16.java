package adventofcode;

import adventofcode.vo.Range;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day16 {

    public static void main(String[] args) throws IOException {
        boolean runPart1 = true;
        boolean runPart2 = true;
        if (runPart1) {
            TimeUtil.startClock(1);
            partOne();
            TimeUtil.time();
        }
        if (runPart2) {
            TimeUtil.startClock(2);
            partTwo();
            TimeUtil.time();
        }
    }

    public static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day16/input.txt", "\n\n");
        Pattern p = Pattern.compile("\\d+-\\d+");
        Matcher m = p.matcher(input.get(0));
        //class: 1-3 or 5-7
        //row: 6-11 or 33-44
        List<String> rawRanges = new ArrayList<>();
        while (m.find()) {
            rawRanges.add(m.group());
        }
        List<Range> ranges = rawRanges.stream().map(r -> {
            String[] parts = r.split("-");
            int min = Integer.parseInt(parts[0]);
            int max = Integer.parseInt(parts[1]);
            return new Range(min, max);
        }).collect(Collectors.toList());


        int result = Arrays.stream(input.get(2).replaceAll("nearby tickets:\n", "").split("[,\\n]"))
                .map(Integer::valueOf)
                .filter(n -> ranges.stream().noneMatch(r -> r.isInRange(n)))
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Part 1 Answer: " + result);
    }


    public static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day16/input.txt", "\n\n");
        Map<Integer, String[]> memory = new HashMap<>();
        Pattern p = Pattern.compile("\\d+-\\d+");
        //class: 1-3 or 5-7
        //row: 6-11 or 33-44

        //make a map of ruleName -> <range1, range2>
        Map<String, List<Range>> rules = new HashMap<>();
        Arrays.stream(input.get(0).split("\n")).forEach(line -> {
            String[] lineParts = line.split(": ");
            Matcher m = p.matcher(lineParts[1]);
            List<String> rawRanges = new ArrayList<>();
            while (m.find()) {
                rawRanges.add(m.group());
            }
            List<Range> ranges = rawRanges.stream().map(r -> {
                String[] parts = r.split("-");
                int min = Integer.parseInt(parts[0]);
                int max = Integer.parseInt(parts[1]);
                return new Range(min, max);
            }).collect(Collectors.toList());
            rules.put(lineParts[0], ranges);
        });

        List<List<Integer>> rows = Arrays.stream(input.get(2).replaceAll("nearby tickets:\n", "").split("[\\n]"))
                .map(r -> Arrays.stream(r.split(",")).map(Integer::valueOf).collect(Collectors.toList()))
                .collect(Collectors.toList());

        //it took me WAY to long to read the problem and see I needed to filter out invalid rows
        rows = rows.stream().filter(r ->
                //keep if every number in the row, matches at least one rule in our rules list - similar to pt1 logic
                r.stream().allMatch(val -> rules.values().stream().flatMap(List::stream).anyMatch(rule -> rule.isInRange(val))))
                .collect(Collectors.toList());

        //rule -> cols that pass the rule
        Map<String, List<Integer>> passedRules = new HashMap<>();
        for (Map.Entry<String, List<Range>> rule : rules.entrySet()) {
            for (int c = 0; c < rows.get(0).size(); c++) {
                int valThatBrokeIt = -2;
                for (int r = 0; r < rows.size(); r++) {
                    Integer currentValue = rows.get(r).get(c);
                    if (rule.getValue().stream().noneMatch(rng -> rng.isInRange(currentValue))) {
                        valThatBrokeIt = currentValue;
                        break;
                    }
                }
                if (valThatBrokeIt == -2) {
                    List<Integer> l = passedRules.getOrDefault(rule.getKey(), new ArrayList<>());
                    l.add(c);
                    passedRules.put(rule.getKey(), l);
                } else {
                    int debug = 0;
                }
            }
        }
        //some columns passed multiple rules, figure out which ones MUST go with which
        Map<String, Integer> finalizedRules = new HashMap<>();
        int totalRules = rules.size();
        //while there is a rule being used for more than 1 col
        while (finalizedRules.size() < totalRules) {
            for (String key : new HashSet<>(passedRules.keySet())) {
                List<Integer> currRule = passedRules.get(key);
                if (currRule != null && currRule.size() == 1) {
                    Integer col = currRule.get(0);
                    finalizedRules.put(key, col);
                    passedRules.remove(key);
                    passedRules.values().forEach(list -> list.removeAll(Collections.singletonList(col)));
                }
            }
        }

        //finally do logic on my ticket
        List<Integer> myTicket = Arrays.stream(input.get(1).split("\\n")[1].split(","))
                .map(Integer::valueOf)
                .collect(Collectors.toList());


        BigInteger result = finalizedRules.entrySet().stream()
                .filter(e -> e.getKey().toLowerCase().startsWith("departure"))
                .map(Map.Entry::getValue) //col indexes of departure rules
                .map(myTicket::get) //values from my ticket to be multiplied
                .map(BigInteger::valueOf)
                .reduce(BigInteger.ONE, BigInteger::multiply);

        System.out.println("Part 2 Answer: " + result);
    }

}
