package org.nodes;

/**
 * Concrete class representing a group node in the grammar of the regex
 * expression tree.
 * 
 * <group> ::= "(" <expression> ")"
 */
public class GroupNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public GroupNode(RegexNode left, RegexNode right) {
        super(left, right);
    }

    @Override
    public void printNode() {
        System.out.print("(");
        this.right.printNode();
        System.out.print(")");
        this.left.printNode();
    }

}
