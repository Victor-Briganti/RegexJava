package org.nodes;

/**
 * Concrete class representing the star operation in the grammar of the regex
 * expression tree.
 * 
 * <star> ::= <elementary-expression> "*"
 */
public class StarNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public StarNode(RegexNode left, RegexNode right) {
        super(left, right);

    }

    @Override
    public void printNode() {
        if (right != null) {
            right.printNode();
        }

        System.out.print("*");

        if (left != null) {
            left.printNode();
        }
    }
}
