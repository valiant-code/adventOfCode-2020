package adventofcode;

import adventofcode.vo.BagRule;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day7 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        AtomicInteger count = new AtomicInteger();
        List<String> input = InputUtil.readFileAsStringList("day7/input.txt");

        Map<String, List<BagRule>> ruleMap = new HashMap<>();
        input.forEach(line -> {
            String bag = removeTrailingChars(line.split(" contain ")[0]);
            List<String> rules = Arrays.asList(line.split(" contain ")[1].split(", "));
            if (rules.size() > 1 || !rules.get(0).equals("no other bags.")) {
                List<BagRule> bagRules = rules.stream().map(str -> {
                    BagRule r = new BagRule();
                    r.setChild(removeTrailingChars(str.split("\\d ")[1]));
                    r.setCount(Integer.parseInt(str.substring(0, 1)));
                    return r;
                }).collect(Collectors.toList());
                ruleMap.put(bag, bagRules);
            } else
                ruleMap.put(bag, Collections.emptyList());
        });

        Set<String> bagsThatCanContainGold = new HashSet<>();

        Set<String> containers = new HashSet<>(findBagsThatContainAll("shiny gold bag", ruleMap));
        bagsThatCanContainGold.addAll(containers);
//
//        while (containers.size() > 0) {
//            HashSet<String> copy = new HashSet<>(containers);
//            containers = new HashSet<>();
//            Set<String> finalContainers = containers;
//            copy.forEach(c -> finalContainers.addAll(findBagsThatContain(c, ruleMap)));
//            containers = finalContainers;
//            bagsThatCanContainGold.addAll(containers);
//        }


        System.out.println("Part 1: " + bagsThatCanContainGold.size());

    }

    private static String removeTrailingChars(String str) {
        return str.replaceAll("s$|s\\.|\\.", "").trim();
    }


    private static List<String> findBagsThatContain(String bagName, Map<String, List<BagRule>> ruleMap) {
        return ruleMap.entrySet().stream().filter(r -> r.getValue().stream().anyMatch(br -> br.getChild().equals(bagName))).map(Map.Entry::getKey).collect(Collectors.toList());
    }


    private static List<String> findBagsThatContainAll(String bagName, Map<String, List<BagRule>> ruleMap) {
        //get bags that contain our search query
        Set<String> bagsThatContain = ruleMap.entrySet().stream().filter(r -> r.getValue().stream().anyMatch(br -> br.getChild().equals(bagName))).map(Map.Entry::getKey).collect(Collectors.toSet());
        //then use recursion to find what bags contain those
        bagsThatContain.addAll(bagsThatContain.stream().map(i -> findBagsThatContainAll(i, ruleMap)).flatMap(Collection::stream).collect(Collectors.toList()));
        return new ArrayList<>(bagsThatContain);
    }


    private static void partTwo() throws IOException {
        AtomicInteger count = new AtomicInteger();
        List<String> input = InputUtil.readFileAsStringList("day7/input.txt");

        Map<String, List<BagRule>> ruleMap = new HashMap<>();
        input.forEach(line -> {
            String bag = removeTrailingChars(line.split(" contain ")[0]);
            List<String> rules = Arrays.asList(line.split(" contain ")[1].split(", "));
            if (rules.size() > 1 || !rules.get(0).equals("no other bags.")) {
                List<BagRule> bagRules = rules.stream().map(str -> {
                    BagRule r = new BagRule();
                    r.setChild(removeTrailingChars(str.split("\\d ")[1]));
                    r.setCount(Integer.parseInt(str.substring(0, 1)));
                    return r;
                }).collect(Collectors.toList());
                ruleMap.put(bag, bagRules);
            } else
                ruleMap.put(bag, Collections.emptyList());
        });


        count.addAndGet(getTotalBagCount("shiny gold bag", ruleMap) - 1);

        System.out.println("Part 2: " + count);
    }

    private static int getTotalBagCount(String bagName, Map<String, List<BagRule>> ruleMap) {
        List<BagRule> k = ruleMap.get(bagName);
        if (k.size() == 0) {
            return 1;
        }
        int count = 1;
        for (BagRule child : k) {
            count += child.getCount() * getTotalBagCount(child.getChild(), ruleMap);
        }
        ;

        return count;
    }
}
