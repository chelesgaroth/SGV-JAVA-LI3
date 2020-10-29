package TestesPerformance;

import static java.lang.System.nanoTime;

public class Crono {

    private static long inicio = 0L;
    private static long fim = 0L;

    public static void start() {
        fim = 0L; inicio = nanoTime();
    }

    public static double stop() {
        fim = nanoTime();
        long elapsedTime = fim - inicio;
        // segundos / 1.0E09
        return elapsedTime / 1.0E09;
    }

    public static String getTime() {
        return "" + stop();
    }


    public static String getTImeString() {
        return "Demorou: " +getTime() + " s";
    }
}