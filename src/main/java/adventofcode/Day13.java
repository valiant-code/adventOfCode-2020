package adventofcode;

import adventofcode.vo.Bus;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day13/input.txt");

        int departureEarliest = Integer.parseInt(input.get(0));

        Bus chosenBus = Arrays.stream(input.get(1).split(","))
                .filter(i -> !"x".equals(i))
                .map(Integer::parseInt)
                .map(id -> new Bus(id, Math.abs(departureEarliest % id - id)))
                .min(Comparator.comparing(Bus::getTimeToWait)).get();

        System.out.println("Part 1: " + chosenBus.answer());
    }


    private static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day13/input.txt");

        List<Integer> buses = Arrays.stream(input.get(1).split(","))
                .map(n -> "x".equals(n) ? -1 : Integer.parseInt(n))
                .collect(Collectors.toList());
        List<BigInteger> values = buses.stream().filter(x -> x > 0).map(BigInteger::valueOf).collect(Collectors.toList());
        List<ImmutablePair<BigInteger, BigInteger>> busesThatMatter = new ArrayList<>();

        for (int i = 0; i < buses.size(); i++) {
            Integer id = buses.get(i);
            if (id < 0) {
                continue;
            }
            //pair of id/offset
            busesThatMatter.add(new ImmutablePair<>(BigInteger.valueOf(id), BigInteger.valueOf(i)));
        }
        //sorting from largest id to smallest didn't actually optimize
//        busesThatMatter.sort(Comparator.comparing(ImmutablePair::getLeft));
//        Collections.reverse(busesThatMatter);

        BigInteger lcm = busesThatMatter.remove(0).getLeft();
        BigInteger curr = lcm;

        while (busesThatMatter.size() > 0) {
            ImmutablePair<BigInteger, BigInteger> nextBus = busesThatMatter.get(0);
            //if (curr + offset) % (busId) == 0
            //then that bus works
            if (curr.add(nextBus.getRight()).mod(nextBus.getLeft()).intValue() == 0) {
                //this only works because every number is prime
                lcm = lcm.multiply(busesThatMatter.remove(0).getLeft());
                continue;
            }
            curr = curr.add(lcm);
        }

        System.out.println("Part 2: " + curr);

    }


}
