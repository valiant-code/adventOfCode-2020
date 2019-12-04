package adventofcode;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InputUtil {

    // ecample filepath: day1/input.txt
    public static String readFileAsString(String filepath) throws IOException {
        File h = getFileFromResources(filepath);
        return readFile(h.getPath());
    }

    public static List<String> readFileAsStringList(String filepath) throws IOException {
        File h = getFileFromResources(filepath);
        return Arrays.asList(readFile(h.getPath()).split("\n"));
    }

    public static List<Integer> readFileAsIntList(String filepath) throws IOException {
        return readFileAsStringList(filepath).stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    public static List<String> readFileAsStringList(String filepath, String delimiter) throws IOException {
        File h = getFileFromResources(filepath);
        return Arrays.asList(readFile(h.getPath()).split(delimiter));
    }

    private static String readFile(String path)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, StandardCharsets.UTF_8);
    }

    private static File getFileFromResources(String fileName) {
        ClassLoader classLoader = InputUtil.class.getClassLoader();

        URL resource = classLoader.getResource(fileName);
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return new File(resource.getFile());
        }
    }
}
