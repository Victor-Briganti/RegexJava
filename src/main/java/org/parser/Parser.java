package org.parser;

import org.lexer.*;
import org.nodes.*;

/**
 * Parser class to mount the AST tree.
 */
public class Parser {
    // Lexer to tokenize the input string
    Lexer lexer;

    // Pointer for the AST tree
    RegexNode head;

    // Error generated during the parser
    ErrorType error;

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
    RegexNode expression(RegexNode node) {
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
    RegexNode union(RegexNode node) {
        lexer.consume();
        UnionAuxNode unionAuxNode = new UnionAuxNode(node, null);
        unionAuxNode.setRight(basicExp(null));
        return new UnionNode(null, unionAuxNode);
    }

    /**
     * Non-terminal symbol of the grammar, represents the basic operators that
     * we can found in the grammar.
     *
     * <basic-expression> ::= <star> | <elementary-expression>
     * 
     * @param node may be used during parsing to represent or modify the current
     *             state.
     * @return new RegexNode() if the parser was successful, or null otherwise.
     */
    RegexNode basicExp(RegexNode node) {
        RegexNode elem = elementaryExp(node);
        if (lexer.peek() == Token.STAR) {
            return star(elem);
        }

        return elem;
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
    RegexNode star(RegexNode node) {
        lexer.consume();
        return new StarNode(null, node);
    }

    /**
     * Non-terminal symbol of the grammar, represents more operators of the grammar,
     * and some other terminal symbols.
     *
     * <elementary-expression> ::= <group> | <char>
     * 
     * @param node may be used during parsing to represent or modify the current
     *             state.
     * @return new RegexNode() if the parser was successful, or null otherwise.
     */
    RegexNode elementaryExp(RegexNode node) {
        if (lexer.peek() == Token.PARENTHESE_OPEN) {
            return group(node);
        }

        if (lexer.peek() == Token.CHARACTER) {
            return charExp();
        }

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
    RegexNode group(RegexNode node) {
        lexer.consume();

        GroupNode groupNode = new GroupNode(null, null);
        RegexNode aux = groupNode;

        Token peekToken = lexer.peek();
        while (peekToken != Token.PARENTHESE_CLOSE && peekToken != Token.EOF) {
            if (aux == groupNode) {
                aux.setRight(expression(node));
                aux = aux.getRight();
                continue;
            }

            if (lexer.peek() == Token.UNION) {
                aux = expression(groupNode.getRight());
                groupNode.setRight(aux);
                break;
            } else {
                aux.setLeft(expression(node));
            }

            aux = aux.getLeft();
            peekToken = lexer.peek();
        }

        if (peekToken == Token.EOF) {
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
    RegexNode charExp() {
        lexer.consume();
        CharNode node = new CharNode(null, null, lexer.getConsumedSymbol());
        return node;
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

        RegexNode aux = head;
        RegexNode iter = null;
        while (lexer.peek() != Token.EOF) {
            iter = expression(aux);
            aux.setLeft(iter);

            if (lexer.peek() == Token.UNION) {
                aux.setLeft(expression(iter));
            }

            aux = iter;
            iter = iter.getLeft();
        }

        if (head.getLeft() == null) {
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
