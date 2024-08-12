package org.regexjava;

public class Main {
    private Regex regex;

    public static void main(String[] args) {
        Main main = new Main();
        main.regex = new Regex();
        if (main.regex.match("b*ca*b*", "bbbbbcaaaaaaaaaaaabc") < 0) {
            System.out.println("Something went wrong");
        } else {
            System.out.println("It works");
        }
    }
}
