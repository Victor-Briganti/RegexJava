package org.nodes;

/**
 * Concrete class representing a basic node in the grammar of the regex
 * expression tree.
 * 
 * <basic-expression> ::= <star> | <plus> | <elementary-RE>
 */
public class BasicNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public BasicNode(RegexNode left, RegexNode right) {
        super(left, right);
    }

    @Override
    public void printNode() {
        this.left.printNode();
    }

}
