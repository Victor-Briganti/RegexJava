package org.nodes;

/**
 * Concrete class representing a empty character in a regex expression tree.
 * 
 * <empty> ::= ""
 */
public class EmptyNode extends RegexNode {

    /**
     * Constructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public EmptyNode(RegexNode left, RegexNode right) {
        super(left, right);
    }

    @Override
    public void printNode() {
        if (left != null) {
            left.printNode();
        }
    }

}
