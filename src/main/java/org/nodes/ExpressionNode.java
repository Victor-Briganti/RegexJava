package org.nodes;

/**
 * Concrete class representing the expression node in the grammar of the
 * regex expression tree.
 * 
 * <expression> ::= <union> | <basic-expression>
 */
public class ExpressionNode extends RegexNode {

    /**
     * Constructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public ExpressionNode(RegexNode left, RegexNode right) {
        super(left, right);
    }

    @Override
    public void printNode() {
        if (left == null) {
            System.out.println("null");
        } else {
            left.printNode();
        }
    }

}
