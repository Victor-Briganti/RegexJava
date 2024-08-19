package org.nodes;

/**
 * Concrete class representing a basic node in the grammar of the regex
 * expression tree.
 * 
 * <basic-RE> ::= <star> | <plus> | <elementary-RE>
 */
public class BasicNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node. Should not be null.
     * @param right The right child node. Should be null.
     */
    public BasicNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null : "Left child should not be null";
        assert right == null : "Right child should be null";
    }

    @Override
    public void printNode() {
        this.left.printNode();
    }

}
