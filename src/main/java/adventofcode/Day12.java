package adventofcode;

import adventofcode.vo.Coordinate;

import java.io.IOException;
import java.util.List;

public class Day12 {


    public static void main(String[] args) throws IOException {
        partOne();
        partTwo();
    }

    private static void partOne() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day12/input.txt");

        Coordinate position = new Coordinate(0, 0);

        String facing = "E";

        for (String line : input) {
            String action = line.substring(0, 1);
            int value = Integer.parseInt(line.substring(1));
            if (action.matches("[LR]")) {
                facing = rotate(facing, action, value);
            } else {
                position = move(position, action, value, facing);
            }
        }
        ;


        System.out.println("Part 1: " + position.manhattanDistance());
    }


    private static String rotate(String startingDirection, String action, int degrees) {
        String ret = startingDirection;
        for (int i = 0; i < degrees / 90; i++) {
            if (action.equals("L")) {
                switch (ret) {
                    case "N":
                        ret = "W";
                        break;
                    case "W":
                        ret = "S";
                        break;
                    case "S":
                        ret = "E";
                        break;
                    case "E":
                        ret = "N";
                        break;
                }
            } else if (action.equals("R")) {
                switch (ret) {
                    case "N":
                        ret = "E";
                        break;
                    case "W":
                        ret = "N";
                        break;
                    case "S":
                        ret = "W";
                        break;
                    case "E":
                        ret = "S";
                        break;
                }
            }
        }
        return ret;
    }

    private static Coordinate move(Coordinate start, String action, int value, String facing) {
        String direction = "F".equals(action) ? facing : action;
        switch (direction) {
            case "N":
                return new Coordinate(start.getX(), start.getY() + value);
            case "S":
                return new Coordinate(start.getX(), start.getY() - value);
            case "E":
                return new Coordinate(start.getX() + value, start.getY());
            case "W":
                return new Coordinate(start.getX() - value, start.getY());
            default:
                throw new RuntimeException("invalid direction - " + direction);
        }
    }


    private static void partTwo() throws IOException {
        List<String> input = InputUtil.readFileAsStringList("day12/input.txt");

        Coordinate position = new Coordinate(0, 0);
        Coordinate waypoint = new Coordinate(10, 1);

        for (String line : input) {
            String action = line.substring(0, 1);
            int value = Integer.parseInt(line.substring(1));
            if (action.matches("[LR]")) {
                waypoint = rotateWaypoint(waypoint, action, value);
            } else if (action.matches("[NSWE]")) {
                waypoint = move(waypoint, action, value, "");
            } else {
                position = moveToWaypoint(position, waypoint, value);
            }
        }
        ;


        System.out.println("Part 2: " + position.manhattanDistance());
    }

    private static Coordinate rotateWaypoint(Coordinate waypoint, String action, int degrees) {
        int x = waypoint.getX();
        int y = waypoint.getY();
        for (int i = 0; i < degrees / 90; i++) {
            int tmpX = x;
            int tmpY = y;
            if (action.equals("L")) {
                y = tmpX;
                x = -1 * tmpY;
            } else if (action.equals("R")) {
                y = -1 * tmpX;
                x = tmpY;
            }
        }
        return new Coordinate(x, y);
    }

    private static Coordinate moveToWaypoint(Coordinate start, Coordinate waypoint, int times) {
        int x = start.getX() + (times * waypoint.getX());
        int y = start.getY() + (times * waypoint.getY());
        return new Coordinate(x, y);
    }

}
