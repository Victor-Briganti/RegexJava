package org.nodes;

public class ElementaryNode extends RegexNode {

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
