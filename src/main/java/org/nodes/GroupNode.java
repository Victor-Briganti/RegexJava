package org.nodes;

public class GroupNode extends RegexNode {

    public GroupNode(RegexNode left, RegexNode right) {
        super(left, right);
        assert left != null;
        assert right == null;
    }

    @Override
    public void printNode() {
        System.out.print("(");
        this.left.printNode();
        System.out.print(")");
    }

}
