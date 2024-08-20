package org.main;

import org.parser.Parser;

public class Main {

    public static void main(String[] args) {
        Parser parser = new Parser("(abc)*b*");
        if (parser.parse() == 0) {
            System.out.println("ok");
            parser.getAST().printNode();
        } else {
            System.out.println("nok");
        }

    }
}
