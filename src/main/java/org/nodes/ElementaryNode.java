package org.nodes;

/**
 * Concrete class representing a elementary node in the grammar of the regex
 * expression tree.
 * 
 * <elementary-RE> ::= <group> | <any> | <eos> | <char> | <set>
 */
public class ElementaryNode extends RegexNode {

    /**
     * Contructor of the node.
     *
     * @param left  The left child node. Should not be null.
     * @param right The right child node. Should be null.
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
