package adventofcode;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Day4 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    static List<String> reqFields = Arrays.asList(
            "byr", "iyr", "eyr",
            "hgt", "hcl", "ecl", "pid");

    //byr (Birth Year)
    //iyr (Issue Year)
    //eyr (Expiration Year)
    //hgt (Height)
    //hcl (Hair Color)
    //ecl (Eye Color)
    //pid (Passport ID)
    //cid (Country ID) *optional*
    private static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day4/input.txt", "\n\n");
        AtomicInteger count = new AtomicInteger();

        input.forEach(passport -> {
            List<String> fieldsStrings = Arrays.asList(passport.replaceAll("\n", " ").split(" "));
            Map<String, String> fields = fieldsStrings.stream().collect(Collectors.toMap(f -> f.split(":")[0], f -> f.split(":")[1]));
            if (reqFields.stream().allMatch(fields::containsKey)) {
                count.getAndIncrement();
            }
            ;
        });

        System.out.println("Part 1: " + count);

    }


    private static void partTwo() throws IOException {

        List<String> input = InputUtil.readFileAsStringList("day4/input.txt", "\n\n");
        AtomicInteger count = new AtomicInteger();

        input.forEach(passport -> {
            List<String> fieldsStrings = Arrays.asList(passport.replaceAll("\n", " ").split(" "));
            Map<String, String> fields = fieldsStrings.stream().collect(Collectors.toMap(f -> f.split(":")[0], f -> f.split(":")[1]));
            if (reqFields.stream().allMatch(fields::containsKey)) {
                //it has all keys, check the values
                boolean valid = checkBirthYear(fields);

                if (valid) {
                    valid = checkExpirationYear(fields);
                }


                if (valid) {
                    valid = checkEye(fields);
                }


                if (valid) {
                    valid = checkHeight(fields);
                }


                if (valid) {
                    valid = checkHair(fields);
                }


                if (valid) {
                    valid = checkPassport(fields);
                }


                if (valid) {
                    valid = checkIssueYear(fields);
                }
                if (valid) {
                    count.getAndIncrement();
                }

            }
        });

        System.out.println("Part 2: " + count);
    }

    private static boolean checkBirthYear(Map<String, String> fields) {
        // byr (Birth Year) - four digits; at least 1920 and at most 2002.
        int birthYear = Integer.parseInt(fields.get("byr"));
        return birthYear >= 1920 && birthYear <= 2002;
    }

    private static boolean checkExpirationYear(Map<String, String> fields) {
        //eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
        int year = Integer.parseInt(fields.get("eyr"));
        return year >= 2020 && year <= 2030;
    }

    private static boolean checkIssueYear(Map<String, String> fields) {
        //iyr (Issue Year) - four digits; at least 2010 and at most 2020.
        int year = Integer.parseInt(fields.get("iyr"));
        return year >= 2010 && year <= 2020;
    }

    private static boolean checkHeight(Map<String, String> fields) {
        String h = fields.get("hgt");
        //hgt (Height) - a number followed by either cm or in:
        if (h.matches("\\d*(cm)")) {
            //If cm, the number must be at least 150 and at most 193.
            int hgt = Integer.parseInt(h.replaceAll("cm", ""));
            return hgt >= 150 && hgt <= 193;
        } else if (h.matches("\\d*(in)")) {
            //If in, the number must be at least 59 and at most 76.
            int hgt = Integer.parseInt(h.replaceAll("in", ""));
            return hgt >= 59 && hgt <= 76;
        } else return false;
    }


    private static boolean checkHair(Map<String, String> fields) {
        //hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
        String k = fields.get("hcl");
        return k.matches("#[0-9a-f]{6}");
    }

    private static boolean checkEye(Map<String, String> fields) {
        // ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
        String k = fields.get("ecl");
        return k.matches("amb|blu|brn|gry|grn|hzl|oth");
    }


    private static boolean checkPassport(Map<String, String> fields) {
        //pid (Passport ID) - a nine-digit number, including leading zeroes.
        String k = fields.get("pid");
        return k.matches("\\d{9}");
    }


//cid (Country ID) - ignored, missing or not.

}
