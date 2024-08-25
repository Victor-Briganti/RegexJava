package org.nodes;

/**
 * Concrete class representing a node that matches any character.
 * 
 * <any> ::= '.'
 */
public class AnyNode extends RegexNode {
    /**
     * Constructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public AnyNode(RegexNode left, RegexNode right) {
        super(left, right);
    }

    @Override
    public void printNode() {
        System.out.print('.');
        if (left != null) {
            left.printNode();
        }
    }
}
