package org.nodes;

/**
 * Concrete class used to assist the the union operator
 */
public class UnionAuxNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public UnionAuxNode(RegexNode left, RegexNode right) {
        super(left, right);
    }

    @Override
    public void printNode() {
        left.printNode();
        System.out.print("|");
        right.printNode();
    }
}
