package org.main;

import org.compiler.Compiler;
import org.engine.Engine;

public class Main {

    public static void main(String[] args) {
        Compiler compiler = new Compiler("(ab)*");
        if (compiler.compile() < 0) {
            System.out.println("Failed");
        } else {
            System.out.println("Worked");
        }

        Engine engine = compiler.getEngine();
        if (!engine.compute("0bdc")) {
            System.out.println("Failed");
        } else {
            System.out.println("Worked");
        }
    }
}
