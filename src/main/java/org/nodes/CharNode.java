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
     * Constructor of the node.
     *
     * @param left  The left child node.
     * @param right The right child node.
     * @param value The character that give the value for this node.
     */
    public CharNode(RegexNode left, RegexNode right, Character value) {
        super(left, right);
        this.value = value;
    }

    @Override
    public void printNode() {
        System.out.print(value);
        if (left != null) {
            left.printNode();
        }
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
