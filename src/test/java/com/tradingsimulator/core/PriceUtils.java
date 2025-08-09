package com.tradingsimulator.core;


public class PriceUtils {
    public static double parsePrice(String text){
        // keep digits, dot only
        String t = text.replaceAll("[^0-9.]", "");
        if (t.isEmpty()) throw new IllegalArgumentException("No price in: " + text);
        return Double.parseDouble(t);
    }

    public static boolean movedByAbs(double current, double start, double abs){
        return Math.abs(current - start) >= abs;
    }

    public static boolean movedByPct(double current, double start, double pct){
        double changePct = Math.abs((current - start) / start) * 100.0;
        return changePct >= pct;
    }
}
