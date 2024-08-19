package org.nodes;

/**
 * Concrete class representing a group node in the grammar of the regex
 * expression tree.
 * 
 * <group> ::= "(" <RE> ")"
 */
public class GroupNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node. Should not be null.
     * @param right The right child node. Should be null.
     */
    public GroupNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null;
        assert right == null;
    }

    @Override
    public void printNode() {
        System.out.print("(");
        this.left.printNode();
        System.out.print(")");
    }

}
