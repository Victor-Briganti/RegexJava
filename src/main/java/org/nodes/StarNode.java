package org.nodes;

/**
 * Concrete class representing the star operation in the regex expression tree.
 */
public class StarNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node. Should not be null.
     * @param right The right child node. Should be null.
     */
    public StarNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null;
        assert right == null;
    }

    @Override
    public void printNode() {
        this.left.printNode();
        System.out.print("*");
    }
}
