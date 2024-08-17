package org.nodes;

public class CharNode extends RegexNode {
    Character value;

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

    public Character getValue() {
        return value;
    }
}
