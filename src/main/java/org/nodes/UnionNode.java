package org.nodes;

public class UnionNode extends RegexNode {

    public UnionNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null;
        assert right != null;
    }

    @Override
    public void printNode() {
        this.left.printNode();
        System.out.print("|");
        this.right.printNode();
    }
}
