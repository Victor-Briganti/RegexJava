package org.nodes;

public class BasicNode extends RegexNode {

    public BasicNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null;
        assert right == null;
    }

    @Override
    public void printNode() {
        this.left.printNode();
    }

}
