package org.nodes;

public abstract class RegexNode {
    RegexNode left;
    RegexNode right;

    RegexNode(RegexNode left, RegexNode right) {
        this.left = left;
        this.right = right;
    }

    public RegexNode getLeft() {
        return left;
    }

    public void setLeft(RegexNode left) {
        this.left = left;
    }

    public void setRight(RegexNode right) {
        this.right = right;
    }

    public RegexNode getRight() {
        return right;
    }

    public abstract void printNode();
}
