package org.nodes;

/**
 * Concrete class representing the union operator in the regex expression tree.
 * 
 * <union> ::= <RE> "|" <basic-RE>
 */
public class UnionNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node. Should not be null.
     * @param right The right child node. Should not be null.
     */
    public UnionNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null;
        assert right != null;
    }

    @Override
    public void printNode() {
        this.left.printNode();
        System.out.print("|");
        this.right.printNode();
    }
}
