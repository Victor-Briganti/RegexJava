package org.main;

import org.nodes.CharNode;
import org.nodes.UnionNode;

public class Main {

    public static void main(String[] args) {
        CharNode charNode = new CharNode(null, null, 'a');
        UnionNode unionNode = new UnionNode(charNode, charNode);
        unionNode.printNode();
    }
}
