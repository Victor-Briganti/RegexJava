package org.nodes;

/**
 * Concrete class representing the union operator in the regex expression tree.
 * 
 * <union> ::= <expression> "|" <basic-expression>
 */
public class UnionNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public UnionNode(RegexNode left, RegexNode right) {
        super(left, right);
    }

    @Override
    public void printNode() {
        this.left.printNode();
        System.out.print("|");
        this.right.printNode();
    }
}
