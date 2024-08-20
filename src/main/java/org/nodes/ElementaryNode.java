package org.nodes;

/**
 * Concrete class representing a elementary node in the grammar of the regex
 * expression tree.
 * 
 * <elementary-expression> ::= <group> | <char>
 */
public class ElementaryNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     */
    public ElementaryNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null;
        assert right == null;
    }

    @Override
    public void printNode() {
        this.left.printNode();
    }

}
