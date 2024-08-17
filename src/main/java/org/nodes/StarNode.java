package org.nodes;

public class StarNode extends RegexNode {

    public StarNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null;
        assert right == null;
    }

    @Override
    public void printNode() {
        this.left.printNode();
        System.out.print("*");
    }
}
