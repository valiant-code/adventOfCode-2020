package adventofcode;

public class TimeUtil {
    private static long start = System.currentTimeMillis();
    private static int pt = 0;


    public static void startClock(int part) {
        pt = part;
        start = System.currentTimeMillis();
    }


    public static void time() {
        long end = System.currentTimeMillis();
//        System.out.println("Pt " + pt + " Start Time: " + start);
//        System.out.println("Pt " + pt + " End Time:   " + end);
        System.out.println("Pt " + pt + " Time Taken: " + (end - start) / 1000f + "\n");
    }


}
