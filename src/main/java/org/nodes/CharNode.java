package org.nodes;

/**
 * Concrete class representing a character in a regex expression tree.
 * 
 * <char> ::= any-non-metachar | "\\" metachar
 */
public class CharNode extends RegexNode {
    // Character representing this node
    Character value;

    /**
     * Contructor of the node.
     *
     * @param left  The left child node. Should be null.
     * @param right The right child node. Should be null.
     * @param value The character that give the value for this node.
     */
    public CharNode(RegexNode left, RegexNode right, Character value) {
        super(left, right);
        assert value != null;
        assert left == null;
        assert right == null;
        this.value = value;
    }

    @Override
    public void printNode() {
        System.out.print(value);
    }

    /**
     * Getter method for the node value.
     *
     * @return character that give the value for this node.
     */
    public Character getValue() {
        return value;
    }
}
