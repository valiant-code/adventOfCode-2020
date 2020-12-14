package adventofcode;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day14/input.txt");

        //mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        //mem[8] = 11
        String[] mask = null;
        Map<Integer, String[]> memory = new HashMap<>();
        Pattern p = Pattern.compile("\\d+");

        for (int i = 0; i < input.size(); i++) {
            String lineParts[] = input.get(i).split(" = ");
            if ("mask".equals(lineParts[0])) {
                mask = lineParts[1].split("");
            } else {
                Matcher m = p.matcher(lineParts[0]);
                m.find();
                int address = Integer.parseInt(m.group());
                memory.put(address, applyPt1Mask(mask, Integer.parseInt(lineParts[1])));
            }
        }
        BigInteger sum = memory.values().stream()
                .map(sa -> String.join("", sa))
                .map(binStr -> new BigInteger(binStr, 2)).reduce(BigInteger.ZERO, BigInteger::add);
        System.out.println("Part 1: " + sum);
    }


    private static String[] applyPt1Mask(String[] mask, int value) {
        String[] binary = String.format("%36s", Integer.toBinaryString(value)).replace(" ", "0").split("");
        for (int i = 0; i < mask.length; i++) {
            if (!"X".equalsIgnoreCase(mask[i])) {
                binary[i] = mask[i];
            }
        }
        return binary;

    }


    private static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day14/input.txt");

        //mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
        //mem[8] = 11
        String[] mask = null;
        Map<BigInteger, Integer> memory = new HashMap<>();
        Pattern p = Pattern.compile("\\d+");

        for (int i = 0; i < input.size(); i++) {
            String lineParts[] = input.get(i).split(" = ");
            if ("mask".equals(lineParts[0])) {
                mask = lineParts[1].split("");
            } else {
                Matcher m = p.matcher(lineParts[0]);
                m.find();
                int address = Integer.parseInt(m.group());
                String[] binaryAddress = String.format("%36s", Integer.toBinaryString(address)).replace(" ", "0").split("");
                Set<String[]> addresses = applyPt2Mask(mask, binaryAddress);
                addresses.stream()
                        .map(sa -> String.join("", sa))
                        .map(binStr -> new BigInteger(binStr, 2))
                        .forEach(adr -> memory.put(adr, Integer.valueOf(lineParts[1])));
            }
        }
        BigInteger sum = memory.values().stream().map(BigInteger::valueOf).reduce(BigInteger.ZERO, BigInteger::add);
        System.out.println("Part 2: " + sum);
        //Part 1: 3059488894985
        //Part 2: 2900994392308
    }

    private static Set<String[]> applyPt2Mask(String[] mask, String[] binaryAddress) {
        List<Integer> xIndexes = new ArrayList<>();
        for (int i = 0; i < mask.length; i++) {
            switch (mask[i].toUpperCase()) {
                case "X":
                    xIndexes.add(i);
                case "1":
                    binaryAddress[i] = mask[i];
                    break;
                case "0":
                    break;
                default:
                    throw new RuntimeException("something went wrong");
            }
        }
        //now handle x's 1000x0x01x
        Set<String[]> permutations = new HashSet<>();
        permutations.add(binaryAddress);
        for (int index : xIndexes) {
            //loop through our list of current permutations, and replace each X with its two permutations of 0 & 1
            permutations = permutations.stream()
                    .flatMap(a -> {
                        //using flat map to make each array into 2 different ones
                        String[] scenario0 = Arrays.stream(a).toArray(String[]::new);
                        scenario0[index] = "0";
                        String[] scenario1 = a;
                        scenario1[index] = "1";
                        return Stream.of(scenario0, scenario1);
                    })
                    .collect(Collectors.toSet());
        }
        return new HashSet<>(permutations);
    }


}
