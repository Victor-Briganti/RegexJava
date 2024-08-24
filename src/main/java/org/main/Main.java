package org.main;

import java.util.Scanner;

import org.compiler.Compiler;
import org.engine.Engine;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the regex: ");
        String regex = scanner.next();

        Compiler compiler = new Compiler(regex);
        if (compiler.compile() < 0) {
            System.out.println("Invalid regex");
            return;
        }

        System.out.print("Enter the input: ");
        String input = scanner.next();

        Engine engine = compiler.getEngine();

        if (engine.compute(input)) {
            System.out.println("Regex did match the input");
        } else {
            System.out.println("Regex did not match the input");
        }

    }
}
