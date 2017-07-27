package com.megasystem.terminales.util;

public class Functions {

    public static String padRight(String s, int n) {
        return String.format("%1$-" + n + "s", s);
    }

    public static String padLeft(String s, int n) {
        return String.format("%1$" + n + "s", s);
    }

    public static String padCenter(String s, int n) {
        return padRight(padLeft(s, ((n / 2) + (s.length() / 2))), n);
    }
}
