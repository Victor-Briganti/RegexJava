package org.nodes;

/**
 * Abstract base class representing a node in a regex expression tree.
 */
public abstract class RegexNode {
    protected RegexNode left;
    protected RegexNode right;

    /**
     * Constructor of the node.
     *
     * @param left  The left child node. May be null.
     * @param right The right child node. May be null.
     */
    RegexNode(RegexNode left, RegexNode right) {
        this.left = left;
        this.right = right;
    }

    /**
     * Returns the left child node.
     *
     * @return The left child node, or null if no left child.
     */
    public RegexNode getLeft() {
        return left;
    }

    /**
     * Set the left child node.
     *
     * @param left The left child node to set. Can be null.
     */
    public void setLeft(RegexNode left) {
        this.left = left;
    }

    /**
     * Set the right child node.
     *
     * @param right The right child node to set. Can be null.
     */
    public void setRight(RegexNode right) {
        this.right = right;
    }

    /**
     * Returns the right child node.
     *
     * @return The right child node, or null if no right child.
     */
    public RegexNode getRight() {
        return right;
    }

    /**
     * Abstracted method used for debugging purpose.
     */
    public abstract void printNode();
}
