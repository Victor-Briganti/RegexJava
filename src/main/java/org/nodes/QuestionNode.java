package org.nodes;

/**
 * Concrete class representing the question operation in the grammar of the
 * regex expression tree.
 * 
 * <question> ::= <elementary-expression> "?"
 */
public class QuestionNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public QuestionNode(RegexNode left, RegexNode right) {
        super(left, right);

    }

    @Override
    public void printNode() {
        if (right != null) {
            right.printNode();
        }

        System.out.print("?");

        if (left != null) {
            left.printNode();
        }
    }
}
