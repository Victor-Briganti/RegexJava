package org.parser;

import org.lexer.*;
import org.nodes.*;

/**
 * Parser class to mount the AST tree.
 */
public class Parser {
    // Lexer to tokenize the input string
    private Lexer lexer;

    // Pointer for the AST tree
    private RegexNode head;

    // Error generated during the parser
    private ErrorType error;

    /**
     * The start symbol of the grammar, represents the top-level structure of the
     * language.
     *
     * <expression> ::= <union> | <basic-expression>
     * 
     * @param node may be used during parsing to represent or modify the current
     *             state.
     * @return new node if the parser was successful, or null otherwise.
     */
    private RegexNode expression(RegexNode node) {
        if (lexer.peek() == Token.UNION) {
            return union(node);
        }

        return basicExp(node);
    }

    /**
     * Non-terminal symbol of the grammar, represents the union operation that
     * we can found in the grammar.
     *
     * <union> ::= <expression> "|" <basic-expression>
     * 
     * @param node used to modify the structure of the AST being generated.
     * @return new RegexNode() if the parser was successful, or null otherwise.
     */
    private RegexNode union(RegexNode node) {
        lexer.consume();
        UnionAuxNode unionAuxNode = new UnionAuxNode(node, null);

        if (lexer.peek() == Token.EOF) {
            unionAuxNode.setRight(emptyExp());
        } else {
            unionAuxNode.setRight(basicExp(null));
        }

        return new UnionNode(null, unionAuxNode);
    }

    /**
     * Non-terminal symbol of the grammar, represents the basic operators that
     * we can found in the grammar.
     *
     * <basic-expression> ::= <star> | <plus> | <elementary-expression>
     * 
     * @param node may be used during parsing to represent or modify the current
     *             state.
     * @return new RegexNode() if the parser was successful, or null otherwise.
     */
    private RegexNode basicExp(RegexNode node) {
        RegexNode elem = elementaryExp(node);

        if (lexer.peek() == Token.STAR) {
            return star(elem);
        }

        if (lexer.peek() == Token.PLUS) {
            return plus(elem);
        }

        return elem;
    }

    /**
     * Non-terminal symbol of the grammar, represents the plus operation that
     * we can found in the grammar.
     *
     * <plus> ::= <elementary-expression> "+"
     * 
     * @param node may be used during parsing to represent or modify the current
     *             state.
     * @return new RegexNode() if the parser was successful, or null otherwise.
     */
    private RegexNode plus(RegexNode node) {
        if (lexer.getConsumedToken() == Token.PLUS) {
            error = ErrorType.DOUBLE_PLUS;
            return null;
        }

        lexer.consume();
        return new PlusNode(null, node);
    }

    /**
     * Non-terminal symbol of the grammar, represents the star operation that
     * we can found in the grammar.
     *
     * <star> ::= <elementary-expression> "*"
     * 
     * @param node may be used during parsing to represent or modify the current
     *             state.
     * @return new RegexNode() if the parser was successful, or null otherwise.
     */
    private RegexNode star(RegexNode node) {
        if (lexer.getConsumedToken() == Token.STAR) {
            error = ErrorType.DOUBLE_STAR;
            return null;
        }

        lexer.consume();
        return new StarNode(null, node);
    }

    /**
     * Non-terminal symbol of the grammar, represents more operators of the grammar,
     * and some other terminal symbols.
     *
     * <elementary-expression> ::= <group> | <char> | <any> | <empty>
     * 
     * @param node may be used during parsing to represent or modify the current
     *             state.
     * @return new RegexNode() if the parser was successful, or null otherwise.
     */
    private RegexNode elementaryExp(RegexNode node) {
        if (lexer.peek() == Token.PARENTHESE_OPEN) {
            return group(node);
        }

        if (lexer.peek() == Token.CHARACTER) {
            return charExp();
        }

        if (lexer.peek() == Token.ANY) {
            return anyExp();
        }

        if (lexer.peek() == Token.UNION) {
            return emptyExp();
        }

        error = ErrorType.INVALID_TOKEN;
        return null;
    }

    /**
     * Non-terminal symbol of the grammar, represents the star operation of the
     * grammar.
     *
     * <group> ::= "(" <expression> ")"
     * 
     * @param node may be used during parsing to represent or modify the current
     *             state.
     * @return new RegexNode() if the parser was successful, or null otherwise.
     */
    private RegexNode group(RegexNode node) {
        lexer.consume();

        GroupNode groupNode = new GroupNode(null, null);
        RegexNode currentNode = groupNode;

        while (lexer.peek() != Token.PARENTHESE_CLOSE && lexer.peek() != Token.EOF) {
            if (currentNode == groupNode) {
                // The first node needs to be appended to the right side of the group node
                currentNode.setRight(expression(node));
                currentNode = currentNode.getRight();
            } else if (lexer.peek() == Token.UNION) {
                // In the case of a union we need to updated the right side of the group node
                // (group)->(expression) => (group)->(union)
                currentNode = expression(groupNode.getRight());
                groupNode.setRight(currentNode);

                // The current node will be the rightmost one.
                // (curNode) = (group)-R->(union)-R->(unionAux)-R->(basic)
                currentNode = groupNode.getRight().getRight().getRight();
            } else {
                currentNode.setLeft(expression(node));
                currentNode = currentNode.getLeft();
            }
        }

        if (lexer.peek() != Token.PARENTHESE_CLOSE) {
            error = ErrorType.PARENTHESE_WITHOUT_CLOSE;
            return null;
        }

        lexer.consume();
        return groupNode;
    }

    /**
     * Terminal symbol of the grammar, represents a character or a
     * meta-character.
     *
     * <char> ::= any-non-metachar | '\' metachar
     * 
     * @return new node if the parser was successful, or null otherwise.
     */
    private RegexNode charExp() {
        lexer.consume();
        return new CharNode(null, null, lexer.getConsumedSymbol());
    }

    /**
     * Terminal symbol of the grammar, represents a any node in the grammar.
     *
     * <any> ::= '.'
     * 
     * @return new node if the parser was successful, or null otherwise.
     */
    private RegexNode anyExp() {
        lexer.consume();
        return new AnyNode(null, null);
    }

    /**
     * Terminal symbol of the grammar, represents a empty node in the grammar.
     *
     * <empty> ::= ''
     * 
     * @return new node if the parser was successful, or null otherwise.
     */
    private RegexNode emptyExp() {
        return new EmptyNode(null, null);
    }

    /**
     * Contructor of the parser.
     *
     * @param input Regular expression to be parsed. This should not be null.
     */
    public Parser(String input) {
        this.lexer = new Lexer(input);
        this.error = ErrorType.SUCCESSFUL;
    }

    /**
     * Sets a new regular expression to be parsed.
     *
     * @param input Regular expression to be parsed. This should not be null.
     */
    public void setInput(String input) {
        lexer.setInput(input);
        head = null;
    }

    /**
     * Parses the regular expression currently saved.
     *
     * @return 0 if the the expression could be parsed, and a negative number
     *         otherwise.
     */
    public int parse() {
        head = expression(null);

        RegexNode currentNode = head;
        while (lexer.peek() != Token.EOF) {
            if (lexer.peek() == Token.UNION) {
                head = expression(head);
                if (head == null) {
                    break;
                }

                currentNode = head;
            } else {
                RegexNode newNode = expression(currentNode);
                if (newNode == null) {
                    head = null;
                    break;
                }

                currentNode.setLeft(newNode);
                currentNode = newNode;
            }
        }

        if (head == null) {
            return error.getValue();
        }

        return 0;
    }

    /**
     * Getter for the AST generated by the parser
     *
     * @return the head pointer of the AST.
     */
    public RegexNode getAST() {
        return head;
    }
}
